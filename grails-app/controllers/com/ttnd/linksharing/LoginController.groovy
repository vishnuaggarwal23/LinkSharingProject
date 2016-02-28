package com.ttnd.linksharing

class LoginController {

    def index() {
        if (session.user) {
            forward(controller: 'user', action: 'index')
        } else {
            render "Login Failed"
            List topPosts=getTopPosts()
            topPosts.each {
                render "${it} <br/>"
            }
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
            user.save(flush: true)
            render "${user} saved"
        } else {
            flash.error "${user.errors.allErrors.collect { message(error: it) }.join(',')}"
            render "User not saved"
        }
    }

    List<Resource> getTopPosts(){
        def result=Resource.getTopPosts()
        return result
    }
}
