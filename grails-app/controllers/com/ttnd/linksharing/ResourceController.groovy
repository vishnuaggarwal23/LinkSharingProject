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
        /*Resource resource=Resource.load(id)
        if(resource){
            resource.delete(flush: true)
            render "Resource Deleted"
        }
        else{
            render "Resource not deleted--- ${resource.errors.allErrors.collect { message(error: it) }.join(',')}"
        }*/
    }

    def search(ResourceSearchCO co) {
        List<Resource> resources = Resource.search(co).list()
        render resources
    }
}
