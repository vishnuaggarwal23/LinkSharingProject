package com.ttnd.linksharing

class UserController {

    def index() {
        User user=session.user
        List topicsOfUserList=Topic.getTopicsOfAUser(user)
        render (view:'index',model:[topicsOfUser:topicsOfUserList])
        //render "User ${user} Dashboard"
    }
}
