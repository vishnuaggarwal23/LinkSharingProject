package com.ttnd.linksharing

import enums.Visibility
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Topic)
class TopicSpec extends Specification {

    @Unroll("Topic Constraints ----- #sno")
    def "topicConstraints"() {
        given:
        Topic topicObj = new Topic(name: topicName, createdBy: creator, visibility: visibility);

        when:
        boolean result = topicObj.validate();

        then:
        result == valid

        where:
        sno | topicName | creator    | visibility         | valid
        1   | "grails"  | new User() | Visibility.PRIVATE | true
        2   | " "       | new User() | Visibility.PRIVATE | false
        3   | null      | new User() | Visibility.PRIVATE | false
        4   | "grails"  | null       | Visibility.PRIVATE | false
        5   | "grails"  | new User() | null               | false
    }

    def "topicUserUniqueness"() {
        given:
        String topicName = "grails"
        User creator = new User();
        Visibility visibility = Visibility.PRIVATE;
        Topic topicObj = new Topic(name: topicName, createdBy: creator, visibility: visibility);

        when:
        topicObj.save();

        then:
        Topic.count() == 1;

        when:
        topicObj = new Topic(name: topicName, createdBy: creator, visibility: visibility);
        topicObj.save();

        then:
        Topic.count() == 1;
        topicObj.errors.allErrors.size() == 1;
        topicObj.errors.getFieldErrorCount('name') == 1;
    }

    def "tostring"() {
        given:
        User user = new User(userName: 'vishnu')
        Topic topic = new Topic(name: name, createdBy: user)

        when:
        String topicName = topic.toString()

        then:
        topicName == result

        where:
        name            | result
        "testTopicName" | "testTopicName, vishnu"
    }
}
