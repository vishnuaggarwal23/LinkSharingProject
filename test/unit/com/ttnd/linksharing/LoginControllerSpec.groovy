package com.ttnd.linksharing

import constants.AppConstants
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(LoginController)
@Mock([User])
class LoginControllerSpec extends Specification {

    def "CheckIndexAction- if user's session is set, then  user should forward to user index action"() {
        setup:
        controller.session.user = new User()

        when:
        controller.index()

        then:
        response.forwardedUrl == "/user/index"
    }

    def "CheckIndexAction- if user's session is not set, then  error should be rendered"() {
        when:
        controller.index()

        then:
        response.text == "Login Failed"
    }

    def "CheckLogout- user's session is invalidated, and is redirected to the login index action"() {
        when:
        controller.logout()

        then:
        session.user == null
        response.forwardedUrl == "/login/index"
    }

    def "CheckRegistration- New user registers for the application"() {
        setup:
        User user = new User(userName: userName, firstName: firstName, lastName: lastName, email: email, password: password,
                confirmPassword: confirmPassword)
        when:
        controller.registration(user.userName, user.firstName, user.lastName, user.email, user.password,
                user.confirmPassword)
        user.save(flush: true)

        then:
        User.count()
        response.text == result

        where:
        userName          | firstName | lastName   | email                          | password | confirmPassword | result
        "vishnu.aggarwal" | "vishnu"  | "aggarwal" | "vishnu.aggarwal@tothenew.com" | "123abc" | "123abc"        | "vishnu.aggarwal saved"
    }

    def "CheckLoginHandler- User is able to login "() {
        setup:
        User user = new User(firstName: "vishnu", lastName: "aggarwal", email: "vishnu.aggarwal@tothenew.com",
                password: AppConstants.PASSWORD, username: "vishnu", confirmPassword: AppConstants.PASSWORD, active: true)
        user.save(flush: true)

        when:
        controller.login(user.userName, user.password)

        then:
        response.redirectedUrl == '/login/index/'
    }

    def "CheckLoginHandler- User is not Active"() {
        setup:
        User user = new User(userName: "user", password: AppConstants.PASSWORD, confirmPassword: AppConstants.PASSWORD,
                firstName: "Name", lastName: "Lname", email: "user@gmail.com")
        user.save(flush: true)

        when:
        controller.login(user.userName, user.password)

        then:
        response.text == "Inactive Account"
    }

    def "CheckLoginHandler- User is not Found"() {
        setup:
        User user = new User(userName: "user", password: AppConstants.PASSWORD, confirmPassword: AppConstants.PASSWORD,
                firstName: "Name", lastName: "Lname", email: "user@gmail.com")

        when:
        controller.login(user.userName, user.password)

        then:
        response.text == "Account not Found"
    }

}
