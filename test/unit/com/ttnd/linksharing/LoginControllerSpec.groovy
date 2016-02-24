package com.ttnd.linksharing

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(LoginController)
class LoginControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
    }

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
        user.save(flush: true, failOnError: true)

        then:
        User.count()
        response.text == result

        where:
        userName          | firstName | lastName   | email                          | password | confirmPassword | result
        "vishnu.aggarwal" | "vishnu"  | "aggarwal" | "vishnu.aggarwal@tothenew.com" | "123abc" | "123abc"        | "vishnu.aggarwal saved"
        "vishnu.aggarwal" | "vishnu"  | "aggarwal" | "vishnuaggarwal@tothenew.com"  | "123a"   | "123abc"        | "User not saved"
    }

}
