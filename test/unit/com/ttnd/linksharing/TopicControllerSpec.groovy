package com.ttnd.linksharing

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@Mock([User,Subscription,Topic])
@TestFor(TopicController)
class TopicControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
    }

    def "CheckTopicSave"() {
        when:
        session.user = user
        controller.save(topicName, visibility)

        then:
        response.text == result

        where:
        user                         | topicName | visibility | result
        new User(userName: "vishnu") | "grails"  | "PUBLIC"   | "grails saved"
        new User(userName: "vishnu") | "grails"  | null       | "topic not saved"
    }
}
