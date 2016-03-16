package com.ttnd.linksharing

class LinkResourceController {

    def resourceService

    def save(LinkResource linkResource) {
        linkResource.createdBy = session.user
        LinkResource tempResource = resourceService.saveResource(linkResource)
        if (tempResource) {
            resourceService.addToReadingItems(linkResource, session.user)
            flash.message = g.message(code: "com.ttnd.linksharing.link.resource.save.resource.saved")
        } else {
            flash.error = g.message(code: "com.ttnd.linksharing.link.resource.save.resource.not.saved")
        }
        redirect(url: request.getHeader("referer"))
    }
}
