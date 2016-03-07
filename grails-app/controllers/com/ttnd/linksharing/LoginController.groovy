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

    /*def registration(String userName, String firstName, String lastName, String email, String password, String
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
    }*/

    def registration(UserCO userCo) {
        User user = User.findByUserNameAndEmail(userCo.userName, userCo.email)
        if (user == null) {
            if(userCo.hasErrors()){
                flash.error=""
            }
            else{
                user = userCo.properties
                user.isActive=true
                /*if (userCo.photo != null) {
                    MultipartFile uploadPhoto = userCo.photo
                    File uploadFile = new File("${grailsApplication.config.grails.serverPath}/" + "${uploadPhoto.originalFilename}")
                }*/
                if (user.validate()) {
                    user.save(flush: true)
                    flash.message = "User registered"
                    forward(action: 'login', params: [loginUserName: user.userName, loginPassword: user.password])
                } else {
                    flash.error = "${user.errors.allErrors.collect { message(error: it) }.join(',')}"
                }
            }
        } else {
            flash.error = "User already exists"
        }
        redirect(controller: 'login', action: 'index')
    }

    List<Resource> getTopPosts() {
        def result = Resource.getTopPosts()
        return result
    }
}
