package com.ttnd.linksharing

import vo.PostVO

class LoginController {

    def index() {
        if (session.user) {
            flash.message = "Logged In"
            forward(controller: 'user', action: 'index')

        } else {
            List<PostVO> topPostVOList = Resource.getTopPosts()
            List<PostVO> recentPostVOList = Resource.getRecentPosts()
            render(view: 'index', model: [topPosts: topPostVOList, recentPosts: recentPostVOList])
        }
        //flash.error="Login Failed"

//            def result=Resource.getTopPosts()
//            render "${result}"
    }

    def login(String loginUserName, String loginPassword) {
        User user = User.findByUserNameAndPassword(loginUserName, loginPassword)
        if (user) {
            if (user.isActive) {
                session.user = user
                //redirect(action: 'index')
            } else {
                flash.error = "Inactive Account"
            }
        } else {
            flash.error = "Account Not Found"
        }
        redirect(controller: 'login', action: 'index')
    }

    def logout() {
        session.invalidate()
        redirect(action: 'index')
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

    List<Resource> getTopPosts() {
        def result = Resource.getTopPosts()
        return result
    }
}
