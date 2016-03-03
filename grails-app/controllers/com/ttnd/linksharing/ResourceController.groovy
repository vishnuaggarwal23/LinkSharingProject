package com.ttnd.linksharing

import co.ResourceSearchCO
import enums.Visibility
import vo.PostVO
import vo.RatingInfoVO
import vo.TopicVO

class ResourceController {

    def index() {
        List<TopicVO> topicVOList = Topic.getTrendingTopics()
        render(view: 'index1', model: [topicVOList: topicVOList])
    }

    def show(Long id) {
        List<TopicVO> topicVOList = Topic.getTrendingTopics()
        PostVO post = Resource.getPost(id)
        render(view: 'show', model: [trendingTopics: topicVOList, post: post])
    }

    def delete(Integer id) {
        Resource resource = Resource.load(id)
        try {
            resource.delete(flush: true)
            render "Resource Deleted"
        }
        catch (Exception e) {
            log.info e.message
            render "Resource not Deleted"
        }
    }

    def search(ResourceSearchCO co) {
        if (co.q) {
            co.visibility = Visibility.PUBLIC
        }
        List<Resource> resources = Resource.search(co).list()
        List<Resource> publicResources = Resource.publicTopicsSearch(co).list()
        render "Resources"
        resources.each {
            render "${it}->${it.topic}\n"
        }
        render "\n\n\nPublic Topic Resources"
        publicResources.each {
            render "${it}->${it.topic}\n"
        }
    }

    def getRatingInfo(Long id) {
        Resource resource = Resource.get(id)
        RatingInfoVO vo = resource.getRatingInfo()
        render vo
    }

/*
    def getTrendingTopics() {
        List result = Topic.getTrendingTopics()
        render "${result}"
    }
*/

    /*def saveDocumentResource(String filePath, String description, String topicName) {
        User user = session.user
        Topic topic = Topic.findByNameAndCreatedBy(topicName, user)
        Resource documentResource = new DocumentResource(filePath: filePath, description: description, createdBy:
                user, topic: topic)
        if (documentResource.validate()) {
            documentResource.save(flush: true)
            flash.message = "Document Resource Saved"
            render flash.message
        } else {
            flash.error = "Document Resource not Saved"
            redirect(controller: 'user', action: 'index')
            render flash.error
        }
    }*/
}
