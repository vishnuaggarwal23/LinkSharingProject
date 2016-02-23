package com.ttnd.linksharing

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(SubscriptionController)
class SubscriptionControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
    }

    def "CheckSubscriptionDelete"() {
        when:
        controller.delete(id)

        then:
        response.text == result

        where:
        id | result
        11 | "Resource Deleted"
    }

    def "CheckSubscriptionUpdate"(){
        when:
        controller.update(id,seriousness)
    }
}
