package com.ttnd.linksharing

import constants.AppConstants
import enums.Visibility
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

    def "CheckTopicShow"() {
        setup:
        User user = new User(userName: "user5", isActive: true, password: AppConstants.PASSWORD, confirmPassword:
                AppConstants.PASSWORD, firstName: "Name", lastName: "Lname", email: "user5@ttnd.com")
        Topic topic = new Topic(name: 'groovy', visibility: visible, createdBy: user)
        session.user = user
        topic.save(flush: true)

        when:
        controller.show(topic.id)

        then:
        response.text == result

        where:
        visible            | result
        Visibility.PUBLIC  | "Success, Subscribed to Public Topic"
        Visibility.PRIVATE | "Success, Subscribed to Private Topic"
    }

    def "CheckTopicShow- User not subscribed to private topic"() {
        setup:
        User user = new User(userName: "user5", isActive: true, password: AppConstants.PASSWORD, confirmPassword:
                AppConstants.PASSWORD, firstName: "Name", lastName: "Lname", email: "user5@ttnd.com")
        User user1 = new User(userName: "user6", isActive: true, password: AppConstants.PASSWORD, confirmPassword:
                AppConstants.PASSWORD, firstName: "Name", lastName: "Lname", email: "user6@ttnd.com")
        Topic topic = new Topic(name: 'groovy', visibility: Visibility.PRIVATE, createdBy: user)
        session.user = user1
        topic.save(flush: true)

        when:
        controller.show(topic.id)

        then:
        response.redirectedUrl == "/login/index"
    }
}
