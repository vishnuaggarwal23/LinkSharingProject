package com.ttnd.linksharing

import co.UpdatePasswordCO
import co.UpdateProfileCO
import co.UserCO
import co.UserSearchCO
import grails.transaction.Transactional
import vo.UserVO

@Transactional
class UserService {

    def assetResourceLocator
    def emailService

    def saveUser(User user) {
        if (user.validate()) {
            return user.save(flush: true)
        } else {
            return null
        }
    }

    def isAdmin(User user) {
        if (user) {
            return user.isAdmin
        } else {
            return null
        }
    }

    def isActive(User user) {
        if (user) {
            return user.isActive
        } else {
            return null
        }
    }

    def isCurrentUser(User user1, User user2) {
        if (user1 && user2) {
            return user1.id == user2.id
        } else {
            return null
        }
    }

    def updatePassword(UpdatePasswordCO updatePasswordCO) {
        User user = updatePasswordCO.getUser()
        if (user) {
            user.password = updatePasswordCO.password
            user.confirmPassword = updatePasswordCO.password
            return saveUser(user)
        } else {
            return null
        }
    }

    def updateProfile(UpdateProfileCO updateProfileCO) {
        User user = updateProfileCO.getUser()
        if (user) {
            if (!updateProfileCO.file.empty) {
                user.photo = updateProfileCO.file.bytes
            }
            user.firstName = updateProfileCO.firstName
            user.lastName = updateProfileCO.lastName
            return saveUser(user)
        } else {
            return null
        }
    }

    def registeredUsers(UserSearchCO userSearchCO, User user) {
        if (user && isAdmin(user)) {
            List<User> registeredUsers = User.search(userSearchCO).list([sort: userSearchCO.sort, order: userSearchCO.order])
            List<UserVO> registeredUsersVO = []
            registeredUsers.each {
                registeredUsersVO.add(new UserVO(id: it.id, name: it.userName, firstName: it.firstName, lastName: it.lastName, email:
                        it.email, isActive: it.isActive))
            }
            return registeredUsersVO
        } else {
            return null
        }

    }

    def toggleActiveStatus(User admin, User normal) {
        if (admin && normal) {
            if (isAdmin(admin) && !isAdmin(normal) && isActive(admin)) {
                normal.isActive = !normal.isActive
                return saveUser(normal)
            }
        } else {
            return null
        }

    }

    def image(User user) {
        if (user) {
            def sendPhoto
            if (user?.photo) {
                sendPhoto = user.photo
            } else {
                sendPhoto = assetResourceLocator.findAssetForURI('silhouette.jpg').getInputStream()
            }
            return sendPhoto
        } else {
            return null
        }
    }

    def registerUser(UserCO userCO, def file) {
        if (checkIfUserExists(userCO)) {
            return null
        } else {
            User user = userCO.properties
            if (!file.empty) {
                user.photo = file.bytes
            }
            user.isActive = true
            return saveUser(user)
        }

    }

    def checkIfUserExists(UserCO userCO) {
        return User.findByUserNameOrEmail(userCO.userName, userCO.email)
    }

    def sendUnreadItemsMail() {
        getUserWithUnreadItems().each { user ->
            emailService.sendUnReadResourcesMail(user, getUnreadResources(user))
        }
    }

    List<User> getUserWithUnreadItems() {
        return Resource.usersWithUnreadResources()
    }

    List<Resource> getUnreadResources(User user) {
        return user.unreadResources()
    }
}
