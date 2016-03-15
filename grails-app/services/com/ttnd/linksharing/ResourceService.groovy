package com.ttnd.linksharing

import constants.AppConstants
import grails.transaction.Transactional
import util.Linksharing
import vo.conversion.DomainToVO

@Transactional
class ResourceService {

    def userService
    def topicService
    def grailsApplication

    Resource saveResource(Resource resource) {
        if (resource.validate()) {
            return resource.save(flush: true)
        }
        return null
    }

    DocumentResource saveResource(DocumentResource documentResource, def sourceFile, def destinationFile) {
        if (documentResource.validate()) {
            sourceFile.transferTo(destinationFile)
            return documentResource.save(flush: true)
        }
        return null
    }

    LinkResource saveResource(LinkResource linkResource) {
        if (linkResource.validate()) {
            return linkResource.save(flush: true)
        }
        return null
    }

    ReadingItem saveReadingItem(ReadingItem readingItem) {
        if (readingItem.validate()) {
            return readingItem.save(flush: true)
        }
        return null
    }

    ResourceRating saveResourceRating(ResourceRating resourceRating) {
        if (resourceRating.validate()) {
            return resourceRating.save(flush: true)
        }
        return null
    }

    def deleteResource(Resource resource) {
        resource.delete(flush: true)
    }

    def show(Resource resource, User user) {
        Boolean canView = false
        if (resource) {
            if (user) {
                if (Linksharing.ifResourceCanBeViewdBy(resource, user)) {
                    canView = true
                }
            } else {
                if (Linksharing.isTopicPublic(resource.topic)) {
                    canView = true
                }
            }
            if (canView) {
                return DomainToVO.post(resource)
            }
        }
        return null
    }

    /*def search(ResourceSearchCO resourceSearchCO) {
        List<PostVO> resources = []
        Resource.search(resourceSearchCO).list([max   : resourceSearchCO.max,
                                                offset: resourceSearchCO.offset]).each {
            resources.add(DomainToVO.post(it))
        }
        return resources
    }*/

    Resource editResourceDescription(Resource resource, String description) {
        if (resource && description) {
            resource.description = description
            return saveResource(resource)
        }
        return null
    }

    def deleteResource(Resource resource, User user) {
        if (Resource) {
            if (Linksharing.ifUserCanEditDeleteResource(resource, user)) {
                def resourceType = Linksharing.checkResourceType(resource)
                if (resourceType == AppConstants.DOCUMENT_RESOURCE_TYPE) {
                    DocumentResource documentResource = (DocumentResource) resource
                    String filePath = documentResource.filePath
                    if (filePath && (new File(filePath).delete())) {
                        deleteResource(resource)
                        return true
                    } else {
                        return false
                    }
                } else if (resourceType == AppConstants.LINK_RESOURCE_TYPE) {
                    deleteResource(resource)
                    return true
                }
            }
        }
        return null
    }

    DocumentResource uploadDocumentResource(DocumentResource documentResource, def sourceFile) {
        if (sourceFile.empty) {
            return null
        } else {
            String filePath = "${grailsApplication.config.grails.serverPath}/${Linksharing.uniqueFileName()}.pdf"
            documentResource.contentType = AppConstants.DOCUMENT_CONTENT_TYPE
            documentResource.filePath = filePath
            def destinationFile = new File(filePath)
            if (destinationFile) {
                return saveResource(documentResource, sourceFile, destinationFile)
            }
        }
    }

    File downloadDocumentResource(DocumentResource documentResource, User user) {
        if (user && documentResource && Linksharing.ifTopicCanbeViewdBy(documentResource.topic, user)) {
            def file = new File(documentResource.filePath)
            if (file.exists()) {
                return file
            }
        }
        return null
    }

    def addToReadingItems(Resource resource, User sessionUser) {
        List<User> subscribedUsers = resource.topic.subscribedUsers()
        subscribedUsers.each {
            if (it.id != sessionUser.id) {
                resource.addToReadingItems(new ReadingItem(
                        user: it,
                        resource: resource,
                        isRead: false)
                )
            }
        }
    }

    ResourceRating saveRating(Resource resource, User user, Integer score) {
        if (user && resource) {
            ResourceRating resourceRating = ResourceRating.findOrCreateByResourceAndUser(resource, user)
            if (resourceRating) {
                resourceRating.score = score
                return saveResourceRating(resourceRating)
            }
        }
        return null
    }
}
