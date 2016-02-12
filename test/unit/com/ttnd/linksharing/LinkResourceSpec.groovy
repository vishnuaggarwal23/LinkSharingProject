package com.ttnd.linksharing

import enums.Visibility
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(LinkResource)
class LinkResourceSpec extends Specification {
    def "Validate Document Resource "() {

        setup:
        User user = new User(firstName: "Vishnu", lastName: "Aggarwal", email: "vishnu.aggarwal@tothenew.com",
                password: "123456", userName: "vishnu.aggarwal")
        Topic topic = new Topic(name: "Grails", visibility: Visibility.PRIVATE, createdBy: user)
        LinkResource linkResource = new LinkResource(url: url, description: description, createdBy:
                user, topic: topic)

        when:
        Boolean result = linkResource.validate()

        then:
        result == valid

        where:
        url                      | description | valid
        ""                       | ""          | false
        null                     | null        | false
        "https://www.google.com" | null        | false
        null                     | "google"    | false
        "https://www.google.com" | "google"    | true
    }
}
