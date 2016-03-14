package com.ttnd.linksharing

import grails.transaction.Transactional

@Transactional
class SubscriptionService {

    Subscription saveSubscription(Subscription subscription) {
        if (subscription.validate()) {
            return subscription.save(flush: true)
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
