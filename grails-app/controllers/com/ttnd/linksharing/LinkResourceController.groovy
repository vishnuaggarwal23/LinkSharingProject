package com.ttnd.linksharing

class LinkResourceController {

    def resourceService

    def save(LinkResource linkResource) {
        linkResource.createdBy = session.user
        LinkResource tempResource=resourceService.saveResource(linkResource)
        if(tempResource){
            resourceService.addToReadingItems(linkResource,session.user)
            flash.message="Resource Saved"
        }
        else{
            flash.error="Resource not Saved"
        }
        /*if (linkResource.save(flush: true)) {
            flash.message = "Link Resource Saved"
        } else {
            flash.error = linkResource.errors.allErrors.collect { message(error: it) }
        }*/
        redirect(controller: 'user', action: 'index')
    }
}
