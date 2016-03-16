package com.ttnd.linksharing

import enums.Visibility
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(LinkResource)
class LinkResourceSpec extends Specification {

    def "validateLinkResource "() {
        setup:
        User user = new User(firstName: "Vishnu", lastName: "Aggarwal", email: "vishnu.aggarwal@tothenew.com",
                password: "123456", confirmPassword: '123456', userName: "vishnu.aggarwal")
        Topic topic = new Topic(name: "Grails", visibility: Visibility.PRIVATE, createdBy: user)
        LinkResource linkResource = new LinkResource(url: url, description: description, createdBy:
                user, topic: topic)

        when:
        Boolean result = linkResource.validate()

        then:
        result == valid

        where:
        description   | url                        | valid
        "description" | 'http://www.tothenew.com/' | true
        " "           | 'http://www.tothenew.com/' | false
        null          | 'http://www.tothenew.com/' | false
        "description" | '://www.tothenew.com/'     | false
        "description" | ' '                        | false
        "description" | null                       | false
    }

    def "tostringCheck"() {
        setup:
        LinkResource linkResource = new LinkResource(url: url)

        when:
        result == linkResource.toString()

        then:
        noExceptionThrown()

        where:
        url                               | result
        "http://www.testLinkResource.com" | "http://www.testLinkResource.com"
        ""                                | ""
        null                              | null
    }
}
