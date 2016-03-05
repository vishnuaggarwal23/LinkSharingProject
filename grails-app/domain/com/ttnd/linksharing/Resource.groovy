package com.ttnd.linksharing

import co.ResourceSearchCO
import enums.Visibility
import vo.PostVO
import vo.RatingInfoVO
import vo.TopicVO

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

    public static List<PostVO> getTopPosts() {
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
                }
            }

            groupProperty('resource.id')
            count('id', 'totalVotes')
            order('totalVotes', 'desc')
        }?.each {
            topPostVOList.add(new PostVO(resourceID: it[0], description: it[1], url: it[2], filePath: it[3], topicID:
                    it[4], topicName: it[5], userID: it[6], userName: it[7], userFirstName: it[8], userLastName: it[9],
                    userPhoto: it[10], isRead: "", resourceRating: 0))
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
            'topic' {
                eq('visibility', Visibility.PUBLIC)
            }
            order('totalResources', 'desc')
            'topic' {
                order('name', 'desc')
            }
            maxResults 5
            firstResult 0
        }
        List list = result.collect { it }
        trendingTopicsList = Topic.getAll(list)
        return trendingTopicsList
    }

    public static List<PostVO> getRecentPosts() {
        List<PostVO> recentPostsList = []
        Resource.createCriteria().list(max: 5) {
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
            }
            order('lastUpdated', 'desc')
        }?.each {
            recentPostsList.add(new PostVO(resourceID: it[0], description: it[1], url: it[2], filePath: it[3], topicID:
                    it[4], topicName: it[5], userID: it[6], userName: it[7], userFirstName: it[8], userLastName: it[9],
                    userPhoto: it[10], isRead: "", resourceRating: 0))
        }
        return recentPostsList
    }

    public static List<PostVO> getTopicPosts(Long topicID) {
        List<PostVO> topicPosts = []
        Resource.createCriteria().list(max: 5) {
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
            }
            eq('topic.id', topicID)
            order('lastUpdated', 'desc')
        }.each {
            topicPosts.add(new PostVO(resourceID: it[0], description: it[1], url: it[2], filePath: it[3], topicID:
                    it[4], topicName: it[5], userID: it[6], userName: it[7], userFirstName: it[8], userLastName: it[9],
                    userPhoto: it[10], isRead: "", resourceRating: 0))
        }
        return topicPosts
    }

    public static PostVO getPost(Long id) {
        /*def obj = Resource.createCriteria().get {
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
            }
            eq('id', id)
        }*/
        def obj = ResourceRating.createCriteria().get{
            projections {
                property('resource.id')
                'resource' {
                    property('description')
                    property('url')
                    property('filePath')
                    eq('id', id)
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
                }
                property('score')
            }
        }
        return new PostVO(resourceID: obj[0], description: obj[1], url: obj[2], filePath: obj[3], topicID:
                obj[4], topicName: obj[5], userID: obj[6], userName: obj[7], userFirstName: obj[8], userLastName: obj[9],
                userPhoto: obj[10], isRead: "",resourceRating: obj[11])
    }

    public static def checkResourceType(Long id) {
        /*def obj = Resource.createCriteria().get {
            projections {
                property('class')
            }
            eq('id', id)
        }
        return obj*/
        Resource resource = Resource.read(id)
        if (resource.class.equals(LinkResource))
            return "LinkResource"
        else if (resource.class.equals(DocumentResource))
            return "DocumentResource"
    }

    public canViewBy(User user){
        if(this.topic.canViewedBy(user)){
            return true
        }
        return false
    }
}