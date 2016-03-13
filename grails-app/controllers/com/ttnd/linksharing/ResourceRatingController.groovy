package com.ttnd.linksharing

class ResourceRatingController {

    def resourceService

    def save(Long id, Integer score) {
        Resource resource = Resource.read(id)
        User user = session.user
        ReadingItem readingItem = ReadingItem.findByUserAndResource(user, resource)
        if (readingItem) {
            ResourceRating resourceRating = ResourceRating.findByUserAndResource(user, resource)
            if (resourceRating) {
                resourceRating.score = score
                if (resourceService.saveResourceRating(resourceRating)) {
                    flash.message = "Resource Rated"
                } else {
                    flash.error = "Resource is not Rated"
                }
            } else {
                resourceRating = new ResourceRating(user: user, resource: resource, score: score)
                if (resourceService.saveResourceRating(resourceRating)) {
                    flash.message = "Resource Rated"
                } else {
                    flash.error = "Resource is not Rated"
                }
            }
        } else {
            flash.error = "Resource can not be rated"
        }
        redirect(uri: '/')
    }
}
