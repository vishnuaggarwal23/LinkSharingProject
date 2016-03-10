package com.ttnd.linksharing

import co.UserCO
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
        /*User user = User.findByUserNameOrEmail(userCo.userName, userCo.email)
        if (user == null) {*/
            if (userCo.hasErrors()) {
                //flash.error=userCo.errors.allErrors
                render(view: '/login/index', model: [topPosts: Resource.getTopPosts(), recentPosts: Resource
                        .getRecentPosts(), userCo            : userCo])

            } else {
                User user = userCo.properties
                def file = params.file
                if (!file.empty) {
                    user.photo = params.file.bytes
                }
                user.isActive = true
                if (user.validate()) {
                    user.save(flush: true)
                    forward(action: 'login', params: [loginUserName: user.userName, loginPassword: user.password])
                } else {
                    //flash.error=user.errors.allErrors
                    render(view: 'index', model: [topPosts: Resource.getTopPosts(), recentPosts: Resource.getRecentPosts(), userCo: user])
                }
            }
        /*} else {
            flash.error = "User already exists"
            redirect(controller: 'login', action: 'index')
        }*/
    }

    List<Resource> getTopPosts() {
        def result = Resource.getTopPosts()
        return result
    }

    def validateEmail() {
        Boolean valid = User.countByEmail(params.email) ? false:true
        /*response.setContentType("text/json;charset=UTF-8")*/
        render valid
    }

    def validateUserName() {
        Boolean valid = User.countByUserName(params.userName) ? false:true
        /*response.setContentType("text/json;charset=UTF-8")*/
        render valid
    }
}
