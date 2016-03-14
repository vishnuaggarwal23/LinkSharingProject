package com.ttnd.linksharing

import co.TopicCO
import co.TopicSearchCO
import enums.Visibility
import grails.transaction.Transactional
import util.Linksharing
import vo.TopicVO

@Transactional
class TopicService {

    def subscriptionService

    Topic saveTopic(Topic topic) {
        if (topic.validate()) {
            return topic.save(flush: true)
        }
        return null
    }

    def search(TopicSearchCO topicSearchCO) {
        List<TopicVO> createdTopics = []
        if (topicSearchCO.id) {
            List<Topic> topicList = Topic.createCriteria().list(max: topicSearchCO.max) {
                eq('createdBy.id', topicSearchCO.id)
                if (topicSearchCO.visibility) {
                    eq('visibility', topicSearchCO.visibility)
                }
            }
            topicList.each {
                topic ->
                    createdTopics.add(new TopicVO(id: topic.id, name: topic.name, visibility: topic
                            .visibility, createdBy: topic.createdBy))
            }
        }
        return createdTopics
    }

    Topic saveTopic(TopicCO topicCO) {
        Topic topic = topicCO.getTopic()
        if (topic) {
            if (topicCO.newName) {
                topic.name = topicCO.newName
            } else {
                topic.visibility = Visibility.checkVisibility(topicCO.visibility)
            }
            return saveTopic(topic)
        }
        return null
    }

    Subscription joinTopic(Topic topic, User user) {
        if (topic && user) {
            return subscriptionService.saveSubscription(new Subscription(user: user, topic: topic))
        }
        return null
    }

    def deleteTopic(Topic topic, User user) {
        if (user && topic) {
            if (Linksharing.isUserAdmin(user) || Linksharing.ifTopicIsCreatedBy(topic, user)) {
                topic.delete(flush: true)
                return true
            } else {
                return false
            }
        }
        return null
    }
}
