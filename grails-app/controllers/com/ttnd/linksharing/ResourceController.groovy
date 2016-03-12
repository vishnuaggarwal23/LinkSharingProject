package com.ttnd.linksharing

import co.ResourceSearchCO
import enums.Visibility
import vo.PostVO
import vo.RatingInfoVO
import vo.TopicVO

class ResourceController {

    def resourceService

    def index() {
        List<TopicVO> topicVOList = Topic.getTrendingTopics()
        render(view: 'index1', model: [topicVOList: topicVOList])
    }

    /*def show(Long id) {
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
    }*/

    def show(Long id) {
        Resource resource = Resource.get(id)
        if (resource) {
            User user = session.user
            PostVO post = resourceService.show(resource, user)
            if (post) {
                if (user) {
                    post.resourceRating = user?.getScore(resource)
                    List<TopicVO> trendingTopics = Topic.getTrendingTopics()
                    render(view: 'show', model: [trendingTopics: trendingTopics, post: post])
                } else {
                    render(view: 'show', model: [post: post])
                }

            } else {
                flash.error = "Resource can not be shown"
                redirect(controller: 'login', action: 'index')
            }
        } else {
            flash.error = "Resource can not be shown"
            redirect(controller: 'login', action: 'index')
        }

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
        List<PostVO> searchPosts = []
        List<Resource> resources = Resource.search(co).list()
        def result = ""
        /*max: co.max, offset: co.offset, sort: co.sort,order: co.order)*/
        resources?.each {
            searchPosts.add(Resource.getPost(it.id))
        }
        searchPosts.each {
            result += g.render(template: '/templates/postPanel', model: [post: it])
        }
        render result
    }

    def search1(ResourceSearchCO resourceSearchCO) {
        String result = ""
        List<PostVO> postVOList = []
        if (resourceSearchCO.q) {
            postVOList = Resource.search(resourceSearchCO).list().collect({
                Resource.getPost(it.id)
            })
        }
        if (resourceSearchCO.visibility == Visibility.PUBLIC) {
            render(view: 'search', model: [postVOList: postVOList, trendingTopics: Topic.getTrendingTopics()])
        } else {
            postVOList.each {
                result += g.render(template: '/templates/postPanel', model: [post: it])
            }
            if (postVOList.size() == 0) {
                result = "<h1>No resource found<h1>"
            }
            render(result)
        }
    }

    def getRatingInfo(Long id) {
        Resource resource = Resource.get(id)
        RatingInfoVO vo = resource.getRatingInfo()
        render vo
    }

    def save(Long id, String description) {
        if (session.user) {
            Resource resource = Resource.get(id)
            if (resource) {
                Resource tempResource = resourceService.editResourceDescription(resource, description)
                if (tempResource) {
                    flash.message = "Resource Description Updated"
                } else {
                    flash.error = "Resource Description is not Updated"
                }
            } else {
                flash.error = "Resource not Found"
            }
        } else {
            flash.error = "Session User not Set"
        }
        render(view: 'show', params: [id: id])
    }

    void addToReadingItems(Resource resource) {
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
