package com.ttnd.linksharing

import grails.test.spock.IntegrationSpec
import vo.RatingInfoVO

class ResourceControllerIntegrationSpec extends IntegrationSpec {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
    }

    def "Check-GetTrendingTopics"() {
        when:
        List list = Topic.getTrendingTopics()

        then:
        !list.isEmpty()
    }

    def "Check-getRatingInfo"() {
        setup:
        Resource resource = Resource.get(id)

        when:
        RatingInfoVO ratingInfoVO = resource.ratingInfo

        then:
        ratingInfoVO != null

        where:
        id << [1, 2]
    }
}