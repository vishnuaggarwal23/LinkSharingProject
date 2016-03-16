package com.ttnd.linksharing

import co.ResourceSearchCO
import enums.Visibility
import vo.PostVO
import vo.RatingInfoVO
import vo.TopicVO
import vo.conversion.DomainToVO

class ResourceController {

    def resourceService

    def show(Long id) {
        Resource resource = Resource?.get(id)
        if (resource) {
            User user = session.user
            PostVO post = resourceService.show(resource, user)
            if (post) {
                if (user) {
                    post.resourceRating = user?.getScore(resource)
                    List<TopicVO> trendingTopicsVO = DomainToVO.trendingTopics()
                    render(view: 'show', model: [trendingTopics: trendingTopicsVO,
                                                 post          : post])
                } else {
                    render(view: 'show', model: [post: post])
                }
            } else {
                flash.error = g.message(code: "com.ttnd.linksharing.resource.show.resource.can.not.show")
                redirect(controller: 'login', action: 'index')
            }
        } else {
            flash.error = g.message(code: "com.ttnd.linksharing.resource.show.resource.not.found")
            redirect(controller: 'login', action: 'index')
        }
    }

    def delete(Long id) {
        Resource resource = Resource.get(id)
        if (session.user && resource) {
            if (resourceService.deleteResource(resource, session.user)) {
                flash.message = g.message(code: "com.ttnd.linksharing.resource.delete.resource.deleted")
            } else {
                flash.error = g.message(code: "com.ttnd.linksharing.resource.delete.resource.not.deleted")
            }
        } else {
            flash.error = g.message(code: "com.ttnd.linksharing.resource.delete.resource.user.not.set")
        }
        redirect(controller: 'login', action: 'index')
    }

    def search(ResourceSearchCO co) {
        List<PostVO> searchPosts = []
        if (co.q && !co.topicID) {
            co.visibility = Visibility.PUBLIC
        }
        List<Resource> resources = Resource.search(co).list()
        def result = ""
        resources?.each {
            searchPosts.add(DomainToVO.post(it))
        }
        if (co.global) {
            render(view: 'search', model: [
                    topPosts      : DomainToVO.topPosts(),
                    trendingTopics: DomainToVO.trendingTopics(),
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
            Resource resource = Resource?.get(id)
            if (resource) {
                if (resourceService.editResourceDescription(resource, description)) {
                    flash.message = g.message(code: "com.ttnd.linksharing.resource.save.resource.description.updated")
                } else {
                    flash.error = g.message(code: "com.ttnd.linksharing.resource.save.resource.description.not.updated")
                }
            } else {
                flash.error = g.message(code: "com.ttnd.linksharing.resource.save.resource.not.found")
            }
        } else {
            flash.error = g.message(code: "com.ttnd.linksharing.resource.save.user.not.set")
        }
        render(view: 'show', model: [post          : DomainToVO.post(Resource?.get(id)),
                                     trendingTopics: DomainToVO.trendingTopics()])
    }
}
