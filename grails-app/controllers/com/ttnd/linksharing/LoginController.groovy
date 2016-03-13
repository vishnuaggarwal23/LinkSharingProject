package com.ttnd.linksharing

import co.UserCO
import vo.PostVO

class LoginController {

    def userService
    def emailService

    def index() {
        if (session.user) {
            flash.message = "Logged In"
            forward(controller: 'user', action: 'index')

        } else {
            List<PostVO> topPostVOList = Resource.getTopPosts()
            List<PostVO> recentPostVOList = Resource.getRecentPosts()
            render(view: 'index', model: [topPosts   : topPostVOList,
                                          recentPosts: recentPostVOList])
        }
    }

    def login(String loginUserName, String loginPassword) {
        User user = User.findByUserNameAndPassword(loginUserName, loginPassword)
        if (user) {
            if (user.isActive) {
                session.user = user
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

    def registration(UserCO userCo) {
        if (userCo.hasErrors()) {
            render(view: '/login/index', model: [topPosts   : Resource.getTopPosts(),
                                                 recentPosts: Resource.getRecentPosts(),
                                                 userCo     : userCo])
        } else {
            User user = userService.registerUser(userCo, params.file)
            if (user) {
                forward(controller: 'login', action: 'index', params: [loginUserName: user.userName,
                                                                       loginPassword: user.password])
            } else {
                render(view: 'index', model: [topPosts   : Resource.getTopPosts(),
                                              recentPosts: Resource.getRecentPosts(),
                                              userCo     : user])
            }
        }
    }

    List<Resource> getTopPosts() {
        def result = Resource.getTopPosts()
        return result
    }

    def validateEmail() {
        Boolean valid = User.countByEmail(params.email) ? false : true
        render valid
    }

    def validateUserName() {
        Boolean valid = User.countByUserName(params.userName) ? false : true
        render valid
    }

    def forgotPassword(String email) {
        if (email) {
            if (emailService.forgotPassword(email)) {
                render(view: 'index', model: [topPosts: Resource.getTopPosts(), recentPosts: Resource.getRecentPosts(), result: "Email Has Been Sent"])
            } else {
                render(view: 'index', model: [topPosts: Resource.getTopPosts(), recentPosts: Resource.getRecentPosts(), result: "Email Has Not Been Sent"])
            }
        } else {
            flash.error = "Please Enter an Email"
            redirect(controller: 'login', action: 'index')
        }
    }
}
