package com.ttnd.linksharing

import constants.AppConstants
import enums.Visibility
import grails.transaction.Transactional

@Transactional
class BootstrapService {

    def userService
    def topicService
    def resourceService
    def subscriptionService

    def createUser() {
        User adminUser = new User(
                userName: 'admin',
                firstName: 'Vishnu',
                lastName: 'Aggarwal',
                email: 'vishnu.aggarwal@tothenew.com',
                password: AppConstants.PASSWORD,
                confirmPassword: AppConstants.PASSWORD,
                isAdmin: true,
                isActive: true
        )

        User normalUser = new User(
                userName: 'normal',
                firstName: 'Vishnu',
                lastName: 'Aggarwal',
                email: 'vishnuaggarwal23@gmail.com',
                password: AppConstants.PASSWORD,
                confirmPassword: AppConstants.PASSWORD,
                isAdmin: false,
                isActive: true
        )

        userService.saveUser(adminUser)
        userService.saveUser(normalUser)
    }

    def createTopic() {
        Topic privateTopic = new Topic(
                name: 'Grails',
                visibility: AppConstants.VISIBILITY,
                createdBy: User.load(1L)
        )

        Topic publicTopic = new Topic(
                name: 'Groovy',
                visibility: Visibility.PUBLIC,
                createdBy: User.load(1L)
        )

        topicService.saveTopic(privateTopic)
        topicService.saveTopic(publicTopic)
    }

    def createResource() {
        Resource linkResource = new LinkResource(
                description: "Grails Web Site",
                createdBy: User.load(1L),
                topic: Topic.load(2L),
                url: 'https://grails.org/'
        )

        Resource documentResource = new DocumentResource(
                description: "Groovy E-Book",
                createdBy: User.load(1L),
                topic: Topic.load(1L),
                filePath: '/home/vishnu/var/www/linksharing/Groovy.pdf',
                contentType: AppConstants.DOCUMENT_CONTENT_TYPE,
                fileName: 'Groovy.pdf'
        )

        resourceService.saveResource(linkResource)
        resourceService.saveResource(documentResource)
    }

    def createSubscriptions() {
        Subscription privateTopicSubscription = new Subscription(
                topic: Topic.load(1L),
                user: User.load(2L),
                seriousness: AppConstants.SERIOUSNESS
        )

        Subscription publicTopicSubscription = new Subscription(
                topic: Topic.load(2L),
                user: User.load(2L),
                seriousness: AppConstants.SERIOUSNESS
        )

        subscriptionService.saveSubscription(privateTopicSubscription)
        subscriptionService.saveSubscription(publicTopicSubscription)
    }

    def createReadingItem() {
        ReadingItem linkResource = new ReadingItem(
                user: User.load(2L),
                resource: Resource.load(1L),
                isRead: false
        )

        ReadingItem documentResource = new ReadingItem(
                user: User.load(2L),
                resource: Resource.load(2L),
                isRead: false
        )

        resourceService.saveReadingItem(linkResource)
        resourceService.saveReadingItem(documentResource)
    }

    def createResourceRating() {
        ResourceRating linkResource = new ResourceRating(
                user: User.load(2L),
                resource: Resource.load(1L),
                score: AppConstants.RATING
        )

        ResourceRating documentResource = new ResourceRating(
                user: User.load(2L),
                resource: Resource.load(2L),
                score: AppConstants.RATING
        )

        resourceService.saveResourceRating(linkResource)
        resourceService.saveResourceRating(documentResource)
    }
}
