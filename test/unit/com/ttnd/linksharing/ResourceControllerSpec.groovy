package com.ttnd.linksharing

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(ResourceController)
@Mock([Resource, LinkResource])
class ResourceControllerSpec extends Specification {

    def "CheckResourceDelete"() {
        when:
        controller.delete(id)

        then:
        response.text == result

        where:
        id | result
        1  | "Resource Deleted"
    }

    def "checkResourceDoesNotExists"() {
        when:
        controller.delete(1)

        then:
        response.text == "Resource not Deleted"
    }
}
