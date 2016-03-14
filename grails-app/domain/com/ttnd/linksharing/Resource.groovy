package com.ttnd.linksharing

import co.ResourceSearchCO
import enums.Visibility
import vo.PostVO
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

    static hasMany = [resourceRating: ResourceRating,
                      readingItems  : ReadingItem]
    static belongsTo = [topic: Topic]

    String toString() {
        return "${description}"
    }

    static namedQueries = {
        search { ResourceSearchCO resourceSearchCO ->
            if (resourceSearchCO.q) {
                if (resourceSearchCO.topicID) {
                    eq('topic.id', resourceSearchCO.topicID)
                }
                if (resourceSearchCO.visibility && resourceSearchCO.visibility == Visibility.PUBLIC) {
                    'topic' {
                        eq('visibility', Visibility.PUBLIC)
                    }
                }
                ilike('description', "%${resourceSearchCO.q}%")
            }
            if (resourceSearchCO.id) {
                if (resourceSearchCO.visibility && resourceSearchCO.visibility == Visibility.PUBLIC) {
                    'topic' {
                        eq('visibility', Visibility.PUBLIC)
                    }
                }
                eq('createdBy.id', resourceSearchCO.id)
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

    static def topPosts() {
        return ResourceRating.createCriteria().list(max: 5) {
            projections {
                property('resource.id')
                'resource' {
                    property('description')
                    property('url')
                    property('filePath')
                    'topic' {
                        property('id')
                        property('name')
                        eq('visibility', enums.Visibility.PUBLIC)
                    }
                    'createdBy' {
                        property('id')
                        property('userName')
                        property('firstName')
                        property('lastName')
                        property('photo')
                    }
                    property('lastUpdated')
                }
            }
            groupProperty('resource.id')
            count('id', 'totalVotes')
            order('totalVotes', 'desc')
        }
    }

    static List<PostVO> getTopPosts() {
        List<PostVO> topPostVOList = []
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
                        eq('visibility', enums.Visibility.PUBLIC)
                    }
                    'createdBy' {
                        property('id')
                        property('userName')
                        property('firstName')
                        property('lastName')
                        property('photo')
                    }
                    property('lastUpdated')
                }
            }
            groupProperty('resource.id')
            count('id', 'totalVotes')
            order('totalVotes', 'desc')
        }?.each {
            topPostVOList.add(new PostVO(resourceID: it[0], description: it[1], url: it[2], filePath: it[3], topicID:
                    it[4], topicName: it[5], userID: it[6], userName: it[7], userFirstName: it[8], userLastName: it[9],
                    userPhoto: it[10], isRead: "", resourceRating: 0, postDate: it[11]))
        }
        return topPostVOList
    }

    static def recentPosts() {
        return Resource.createCriteria().list(max: 5) {
            projections {
                property('id')
                property('description')
                property('url')
                property('filePath')
                'topic' {
                    property('id')
                    property('name')
                    eq('visibility', enums.Visibility.PUBLIC)
                }
                'createdBy' {
                    property('id')
                    property('userName')
                    property('firstName')
                    property('lastName')
                    property('photo')
                }
                property('lastUpdated')
            }
            order('lastUpdated', 'desc')
        }
    }

    static def topicPosts(Long id) {
        return Resource.createCriteria().list(max: 5) {
            projections {
                property('id')
                property('description')
                property('url')
                property('filePath')
                'topic' {
                    property('id')
                    property('name')
                }
                'createdBy' {
                    property('id')
                    property('userName')
                    property('firstName')
                    property('lastName')
                    property('photo')
                }
                property('lastUpdated')
            }
            eq('topic.id', id)
            order('lastUpdated', 'desc')
        }
    }

    def post() {
        return Resource.createCriteria().get {
            projections {
                property('id')
                property('description')
                property('url')
                property('filePath')
                'topic' {
                    property('id')
                    property('name')
                }
                'createdBy' {
                    property('id')
                    property('userName')
                    property('firstName')
                    property('lastName')
                    property('photo')
                }
                property('lastUpdated')
            }
            eq('id', this.id)
        }
    }

    static List usersWithUnreadResources() {
        return ReadingItem.createCriteria().listDistinct {
            projections {
                property('user')
            }
            eq('isRead', false)
        }
    }

}