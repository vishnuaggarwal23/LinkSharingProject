package com.ttnd.linksharing

import enums.Visibility
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Topic)
class TopicSpec extends Specification {
    def "Check Topic"() {
        setup:
        User user = new User()
        Topic topic = new Topic(name: name, createdBy: user, visibility: visibility)


        when:
        Boolean result = topic.validate()

        then:
        result == valid

        where:
        name     | visibility         | valid
        ""       | ""                 | false
        null     | null               | false
        "Grails" | Visibility.PRIVATE | true
        "Grails" | Visibility.PUBLIC  | true
        "Grails" | "abc"              | false
    }

    def "unique topic name per user"() {
        setup:
        String topicName = "grails"
        User user = new User(firstName: "Vishnu", lastName: "Aggarwal", email: "vishnu.aggarwal@tothenew.com",
                password: "123456", userName: "vishnu.aggarwal")
        Topic topic = new Topic(name: topicName, visibility: Visibility.PRIVATE, createdBy: user)

        when:
        topic.save()

        then:
        Topic.count() == 1

        when:
        topic = new Topic(name: topicName, visibility: Visibility.PRIVATE, createdBy: user)
        topic.save()

        then:
        Topic.count() == 1
        topic.errors.allErrors.size() == 1
        topic.errors.getFieldErrorCount('name') == 1
    }
}
