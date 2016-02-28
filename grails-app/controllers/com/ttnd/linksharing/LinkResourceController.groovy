package com.ttnd.linksharing

class LinkResourceController {
    def save(LinkResource linkResource) {
        linkResource.createdBy = session.user
        if (linkResource.save(flush: true)) {
            flash.message = "Link Resource Saved"
        } else {
            flash.error = linkResource.errors.allErrors.collect { message(error: it) }
        }
        redirect(controller: 'user', action: 'index')
    }
}
