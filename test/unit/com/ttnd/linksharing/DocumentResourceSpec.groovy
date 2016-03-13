package com.ttnd.linksharing

import enums.Visibility
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(DocumentResource)
class DocumentResourceSpec extends Specification {
    def "Validate Document Resource "() {

        setup:
        User user = new User(firstName: "Vishnu", lastName: "Aggarwal", email: "vishnu.aggarwal@tothenew.com",
                password: "123456", userName: "vishnu.aggarwal")
        Topic topic = new Topic(name: "Grails", visibility: Visibility.PRIVATE, createdBy: user)
        DocumentResource documentResource = new DocumentResource(filePath: filePath, description: description,
                createdBy: user, topic: topic)

        when:
        Boolean result = documentResource.validate()

        then:
        result == valid

        where:
        filePath   | description | valid
        ""         | ""          | false
        null       | null        | false
        "filepath" | null        | false
        null       | "file"      | false
        "filepath" | "file"      | true
    }

    def "CheckToString"() {
        setup:
        DocumentResource documentResource = new DocumentResource(filePath: filePath)

        when:
        result == documentResource.toString()

        then:
        noExceptionThrown()

        where:
        filePath          | result
        "/some/file/path" | "/some/file/path"
    }
}
