package com.ttnd.linksharing

import co.ResourceSearchCO
import enums.Visibility
import vo.RatingInfoVO
import vo.TopicVO

class ResourceController {

    def index() {}

    def delete(Integer id) {
        try {
            Resource resource = Resource.load(id)
            resource.delete(flush: true)
            render "Resource Deleted"
        }
        catch (Exception e) {
            render e.message
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

    def show(Long id) {
        Resource resource = Resource.get(id)
        RatingInfoVO vo = resource.getRatingInfo()
        render vo
    }

    def getTrendingTopics() {
        List result = Topic.getTrendingTopics()
        render "${result}"
    }

    def saveLinkResource(LinkResource linkResource) {
        linkResource.createdBy = session.user
        if (linkResource.save(flush: true)) {
            flash.message = "Link Resource Saved"
        } else {
            flash.error = linkResource.errors.allErrors.collect { message(error: it) }
        }
        redirect(controller: 'user', action: 'index')
    }

    def saveDocumentResource(String filePath, String description, String topicName) {
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
    }
}
