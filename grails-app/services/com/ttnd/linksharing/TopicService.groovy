package com.ttnd.linksharing

import co.TopicCO
import co.TopicSearchCO
import enums.Visibility
import grails.transaction.Transactional
import vo.TopicVO

@Transactional
class TopicService {

    def userService
    def subscriptionService

    def saveTopic(Topic topic) {
        if (topic.validate()) {
            return topic.save(flush: true)
        } else {
            return null
        }
    }

    def isPublic(Topic topic) {
        if (topic) {
            return topic.visibility == Visibility.PUBLIC
        } else {
            return null
        }
    }

    def canViewedBy(Topic topic, User user) {
        if (user && topic) {
            return ((isPublic(topic)) || userService.isAdmin(user) || (Subscription.findByUserAndTopic(user,
                    topic)))
        } else {
            return null
        }
    }

    def isCreatedBy(Topic topic, User user) {
        if (user && topic) {
            return topic.createdBy.id == user.id
        } else {
            return null
        }
    }

    def search(TopicSearchCO topicSearchCO) {
        List<TopicVO> createdTopics = []
        if (topicSearchCO.id) {
            User user = topicSearchCO.getUser()
            List<Topic> topicList = Topic.createCriteria().list(max: topicSearchCO.max) {
                eq('createdBy.id', topicSearchCO.id)
                if (topicSearchCO.visibility)
                    eq('visibility', topicSearchCO.visibility)
            }
            topicList.each {
                topic ->
                    createdTopics.add(new TopicVO(id: topic.id, name: topic.name, visibility: topic
                            .visibility, createdBy: topic.createdBy))
            }
        }
        return createdTopics
    }

    def saveTopic(TopicCO topicCO) {
        Topic topic = topicCO.getTopic()
        if (topic) {
            if (topicCO.newName) {
                topic.name = topicCO.newName
            } else {
                topic.visibility = Visibility.checkVisibility(topicCO.visibility)
            }
            return saveTopic(topic)
        } else {
            return null
        }
    }

    def joinTopic(Topic topic, User user) {
        if (topic && user) {
            return subscriptionService.saveSubscription(new Subscription(user: user, topic: topic))
        } else {
            return null
        }
    }

    def deleteTopic(Topic topic, User user) {
        if (user && topic) {
            if (userService.isAdmin(user) || isCreatedBy(topic, user)) {
                topic.delete(flush: true)
                return true
            } else {
                return false
            }
        } else {
            return null
        }
    }
}
