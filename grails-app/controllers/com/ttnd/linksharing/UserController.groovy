package com.ttnd.linksharing

class UserController {

    def index() {
        User user=session.user;
        render "User ${user.userName} Dashboard"
    }
}
