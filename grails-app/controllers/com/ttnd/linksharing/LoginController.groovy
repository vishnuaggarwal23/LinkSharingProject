package com.ttnd.linksharing

class LoginController {

    def index() {
        if (session.user) {
            forward([controller: 'UserController', action: 'index'])
        } else {
            render "Session Failure"
        }
    }

    def login(String username, String password) {

    }

    def logout() {
        session.invalidate()
        forward([action:'index'])
    }
}
