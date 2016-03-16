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
                jsonResponse.message = g.message(code: "com.ttnd.linksharing.resource.rating.rating.saved")
            } else {
                jsonResponse.error = g.message(code: "com.ttnd.linksharing.resource.rating.rating.not.saved")
            }
        } else {
            jsonResponse.error = g.message(code: "com.ttnd.linksharing.resource.rating.resource.user.not.set")
        }
        render jsonResponse as JSON
    }
}
