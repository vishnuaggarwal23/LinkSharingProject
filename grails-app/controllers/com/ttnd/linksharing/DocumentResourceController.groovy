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
                flash.message = "Resource Saved"
            } else {
                flash.error = "Resource not Saved"
            }
        } else {
            flash.error = "Session user not set"
        }
        redirect(controller: 'login', action: 'index')
    }

    def download(Long id) {
        DocumentResource documentResource = (DocumentResource) Resource.get(id)
        def file = resourceService.downloadDocumentResource(documentResource, session.user)
        if (file) {
            response.setContentType(AppConstants.DOCUMENT_CONTENT_TYPE)
            response.setHeader("Content-disposition", "attachment;filename=\"${documentResource.getFileName()}\"")
            response.outputStream << file.bytes
        } else {
            flash.error = "Resource not Available"
        }
        redirect(controller: 'login', action: 'index')
    }

    //TDOD: add to reading items to be implemented
}
