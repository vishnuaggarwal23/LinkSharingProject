package com.ttnd.linksharing

import constants.AppConstants

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

    def registration() {
        User user = new User(userName: "user4", firstName: "fname2", lastName: "lname2", email: "user3@ttnd.com",
                password: AppConstants.PASSWORD, confirmPassword: "123abc", isActive: true, isAdmin: false)
        if (user.validate()) {
            user.save(flush: true, failOnError: true)
            addToUserList(user)
            render "${user} saved"
        } else {
            flash.message = "${user} not added--- ${user.errors.allErrors}"
            render "${user.errors.allErrors.collect { message(error: it) }.join(',')}"
        }
    }

    private void addToUserList(User user) {
        List<User> users = User.list()
        users.add(user)
    }
}
