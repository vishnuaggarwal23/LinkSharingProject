package com.ttnd.linksharing

class ResourceController {

    def index() {}

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
}
