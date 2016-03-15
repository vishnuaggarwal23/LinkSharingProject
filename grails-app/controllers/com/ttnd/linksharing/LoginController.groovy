package com.ttnd.linksharing

import co.UserCO
import vo.PostVO
import vo.conversion.DomainToVO

class LoginController {

    def userService
    def emailService

    def index() {
        if (session.user) {
            flash.message = g.message(code: "com.ttnd.linksharing.login.index.active.logged.in.account")
            forward(controller: 'user', action: 'index')
        } else {

            List<PostVO> topPostVO = DomainToVO.topPosts()
            List<PostVO> recentPostVO = DomainToVO.recentPosts()
            render(view: 'index', model: [topPosts   : topPostVO,
                                          recentPosts: recentPostVO])
        }
    }

    def login(String loginUserName, String loginPassword) {
        User user = User.findByUserNameAndPassword(loginUserName, loginPassword)
        if (user) {
            if (user.isActive) {
                session.user = user
            } else {
                flash.error = g.message(code: "com.ttnd.linksharing.login.login.inactive.account")
            }
        } else {
            flash.error = g.message(code: "com.ttnd.linksharing.login.login.unregistered.account")
        }
        redirect(controller: 'login', action: 'index')
    }

    def logout() {
        session.invalidate()
        redirect(action: 'index')
    }

    def registration(UserCO userCo) {
        if (userCo.hasErrors()) {
            render(view: '/login/index', model: [topPosts   : DomainToVO.topPosts(),
                                                 recentPosts: DomainToVO.recentPosts(),
                                                 userCo     : userCo])
        } else {
            User user = userService.registerUser(userCo, params.file)
            if (user) {
                forward(controller: 'login', action: 'index', params: [loginUserName: user.userName,
                                                                       loginPassword: user.password])
            } else {
                render(view: 'index', model: [topPosts   : DomainToVO.topPosts(),
                                              recentPosts: DomainToVO.recentPosts(),
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
                render(view: 'index', model: [
                        topPosts   : DomainToVO.topPosts(),
                        recentPosts: DomainToVO.recentPosts(),
                        result     : "Email Has Been Sent"])
            } else {
                render(view: 'index', model: [
                        topPosts   : DomainToVO.topPosts(),
                        recentPosts: DomainToVO.recentPosts(),
                        result     : "Email Has Not Been Sent"])
            }
        } else {
            flash.error = "Please Enter an Email"
            redirect(controller: 'login', action: 'index')
        }
    }
}
