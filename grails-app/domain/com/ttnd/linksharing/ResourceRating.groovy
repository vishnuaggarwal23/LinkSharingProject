package com.ttnd.linksharing

class ResourceRating {
    Integer score
    Date dateCreated
    Date lastUpdated
    static constraints = {
        score(min: 1, max: 5)
        resource(unique: ['user'])
    }

    static belongsTo = [user: User, resource: Resource]

    public static ResourceRating save(ResourceRating resourceRating) {
        resourceRating.validate()
        if (resourceRating.hasErrors()) {
            resourceRating.errors.each {
                log.error "error while saving resourceRating ${it.allErrors}"
            }
            return null
        } else {
            resourceRating.save(flush: true)
            return resourceRating
        }
    }
}
