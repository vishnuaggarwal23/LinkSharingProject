package com.ttnd.linksharing

import constants.AppConstants

class DocumentResourceController {

    def resourceService

    def upload(DocumentResource documentResource) {
        if (session.user) {
            documentResource.createdBy = session.user
            def file = params.file
            DocumentResource tempResource = resourceService.uploadDocumentResource(documentResource, file)
            if (tempResource) {
                resourceService.addToReadingItems(tempResource, session.user)
                flash.message = g.message(code: "com.ttnd.linksharing.document.resource.upload.resource.saved")
            } else {
                flash.error = g.message(code: "com.ttnd.linksharing.document.resource.upload.resource.not.saved")
            }
        } else {
            flash.error = g.message(code: "com.ttnd.linksharing.document.resource.upload.user.not.set")
        }
        redirect(url: request.getHeader("referer"))
    }

    def download(Long id) {
        DocumentResource documentResource = (DocumentResource) Resource.get(id)
        def file = resourceService.downloadDocumentResource(documentResource, session.user)
        if (file) {
            response.setContentType(AppConstants.DOCUMENT_CONTENT_TYPE)
            response.setHeader("Content-disposition", "attachment;filename=\"${documentResource.getFileName()}\"")
            response.outputStream << file.bytes
        } else {
            flash.error = g.message(code: "com.ttnd.linksharing.document.resource.download.resource.not.found")
        }
        redirect(url: request.getHeader("referer"))
    }
}
