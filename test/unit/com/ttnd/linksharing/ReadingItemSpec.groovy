package com.ttnd.linksharing

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(ReadingItem)
@Mock([LinkResource, DocumentResource])
class ReadingItemSpec extends Specification {

    def "constraintsOfResourceItemExcludingUserUniqueness"() {
        given:
        ReadingItem resourceItemObj = new ReadingItem(resource: resource, user: user, isRead: isRead);

        when:
        boolean result = resourceItemObj.validate();

        then:
        result == excepted

        where:
        sno | resource           | user       | isRead | excepted
        1   | new LinkResource() | new User() | true   | true
        2   | null               | new User() | true   | false
        3   | new LinkResource() | null       | true   | false
        4   | new LinkResource() | new User() | null   | false
    }

    def "validatingUniqueReadingItem"() {
        given:
        Resource resource = new LinkResource().save(validate: false)
        User user = new User().save(validate: false)
        ReadingItem readingItem = new ReadingItem(resource: resource, user: user, isRead: true)
        ReadingItem newReadingItem = new ReadingItem(resource: resource, user: user, isRead: false)

        when:
        readingItem.save(flush: true)
        newReadingItem.save(flush: true)

        then:
        !readingItem.errors.allErrors.size()
        newReadingItem.errors.allErrors.size()
        newReadingItem.errors.getFieldError('resource')

    }

    def "tostring"() {
        setup:
        User user = new User(userName: userName).save(validate: false)
        Resource resource = new DocumentResource(description: description).save(validate: false)
        ReadingItem readingItem = new ReadingItem(user: user, resource: resource, isRead: isRead)

        when:
        result == readingItem.toString()

        then:
        noExceptionThrown()

        where:
        userName          | description | isRead | result
        "vishnu.aggarwal" | "grails"    | true   | "vishnu.aggarwal read the grails: true"
    }
}
