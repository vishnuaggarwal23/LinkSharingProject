package com.ttnd.linksharing

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(ResourceRating)
@Mock([DocumentResource, LinkResource])
class ResourceRatingSpec extends Specification {

    def "checkResourceRating"() {
        given:
        ResourceRating resourceRating = new ResourceRating(resource: resource, user: user, score: score)

        when:
        Boolean valid = resourceRating.validate()

        then:
        valid == result

        where:
        resource                                     | user                             | score | result
        new DocumentResource().save(validate: false) | new User().save(validate: false) | 3     | true
        null                                         | new User().save(validate: false) | 3     | false
        new DocumentResource().save(validate: false) | null                             | 3     | false
        new DocumentResource().save(validate: false) | new User().save(validate: false) | 10    | false
        new DocumentResource().save(validate: false) | new User().save(validate: false) | 0     | false

    }

    def "validateUniqueResourceRating"() {

        given:
        Resource resource = new LinkResource().save(validate: false)
        User user = new User().save(validate: false)
        ResourceRating resourceRating = new ResourceRating(resource: resource, user: user, score: 3)
        ResourceRating newResourceRating = new ResourceRating(resource: resource, user: user, score: 4)

        when:
        resourceRating.save(flush: true)
        newResourceRating.save(flush: true)

        then:
        !resourceRating.errors.allErrors.size()
        newResourceRating.errors.allErrors.size()
        newResourceRating.errors.getFieldError('resource')
    }

    def "tostringCheck"() {
        setup:
        User user = new User(userName: userName).save(validate: false)
        Resource resource = new DocumentResource(description: description).save(validate: false)
        ResourceRating resourceRating = new ResourceRating(user: user, resource: resource, score: score)

        when:
        result == resourceRating.toString()

        then:
        noExceptionThrown()

        where:
        userName          | description | score | result
        "vishnu.aggarwal" | "grails"    | 5     | "vishnu.aggarwal rated grails by 5"
    }
}
