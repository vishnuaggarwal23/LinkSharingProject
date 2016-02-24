package com.ttnd.linksharing

import co.ResourceSearchCO

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
        List<Resource> resources = Resource.search(co).list()
        List<Resource> publicResources=Resource.publicTopicsSearch(co).list()
        //render "Resources-> ${resources}\n\n"
        render "Resources"
        resources.each{
            render "${it}->${it.topic}\n"
        }
        //render "Public Topic Resources-> ${publicResources}"
        render "\n\n\nPublic Topic Resources"
        publicResources.each{
            render "${it}->${it.topic}\n"
        }
    }
}
