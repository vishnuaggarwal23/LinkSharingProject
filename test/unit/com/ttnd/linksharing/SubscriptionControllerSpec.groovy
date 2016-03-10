package com.ttnd.linksharing

import constants.AppConstants
import enums.Visibility
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(SubscriptionController)
@Mock([User, Topic, Subscription])
class SubscriptionControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
    }

    def "CheckSubscriptionDelete"() {
        setup:
        User user = new User().save(validate: false)
        Topic topic = new Topic().save(validate: false)
        Subscription subscription = new Subscription(user: user, topic: topic)
        subscription.id = id
        subscription.save(validate: false)

        when:
        controller.delete(id)

        then:
        response.text == result

        where:
        id | result
        1  | "Subscription Deleted"
    }

    def "checkSubscriptionSave"() {
        setup:
        User user = new User(userName: 'user', firstName: 'fname', lastName: 'lname', email: 'email@gmail.com', password:
                AppConstants.PASSWORD, confirmPassword: AppConstants.PASSWORD)
        user.save()
        session.user = user
        Topic topic = new Topic(name: 'topic1', createdBy: user, visibility: Visibility.PUBLIC)
        topic.id = id
        topic.save()

        when:
        controller.save(id)

        then:
        response.text == result

        where:
        id | result
        1  | "subscription saved"
    }

    def "CheckSubscriptionUpdate"() {
        setup:
        User user = new User().save(validate: false)
        Topic topic = new Topic().save(validate: false)
        Subscription subscription = new Subscription(user: user, topic: topic)
        subscription.id = id
        subscription.save(validate: false)

        when:
        controller.update(id, seriousness)

        then:
        response.text == result

        where:
        id | seriousness | result
        1  | "serious"   | "Subscription Updated"
        2  | "casual"    | "Subscription Updated"
    }

    def "checkSubscriptionNotFoundInDeletion"() {
        when:
        controller.delete(1)

        then:
        response.text == "Subscription not Deleted"
    }

    def "checkSubscriptionNotFoundInUpdation"() {
        when:
        controller.update(1, "CASUAL")

        then:
        response.text == "Subscription not Found"
    }
}
