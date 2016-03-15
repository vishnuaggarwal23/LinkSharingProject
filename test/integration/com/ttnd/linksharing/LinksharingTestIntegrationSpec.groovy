package com.ttnd.linksharing

import co.UserCO
import constants.AppConstants
import grails.test.spock.IntegrationSpec
import util.Linksharing

class LinksharingTestIntegrationSpec extends IntegrationSpec {
    def "isUserAdmin"() {
        when:
        Boolean result = Linksharing.isUserAdmin(user)

        then:
        result == valid

        where:
        user         | valid
        User.get(1L) | false
        User.get(2L) | true
    }

    def "isUserActive"() {
        when:
        Boolean result = Linksharing.isUserActive(user)

        then:
        result == valid

        where:
        user         | valid
        User.get(1L) | true
        User.get(2L) | false
    }

    def "isCurrentUser"() {
        when:
        Boolean result = Linksharing.isCurrentUser(user1, user2)

        then:
        result == valid

        where:
        user1        | user2        | valid
        User.get(1L) | User.get(1L) | true
        User.get(1L) | User.get(2L) | false

    }

    def "ifUserExists"() {
        given:
        UserCO userCO = new UserCO(userName: 'user', firstName: 'Vishnu', lastName: 'Aggarwal', email: 'user@ttnd.com',
                password: AppConstants.PASSWORD, confirmPassword: AppConstants.PASSWORD)
        UserCO user = new UserCO(userName: 'admin', firstName: 'Vishnu', lastName: 'Aggarwal', email: 'admin@ttnd.com',
                password: AppConstants.PASSWORD, confirmPassword: AppConstants.PASSWORD)

        expect:
        Linksharing.ifUserExists(userCO)
        !Linksharing.ifUserExists(user)

    }

    def "getUserWithUnreadResources"() {
        when:
        List<User> userWithUnreadResources = Linksharing.getUserWithUnreadResources()

        then:
        !userWithUnreadResources.isEmpty()
    }

    def "getUnreadResources"() {
        when:
        List<Resource> unreadResources = Linksharing.getUnreadResources(User.get(1L))

        then:
        !unreadResources.isEmpty()
    }

    def "isTopicPublic"() {
        when:
        Boolean result = Linksharing.isTopicPublic(topic)

        then:
        result == valid

        where:
        topic         | valid
        Topic.get(1L) | false
        Topic.get(2L) | true

    }

    def "ifTopicCanbeViewdBy"() {
        when:
        Boolean result = Linksharing.ifTopicCanbeViewdBy(topic, user)

        then:
        result == valid

        where:
        topic         | user          | valid
        Topic.get(1L) | User.get(1L)  | true
        Topic.get(1L) | User.get(2L)  | true
        Topic.get(2L) | User.load(4L) | true
        Topic.get(1L) | User.load(4L) | false

    }

    def "ifTopicIsCreatedBy"() {
        when:
        Boolean result = Linksharing.ifTopicIsCreatedBy(topic, user)

        then:
        result == valid

        where:
        topic         | user         | valid
        Topic.get(1L) | User.get(1L) | true
        Topic.get(6L) | User.get(1L) | false

    }

    def "isResourceCreatedBy"() {
        when:
        Boolean result = Linksharing.isResourceCreatedBy(resource, user)

        then:
        result == valid

        where:
        resource          | user         | valid
        Resource.get(1L)  | User.get(1L) | true
        Resource.get(11L) | User.get(1L) | false
    }

    def "ifUserCanEditDeleteResource"() {
        when:
        Boolean result = Linksharing.ifUserCanEditDeleteResource(resource, user)

        then:
        result == valid

        where:
        resource          | user         | valid
        Resource.get(1L)  | User.get(1L) | true
        Resource.get(1L)  | User.get(2L) | true
        Resource.get(11L) | User.get(1L) | false
    }

    def "ifResourceCanBeViewdBy"() {
        when:
        Boolean result = Linksharing.ifResourceCanBeViewdBy(resource, user)

        then:
        result == valid

        where:
        resource         | user          | valid
        Resource.get(1L) | User.get(1L)  | true
        Resource.get(2L) | User.get(2L)  | true
        Resource.get(5L) | User.load(4L) | false

    }

    def "checkResourceType"() {
        when:
        String result = Linksharing.checkResourceType(resource)

        then:
        result == valid

        where:
        resource         | valid
        Resource.get(1L) | AppConstants.LINK_RESOURCE_TYPE
        Resource.get(2L) | AppConstants.DOCUMENT_RESOURCE_TYPE
    }

    def "uniqueFileName"() {
        given:
        String fileName1 = Linksharing.uniqueFileName()
        String fileName2 = Linksharing.uniqueFileName()

        expect:
        fileName1 != fileName2
    }

    def "isTopicSubscribedByUser"() {
        when:
        Boolean result = Linksharing.isTopicSubscribedByUser(topic, user)

        then:
        result == valid

        where:
        topic         | user          | valid
        Topic.get(1L) | User.get(1L)  | true
        Topic.get(1L) | User.get(2L)  | true
        Topic.get(1L) | User.load(4L) | false
    }
}
