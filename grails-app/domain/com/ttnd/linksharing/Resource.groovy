package com.ttnd.linksharing

import co.ResourceSearchCO
import enums.Visibility
import vo.RatingInfoVO

abstract class Resource {
    String description
    User createdBy
    Date dateCreated
    Date lastUpdated

    static mapping = {
        description(type: 'text')
    }

    static transients = ['ratingInfo']

    static constraints = {
        description(blank: false)
    }

    static hasMany = [resourceRating: ResourceRating, readingItems: ReadingItem]
    static belongsTo = [topic: Topic]

    public static Resource save(Resource resource) {
        resource.validate()
        if (resource.hasErrors()) {
            resource.errors.each {
                log.error "error while saving resource ${it}--- ${it.allErrors}"
            }
            return null
        } else {
            resource.save(flush: true)
            return resource
        }
    }


    static namedQueries = {
        search { ResourceSearchCO co ->
            if (co.topicID && co.visibility) {
                eq('topic.id', co.topicID)
                'topic' {
                    eq('visibility', co.visibility)
                }
            }
        }

        publicTopicsSearch { ResourceSearchCO co ->
            if (co.topicID) {
                eq('topic.id', co.topicID)
                topic {
                    eq('visibility', Visibility.PRIVATE)
                }
            }
        }
    }

    RatingInfoVO getRatingInfo() {
        List result = ResourceRating.createCriteria().get {
            projections {
                count('id', 'totalVotes')
                avg('score')
                sum('score')
            }
            eq('resources', this)
            order('totalVotes', 'desc')
        }
        new RatingInfoVO(totalVotes: result[0], averageScore: result[1], totalScore: result[2])
    }
}