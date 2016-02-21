package com.ttnd.linksharing

class LoginController {

    def index() {
        if (session.user) {
            forward(controller: 'user', action: 'index')
        } else {
            render "Login Failed"
        }
    }

    def login(String username, String password) {
        User user = User.findByUserNameAndPassword(username, password)
        if (user) {
            if (user.isActive) {
                session.user=user
                redirect(action: 'index')
            } else {
                flash.put("error", "Inactive Account")
                render flash.get("error")
            }
        } else {
            flash.put("error", "Account not Found")
            render flash.get("error")
        }
    }

    def logout() {
        session.invalidate()
        forward(action: 'index')
    }
}
