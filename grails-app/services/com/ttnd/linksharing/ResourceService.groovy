package com.ttnd.linksharing

import co.ResourceSearchCO
import constants.AppConstants
import grails.transaction.Transactional
import vo.PostVO

@Transactional
class ResourceService {

    def userService
    def topicService
    def grailsApplication

    def saveResource(Resource resource) {
        if (resource.validate()) {
            return resource.save(flush: true)
        } else {
            return null
        }
    }

    def saveResource(DocumentResource documentResource, def sourceFile, def destinationFile) {
        if (documentResource.validate()) {
            sourceFile.transferTo(destinationFile)
            return documentResource.save(flush: true)
        } else {
            return null
        }
    }

    def saveResource(LinkResource linkResource) {
        if (linkResource.validate()) {
            return linkResource.save(flush: true)
        } else {
            return null
        }
    }

    def deleteResource(Resource resource) {
        resource.delete(flush: true)
    }

    def isCreatedBy(Resource resource, User user) {
        if (user && resource) {
            return resource.createdBy.id == user.id
        } else {
            return null
        }
    }

    def canEditDeleteResource(Resource resource, User user) {
        if (user && resource) {
            return ((userService.isAdmin(user)) || (isCreatedBy(resource, user)))
        } else {
            return null
        }
    }

    def canViewedBy(Resource resource, User user) {
        if (user && resource) {
            return topicService.canViewedBy(resource.topic, user)
        } else {
            return null
        }
    }

    def checkType(Resource resource) {
        if (resource) {
            if (resource.class.equals(LinkResource)) {
                return AppConstants.LINK_RESOURCE_TYPE
            } else if (resource.class.equals(DocumentResource)) {
                return AppConstants.DOCUMENT_RESOURCE_TYPE
            }
        } else {
            return null
        }
    }

    def show(Resource resource, User user) {
        Boolean canView = false
        if (resource) {
            if (user) {
                if (canViewedBy(resource, user)) {
                    canView = true
                }
            } else {
                if (topicService.isPublic(resource.topic)) {
                    canView = true
                }
            }
            if (canView) {
                return Resource.getPost(resource.id)
            } else {
                return null
            }
        } else {
            return null
        }
    }

    def search(ResourceSearchCO resourceSearchCO) {
        List<PostVO> resources = []
        Resource.search(resourceSearchCO).list([max: resourceSearchCO.max, offset: resourceSearchCO.offset]).each {
            resources.add(Resource.getPost(it.id))
        }
        return resources
    }

    def editResourceDescription(Resource resource, String description) {
        if (resource && description) {
            resource.description = description
            return saveResource(resource)
        } else {
            return null
        }
    }

    def deleteResource(Resource resource, User user) {
        if (Resource) {
            if (canEditDeleteResource(resource, user)) {
                def resourceType = checkType(resource)
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

    def uploadDocumentResource(DocumentResource documentResource, def sourceFile) {
        if (sourceFile.empty) {
            return null
        } else {
            String filePath = "${getFileServerPath()}${getUniqueID()}.pdf"
            documentResource.contentType = AppConstants.DOCUMENT_CONTENT_TYPE
            documentResource.filePath = filePath
            def destinationFile = new File(filePath)
            if (destinationFile) {
                return saveResource(documentResource, sourceFile, destinationFile)
            }
        }
    }

    def downloadDocumentResource(DocumentResource documentResource, User user) {
        if (user && documentResource && topicService.canViewedBy(documentResource.topic, user)) {
            def file = new File(documentResource.filePath)
            if (file.exists()) {
                return file
            } else {
                return null
            }
        } else {
            return null
        }
    }

    def getFileServerPath() {
        return "${grailsApplication.config.grails.serverPath}/"
    }

    def getUniqueID() {
        return UUID.randomUUID()
    }

    def addToReadingItems(Resource resource, User sessionUser) {
        List<User> subscribedUsers = Subscription.createCriteria().list {
            projections {
                property('user')
            }
            eq('topic', resource.topic)
        }
        subscribedUsers.each {
            if (it.id != sessionUser.id) {
                resource.addToReadingItems(new ReadingItem(user: it, resource: resource, isRead: false))
            }
        }
    }

}
