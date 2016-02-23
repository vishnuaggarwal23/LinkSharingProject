package com.ttnd.linksharing

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(UserController)
class UserControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
    }

    def "CheckUserIndex"() {
        when:
        session.user = user
        controller.index()

        then:
        response.text == result

        where:
        user                         | result
        new User(userName: "vishnu") | "User vishnu Dashboard"
    }
}
