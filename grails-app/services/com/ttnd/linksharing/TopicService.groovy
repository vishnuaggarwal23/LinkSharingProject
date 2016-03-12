package com.ttnd.linksharing

import co.TopicCO
import co.TopicSearchCO
import enums.Visibility
import grails.transaction.Transactional
import vo.TopicVO

@Transactional
class TopicService {

    def saveTopic(Topic topic) {
        if (topic.validate()) {
            return topic.save(flush: true)
        } else {
            return null
        }

    }

    def search(TopicSearchCO topicSearchCO) {
        List<TopicVO> createdTopics = []
        /*if (topicSearchCO.id) {
            User user = topicSearchCO.getUser()
            if (user) {
                user.getCreatedTopics().each {
                    createdTopics.add(new TopicVO(id: it.id, visibility: it.visibility, name: it.name, createdBy: it.createdBy))
                }
            }
        }*/

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
}
