package com.ttnd.linksharing

class LinkResourceController {

    def resourceService

    def save(LinkResource linkResource) {
        linkResource.createdBy = session.user
        LinkResource tempResource = resourceService.saveResource(linkResource)
        if (tempResource) {
            resourceService.addToReadingItems(linkResource, session.user)
            flash.message = "Resource Saved"
        } else {
            flash.error = "Resource not Saved"
        }
        redirect(controller: 'user', action: 'index')
    }
}
