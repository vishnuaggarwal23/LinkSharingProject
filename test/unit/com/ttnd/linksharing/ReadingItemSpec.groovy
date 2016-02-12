package com.ttnd.linksharing

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(ReadingItem)
class ReadingItemSpec extends Specification {

    void "Validating Unique Reading Item"() {
        given:
        LinkResource resource = new LinkResource()
        User user = new User()
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
}
