package com.ttnd.linksharing

import co.TopicSearchCO
import grails.transaction.Transactional

@Transactional
class SubscriptionService {

    def saveSubscription(Subscription subscription) {
        if (subscription.validate()) {
            return subscription.save(flush: true)
        } else {
            return null
        }
    }

    def search(TopicSearchCO topicSearchCO) {
        if (topicSearchCO.id) {
            User user = topicSearchCO.getUser()
            return user.getSubscribedTopics()
        }
    }
}
