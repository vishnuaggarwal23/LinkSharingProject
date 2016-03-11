package com.ttnd.linksharing

import co.TopicSearchCO
import grails.transaction.Transactional
import vo.TopicVO

@Transactional
class TopicService {

    def serviceMethod() {

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
}
