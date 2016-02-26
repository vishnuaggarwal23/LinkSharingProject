package com.ttnd.linksharing

import constants.AppConstants
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
        when:
        controller.delete(id)

        then:
        response.text == result

        where:
        id | result
        11 | "Resource Deleted"
    }

    def "CheckSubscriptionSave"() {
        setup:
        User user = new User(userName: 'user', firstName: 'fname', lastName: 'lname', email: 'email@gmail.com', password:
                AppConstants.PASSWORD, confirmPassword: AppConstants.PASSWORD)

        when:
        controller.save(topicID)

        then:
        response.text == result

        where:
        topicID | result
        1       | "subscription saved"
        15      | "subscription not saved"
    }

    def "CheckSubscriptionUpdate"() {
        when:
        controller.update(id, seriousness)

        then:
        response.text == result

        where:
        id  | seriousness | result
        1   | "serious"   | "Subscription Updated"
        2   | "casual"    | "Subscription Updated"
        100 | "casual"    | "Subscription not updated"
    }
}
