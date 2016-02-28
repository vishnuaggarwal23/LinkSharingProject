package com.ttnd.linksharing

import co.ResourceSearchCO
import enums.Visibility
import org.xhtmlrenderer.css.parser.property.PrimitivePropertyBuilders
import vo.RatingInfoVO
import vo.TopicVO
import vo.TopPostVO

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
            eq('resource', this)
            order('totalVotes', 'desc')
        }
        new RatingInfoVO(totalVotes: result[0], averageScore: result[1], totalScore: result[2])
    }

    public static List<TopPostVO> getTopPosts() {
        List<TopPostVO> topPostVOList = []
        ResourceRating.createCriteria().list(max: 5) {
            projections {
                property('resource.id')
                'resource' {
                    property('description')
                    property('url')
                    property('filePath')
                    'topic' {
                        property('id')
                        property('name')
                        eq('visibility',enums.Visibility.PRIVATE)
                    }
                    'createdBy' {
                        property('id')
                        property('userName')
                        property('firstName')
                        property('lastName')
                        property('photo')
                    }
                }
            }

            groupProperty('resource.id')
            count('id', 'totalVotes')
            order('totalVotes', 'desc')
        }?.each {
            topPostVOList.add(new TopPostVO(resourceID: it[0], description: it[1], url: it[2], filePath: it[3], topicID:
                    it[4], topicName: it[5], userID: it[6], userName: it[7], userFirstName: it[8], userLastName: it[9],
                    userPhoto: it[10]))
        }
        return topPostVOList
    }

    public static List<TopicVO> getTrendingTopics() {
        List<TopicVO> trendingTopicsList
        def result = Resource.createCriteria().list() {
            projections {
                createAlias('topic', 't')
                groupProperty('t')
                property('t.name')
                property('t.visibility')
                count('id', 'totalResources')
                property('t.createdBy')
            }
            'topic'{
                eq('visibility',Visibility.PUBLIC)
            }
            //eq('t.visibility', Visibility.PUBLIC)
            order('totalResources', 'desc')
            'topic'{
                order('name','desc')
            }
            //order('t.name', 'desc')
            maxResults 5
            firstResult 0
        }
        List list=result.collect {it}
        trendingTopicsList=Topic.getAll(list)
    }
}