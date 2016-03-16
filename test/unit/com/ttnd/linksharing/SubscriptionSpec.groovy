package com.ttnd.linksharing

import enums.Seriousness
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Subscription)
@Mock(Topic)
class SubscriptionSpec extends Specification {

    def "validatingSubscription"() {
        given:
        Subscription subscription = new Subscription(topic: topic, user: user, seriousness: seriousness)

        when:
        Boolean valid = subscription.validate()

        then:
        valid == result

        where:
        topic       | user       | seriousness        | result
        new Topic() | new User() | Seriousness.CASUAL | true
        null        | new User() | Seriousness.CASUAL | false
        new Topic() | null       | Seriousness.CASUAL | false
        new Topic() | new User() | null               | false
    }

    def "validatingDuplicateSubscription"() {

        given:
        Topic topic = new Topic()
        User user = new User()
        Seriousness seriousness = Seriousness.VERY_SERIOUS
        Subscription subscriptionObj = new Subscription(topic: topic, user: user, seriousness: seriousness)

        when:
        subscriptionObj.save()

        then:
        Subscription.count() == 1

        when:
        subscriptionObj = new Subscription(topic: topic, user: user, seriousness: seriousness)
        subscriptionObj.save()

        then:
        Subscription.count() == 1
        subscriptionObj.errors.allErrors.size() == 1
        subscriptionObj.errors.getFieldErrorCount('user') == 1
    }

    def "tostringCheck"() {

        given:
        User user = new User(userName: userName)
        Topic topic = new Topic(name: topicName)
        Subscription subscription = new Subscription(topic: topic, user: user, seriousness: Seriousness.CASUAL)

        when:
        result == subscription.toString()

        then:
        noExceptionThrown()

        where:
        userName          | topicName | result
        "vishnu.aggarwal" | "grails"  | "vishnu.aggarwal subscribed grails"
    }
}
