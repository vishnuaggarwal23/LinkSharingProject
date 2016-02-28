package com.ttnd.linksharing

import grails.test.spock.IntegrationSpec

class LoginControllerIntegrationSpec extends IntegrationSpec {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
    }

    def "Check-TopPosts"(){
        setup:
        LoginController loginController=new LoginController()

        when:
        List result=loginController.getTopPosts()

        then:
        !result.isEmpty()
    }
}
