package com.ttnd.linksharing

import constants.AppConstants
import grails.transaction.Transactional

class DocumentResourceController extends ResourceController {

    def index() {}

    /*def saveDocumentResource(String filePath, String description, String topicName) {
        User user = session.user
        Topic topic = Topic.findByNameAndCreatedBy(topicName, user)
        Resource documentResource = new DocumentResource(filePath: filePath, description: description, createdBy:
                user, topic: topic)
        if (documentResource.validate()) {
            documentResource.save(flush: true)
            flash.message = "Document Resource Saved"
            render flash.message
        } else {
            flash.error = "Document Resource not Saved"
            redirect(controller: 'user', action: 'index')
            render flash.error
        }
    }*/

    /*def save(DocumentResource documentResource) {
        documentResource.createdBy = session.user
        log.info documentResource.filePath
        log.info documentResource
        log.info params.filePath
        MultipartFile uploadDocument = documentResource.filePath
        String uploadPath = "${grailsApplication.config.grails.serverPath}/${uploadDocument.originalFilename}"
        log.info uploadPath
        File uploadFile = new File(uploadPath)
        uploadFile.bytes = uploadDocument.bytes
        documentResource.filePath = uploadPath
        log.info documentResource.filePath
        if (documentResource.validate()) {
            documentResource.save(flush: true)
            flash.message = "Document Uploaded"
        } else {
            log.error "${documentResource.errors.allErrors}"
            flash.error = "Document not Uploaded"
        }
        redirect(controller: 'login', action: 'index')
    }*/

    @Transactional
    def save(DocumentResource documentResource) {
        documentResource.createdBy = session.user
        def file = params.file
        if (file.empty) {
            flash.error = "File is Empty"
        } else {
            String path = "/home/vishnu${grailsApplication.config.grails.serverPath}/${UUID.randomUUID()}.pdf"
            documentResource.contentType = params.file.contentType
            documentResource.filePath = path
            if (documentResource.validate()) {
                documentResource.save(flush: true)
                File destinationFile = new File(path)
                params.file.transferTo(destinationFile)
                flash.message = "Document Resource Saved"
            } else {
                flash.error = "Resource not Saved -- ${documentResource.errors.allErrors}"
            }
            //addToReadingItems(documentResource)
        }
        redirect(controller: 'login', action: 'index')
    }

    def download(Long resourceId) {
        DocumentResource documentResource = (DocumentResource) Resource.get(resourceId)
        if (documentResource && documentResource.topic.canViewedBy(session.user)) {
            def file = new File(documentResource.filePath)
            if (file.exists()) {
                response.setContentType(AppConstants.DOCUMENT_CONTENT_TYPE)
                response.setHeader("Content-disposition", "attachment;filename=\"${documentResource.getFileName()}\"")
                response.outputStream << file.bytes
            } else {
                flash.error = "resource not found"
            }
        } else {
            flash.error = "resource not found"
        }
        redirect(controller: 'login', action: 'index')
    }
}
