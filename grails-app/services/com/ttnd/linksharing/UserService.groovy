package com.ttnd.linksharing

import grails.transaction.Transactional

@Transactional
class UserService {

    def serviceMethod() {

    }

    def isAdmin(Long id) {
        User user = User.get(id)
        if (user) {
            return user.isAdmin
        }
    }

    def isActive(Long id) {
        User user = User.get(id)
        if (user) {
            return user.isActive
        }
    }

    def isCurrentUser(Long userId1, Long userId2) {
        User user1 = User.get(userId1)
        User user2 = User.get(userId2)
        if (user1 && user2) {
            return user1.equals(user2)
        }
    }

    def toggleActive(Long userId, Long adminUserId) {
        User adminUser = User.get(adminUserId)
        User user = User.get(userId)
        if(adminUser){
            if(user){
                user.isActive=!user.isActive
            }
        }
    }
}
