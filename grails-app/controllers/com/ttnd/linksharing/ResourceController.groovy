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
        Resource resource = Resource.get(id)
        if (resource) {
            User user = session.user
            PostVO post = resourceService.show(resource, user)
            if (post) {
                if (user) {
                    post.resourceRating = user?.getScore(resource)

                    /*List<TopicVO> trendingTopics = Topic.getTrendingTopics()*/

                    List<TopicVO> trendingTopicsVO = DomainToVO.trendingTopics()
                    render(view: 'show', model: [trendingTopics: trendingTopicsVO, post: post])
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
            if (resourceService.deleteResource(resource, session.user)) {
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
            Resource resource = Resource.get(id)
            if (resource) {
                if (resourceService.editResourceDescription(resource, description)) {
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
        render(view: 'show', model: [post: DomainToVO.post(Resource?.get(id)), trendingTopics: DomainToVO.trendingTopics()])
    }
}
