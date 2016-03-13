package com.ttnd.linksharing

import co.ResourceSearchCO
import enums.Visibility
import vo.PostVO
import vo.RatingInfoVO
import vo.TopicVO

class ResourceController {

    def resourceService

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
        Resource resource = Resource.get(id)
        if (session.user && resource) {
            def resourceDeleted = resourceService.deleteResource(resource, session.user)
            if (resourceDeleted) {
                flash.message = "Resource Deleted"
            } else {
                flash.error = "Resource not Deleted"
            }
        } else {
            flash.error = "Resource or User not Found"
        }
        redirect(controller: 'login', action: 'index')
    }

    def search(ResourceSearchCO co) {
        List<PostVO> searchPosts = []
        if (co.q) {
            co.visibility = Visibility.PUBLIC
        }
        List<Resource> resources = Resource.search(co).list()
        def result = ""
        resources?.each {
            searchPosts.add(Resource.getPost(it.id))
        }
        if (co.global) {
            render(view: 'search', model: [
                    topPosts      : Resource.getTopPosts(),
                    trendingTopics: Topic.getTrendingTopics(),
                    posts         : searchPosts])
        } else {
            searchPosts.each {
                result += g.render(template: '/templates/postPanel', model: [post: it])
            }
            render result
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
        render(view: 'show', model: [post: Resource.getPost(id), trendingTopics: Topic.getTrendingTopics()])
    }
}
