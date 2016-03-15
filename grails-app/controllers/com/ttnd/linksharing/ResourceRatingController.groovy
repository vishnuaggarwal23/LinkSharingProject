package com.ttnd.linksharing

import grails.converters.JSON

class ResourceRatingController {

    def resourceService

    def save(Long userId, Long resourceId, Integer score) {

        Map jsonResponse = [:]
        Resource resource = Resource?.get(resourceId)
        User user = User?.get(userId)
        if (user && resource) {
            if (resourceService.saveRating(resource, user, score)) {
                jsonResponse.message = "Resource Rated"
            } else {
                jsonResponse.error = "Resource not Rated"
            }
        }
        render jsonResponse as JSON
    }
}
