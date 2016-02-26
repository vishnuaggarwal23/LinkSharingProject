package com.ttnd.linksharing

import co.ResourceSearchCO
import enums.Visibility
import vo.RatingInfoVO

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
        if(co.q){
            co.visibility=Visibility.PUBLIC
        }
        List<Resource> resources = Resource.search(co).list()
        List<Resource> publicResources=Resource.publicTopicsSearch(co).list()
        render "Resources"
        resources.each{
            render "${it}->${it.topic}\n"
        }
        render "\n\n\nPublic Topic Resources"
        publicResources.each{
            render "${it}->${it.topic}\n"
        }
    }

    def show(Long id){
        Resource resource=Resource.get(id)
        RatingInfoVO vo=resource.getRatingInfo()
        render vo
    }

    def getTrendingTopics() {
        List result = Topic.getTrendingTopics()
        render "${result}"
    }
}
