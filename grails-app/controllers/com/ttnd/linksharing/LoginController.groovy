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
                session.user = user
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

    def registration(String userName, String firstName, String lastName, String email, String password, String
            confirmPassword) {
        User user = new User(userName: userName, firstName: firstName, lastName: lastName,
                email: email, password: password, confirmPassword: confirmPassword)
        if (user.validate()) {
            user.save(flush: true, failOnError: true)
            render "${user} saved"
        } else {
            //flash.message = "${user} not added--- ${user.errors.allErrors}"
            flash.error "${user.errors.allErrors.collect { message(error: it) }.join(',')}"
            render "User not saved"
        }
    }
}
