package com.ttnd.linksharing

class ResourceRatingController {

    def index() {}

    def save(Long id, Integer score) {
        Resource resource = Resource.read(id)
        User user = session.user
        ReadingItem readingItem = ReadingItem.findByUserAndResource(user, resource)
        if (readingItem && !readingItem.isRead) {
            ResourceRating resourceRating = new ResourceRating(user: user, resource: resource, score: score)
            if (ResourceRating.save(resourceRating)) {
                flash.message = "Resource Rated"
            } else {
                flash.error = "Resource is not Rated"
            }
        } else {
            flash.error = "Resource can not be rated"
        }
        //redirect(controller: 'resource', action: 'show', params: [id: id])
        redirect(uri: '/')
    }
}
