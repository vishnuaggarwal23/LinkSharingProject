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
        Resource resource = Resource.get(id)
        User user = session.user
        if (resource) {
            if (resource.canViewBy(user)) {
                List<TopicVO> topicVOList = Topic.getTrendingTopics()
                PostVO post = Resource.getPost(id)
                if (session.user)
                    post.resourceRating = user?.getScore(resource)
                flash.message = "User Can Access the Resource"
                render(view: 'show', model: [trendingTopics: topicVOList, post: post])
            } else {
                flash.error = "User Access not Permitted"
                redirect(uri: '/')
            }
        } else {
            flash.error = "Resource does not Exists"
            redirect(uri: '/')
        }
        /*try {
            if (resource.canViewBy(user)) {
                List<TopicVO> topicVOList = Topic.getTrendingTopics()
                PostVO post = Resource.getPost(id)
                post.resourceRating = user.getScore(resource)
                //Integer resourceScore =
                def resourceType = Resource.checkResourceType(id)
                flash.message = "User Can Access the Resource"
                render(view: 'show', model: [trendingTopics: topicVOList, post: post])
            } else {
                flash.error = "User Access not Permitted"
                redirect(uri: '/')
            }
        }
        catch (Exception e) {
            log.error e.message
            flash.error = "Resource does not Exists"
            redirect(uri: '/')
        }*/
    }

    def delete(Long id) {
        if (User.canDeleteResource(session.user, id)) {
            Resource resource = Resource.load(id)
            try {
                if (resource.deleteFile())
                    flash.message = "Resource Deleted"
                else
                    flash.error = "Resource not Deleted"
            }
            catch (Exception e) {
                log.info e.message
                flash.error = "Resource Not Found"
            }
        } else {
            flash.error = "Deletion Not Permissible"
        }
        redirect(uri: '/')
    }

    def search(ResourceSearchCO co) {
        List<PostVO> searchPosts=[]
        List<Resource> resources = Resource.search(co).list()
        def result=""
        /*max: co.max, offset: co.offset, sort: co.sort,order: co.order)*/
        resources?.each {
            searchPosts.add(Resource.getPost(it.id))
        }
        searchPosts.each{
            result+=g.render(template: '/templates/postPanel',model: [post:it])
        }
        render result
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

    void addToReadingItems(Resource resource) {
        /*Topic topic = Resource.createCriteria().get {
            projections {
                property('topic')
            }
            eq('topic', resource.topic)
        }
        List<User> subscribedUserList = Topic.getSubscribedUsers(topic)*/
        Topic topic = Resource.createCriteria().get {
            projections {
                property('topic')
            }
            eq('topic', resource.topic)
        }
        List<User> subscribedUserList = Subscription.createCriteria().list {
            projections {
                property('user')
            }
            eq('topic', topic)
        }
        subscribedUserList.each { user ->
            if (user.id == session.user?.id)
                resource.addToReadingItems(new ReadingItem(user: user, resource: resource, isRead: true))
            else
                resource.addToReadingItems(new ReadingItem(user: user, resource: resource, isRead: false))
        }
    }
}
