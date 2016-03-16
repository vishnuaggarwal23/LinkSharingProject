package com.ttnd.linksharing

import constants.AppConstants
import enums.Seriousness
import grails.transaction.Transactional
import util.Linksharing

@Transactional
class SubscriptionService {

    Subscription saveSubscription(Subscription subscription) {
        if (subscription.validate()) {
            return subscription.save(flush: true)
        }
        return null
    }

    def deleteSubscription(Subscription subscription) {
        subscription.delete(flush: true)
    }

    Subscription saveSubscription(Topic topic, User user) {
        if (user && topic) {
            Subscription subscription = new Subscription(topic: topic, user: user, seriousness: AppConstants.SERIOUSNESS)
            return saveSubscription(subscription)
        }
        return null
    }

    def deleteSubscription(Topic topic, User user) {
        if (user && topic) {
            if (Linksharing.isTopicSubscribedByUser(topic, user) && !Linksharing.ifTopicIsCreatedBy(topic, user)) {
                deleteSubscription(Subscription.findByUserAndTopic(user, topic))
                return true
            } else {
                return false
            }
        }
        return null
    }

    def updateSubscription(Subscription subscription, String serious) {
        if (subscription && serious) {
            subscription.seriousness = Seriousness.checkSeriousness(serious)
            return saveSubscription(subscription)
        }
        return null
    }

    /*def search(TopicSearchCO topicSearchCO) {
        if (topicSearchCO.id) {
            User user = topicSearchCO.getUser()
            return user.getSubscribedTopics()
        }
    }*/
}
