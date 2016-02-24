package com.ttnd.linksharing

import co.ResourceSearchCO

abstract class Resource {
    String description
    User createdBy
    Date dateCreated
    Date lastUpdated

    static mapping = {
        description(type: 'text')
    }

    static constraints = {
        description(blank: false)
    }

    static hasMany = [resourceRating: ResourceRating, readingItems: ReadingItem]
    static belongsTo = [topic: Topic]

    String toString() {
        return "${topic} has resource -> ${description}"
    }

    public static Resource save(Resource resource) {
        resource.validate()
        if (resource.hasErrors()) {
            resource.errors.each {
                log.error "error while saving resource ${it}--- ${it.allErrors}"
            }
            return null
        } else {
            resource.save(flush: true, failOnError: true)
            return resource
        }
    }

    static namedQueries = {
        search { ResourceSearchCO co ->
            if (co.topicID&&co.visibility) {
                eq('topic.id', co.topicID)
                'topic'{
                    eq('topic.visibility',co.visibility)
                }
            }
        }

        publicTopicsSearch { ResourceSearchCO co ->
            if (co.topicID) {
                    eq('topic.id', co.topicID)
                    eq('this.topic.visibility', "PUBLIC")


            }
        }
    }
}