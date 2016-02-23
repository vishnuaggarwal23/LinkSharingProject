package com.ttnd.linksharing

import constants.AppConstants
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
        response.forwardedUrl == "/login/index"
    }

    def "CheckRegistration- New user registers for the application"() {
        setup:
        User user = new User(userName: "testUser", firstName: "test", lastName: "user", email: "testUser@ttnd.com",
                password: AppConstants.PASSWORD, confirmPassword: AppConstants.PASSWORD)

        when:
        controller.registration()

        then:
        User.count == 1
    }

    def "CheckRegistration- New user is not able to register for the application"() {
        setup:
        User user = new User(userName: "testUser", firstName: "test", lastName: "user", email: "testUser@ttnd.com",
                password: AppConstants.PASSWORD, confirmPassword: AppConstants.PASSWORD)
    }

}
