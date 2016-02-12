package com.ttnd.linksharing

import enums.Seriousness
import enums.Visibility
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Subscription)
class SubscriptionSpec extends Specification {
    def "Validating Subscription"() {
        setup:
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

    def "Validating duplicate subscription"() {
        given:
        User user = new User()
        Topic topic = new Topic()
        Subscription subscription = new Subscription(topic: topic, user: user, seriousness: Seriousness.VERY_SERIOUS)
        Subscription newSubscription = new Subscription(topic: topic, user: user, seriousness: Seriousness.VERY_SERIOUS)

        when:
        subscription.save(flush: true)
        newSubscription.save(flush: true)

        then:
        !subscription.errors.allErrors.size()
        newSubscription.errors.allErrors.size()
        newSubscription.errors.getFieldError('user')
    }
}
