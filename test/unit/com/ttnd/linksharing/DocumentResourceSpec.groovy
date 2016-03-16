package com.ttnd.linksharing

import constants.AppConstants
import enums.Visibility
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(DocumentResource)
class DocumentResourceSpec extends Specification {
    def "validateDocumentResource "() {

        setup:
        User user = new User(firstName: "Vishnu", lastName: "Aggarwal", email: "vishnu.aggarwal@tothenew.com",
                password: "123456", confirmPassword: '123456', userName: "vishnu.aggarwal")
        Topic topic = new Topic(name: "Grails", visibility: Visibility.PRIVATE, createdBy: user)
        DocumentResource documentResource = new DocumentResource(filePath: filePath, description: description,
                createdBy: user, topic: topic, contentType: AppConstants.DOCUMENT_CONTENT_TYPE)

        when:
        Boolean result = documentResource.validate()

        then:
        result == valid

        where:
        description   | filePath      | valid
        "description" | '/ home/file' | true
        " "           | '/home/file'  | false
        null          | '/home/file'  | false
        "description" | ' '           | false
        "description" | null          | false
    }

    def "tostringCheck"() {
        setup:
        DocumentResource documentResource = new DocumentResource(filePath: filePath)

        when:
        result == documentResource.toString()

        then:
        noExceptionThrown()

        where:
        filePath          | result
        "/some/file/path" | "/some/file/path"
        ""                | ""
        null              | null
    }
}
