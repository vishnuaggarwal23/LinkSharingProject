package com.ttnd.linksharing

import co.TopicSearchCO
import grails.transaction.Transactional

@Transactional
class SubscriptionService {

    def serviceMethod() {

    }

    def search(TopicSearchCO topicSearchCO){
        if(topicSearchCO.id){
            User user=topicSearchCO.getUser()
            return user.getSubscribedTopics()
        }
    }
}
