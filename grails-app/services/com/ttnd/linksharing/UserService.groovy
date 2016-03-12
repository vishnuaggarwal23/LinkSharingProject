package com.ttnd.linksharing

import co.UpdatePasswordCO
import co.UpdateProfileCO
import co.UserSearchCO
import grails.transaction.Transactional
import vo.UserVO

@Transactional
class UserService {

    def assetResourceLocator

    def saveUser(User user) {
        if (user.validate()) {
            return user.save(flush: true)
        }
        return null
    }

    def isAdmin(User user) {
        if (user) {
            return user.isAdmin
        }
    }

    def isActive(User user) {
        if (user) {
            return user.isActive
        }
    }

    def isCurrentUser(User user1, User user2) {
        if (user1 && user2) {
            return user1.id == user2.id
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
        }
        return null
    }

    def toggleActiveStatus(User admin, User normal) {
        if (admin && normal) {
            if (isAdmin(admin) && !isAdmin(normal) && isActive(admin)) {
                normal.isActive = !normal.isActive
                return saveUser(normal)
            }
        }
        return null
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
        }
        return null
    }
}
