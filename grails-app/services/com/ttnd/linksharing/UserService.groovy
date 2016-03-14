package com.ttnd.linksharing

import co.UpdatePasswordCO
import co.UpdateProfileCO
import co.UserCO
import grails.transaction.Transactional
import util.Linksharing

@Transactional
class UserService {

    def assetResourceLocator
    def emailService

    User saveUser(User user) {
        if (user.validate()) {
            return user.save(flush: true)
        }
        return null
    }

    User updatePassword(UpdatePasswordCO updatePasswordCO) {
        User user = updatePasswordCO.getUser()
        if (user) {
            user.password = updatePasswordCO.password
            user.confirmPassword = updatePasswordCO.password
            return saveUser(user)
        }
        return null
    }

    User updateProfile(UpdateProfileCO updateProfileCO) {
        User user = updateProfileCO.getUser()
        if (user) {
            if (!updateProfileCO.file.empty) {
                user.photo = updateProfileCO.file.bytes
            }
            user.firstName = updateProfileCO.firstName
            user.lastName = updateProfileCO.lastName
            return saveUser(user)
        }
        return null
    }

    User toggleActiveStatus(User admin, User normal) {
        if (admin && normal) {
            if (Linksharing.isUserAdmin(admin) && !Linksharing.isUserAdmin(normal) && Linksharing.isUserActive(admin)) {
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

    User registerUser(UserCO userCO, def file) {
        if (Linksharing.ifUserExists(userCO)) {
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

    def sendUnreadItemsMail() {
        Linksharing.getUserWithUnreadResources().each { user ->
            emailService.sendUnReadResourcesMail(user, Linksharing.getUnreadResources(user))
        }
    }
}
