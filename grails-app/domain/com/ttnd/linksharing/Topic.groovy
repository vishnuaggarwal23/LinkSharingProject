package com.ttnd.linksharing

import constants.AppConstants
import enums.Visibility
import vo.TopicVO
import vo.UserVO

class Topic {
    String name
    User createdBy
    Visibility visibility
    Date dateCreated
    Date lastUpdated

    def subscriptionService

    static hasMany = [subscriptions: Subscription, resources: Resource]

    static constraints = {
        name(blank: false, unique: ['createdBy'])
        visibility(inList: Visibility.values().toList())
    }

    static mapping = {
        sort name: 'asc'
    }

    def afterInsert() {
        Topic.withNewSession {
            Subscription subscription = new Subscription(user: this.createdBy, topic: this, seriousness: AppConstants.SERIOUSNESS)
            if (subscriptionService.saveSubscription(subscription)) {
                log.info "${subscription}-> ${this.createdBy} subscribed for ${this}"
            } else {
                log.error "Subscription does not occurs--- ${subscription.errors.allErrors}"
            }
        }
    }

    String toString() {
        return "${name}, ${createdBy}"
    }

    static List<TopicVO> getTrendingTopics() {
        List<TopicVO> trendingTopics = []
        Resource.createCriteria().list {
            projections {
                createAlias('topic', 't')
                groupProperty('t.id')
                property('t.name')
                property('t.visibility')
                count('t.id', 'topicCount')
                property('t.createdBy')
            }
            order('topicCount', 'desc')
            order('t.name', 'asc')
            maxResults(5)
        }?.each {
            trendingTopics.add(new TopicVO(id: it[0], name: it[1], visibility: it[2], count: it[3], createdBy: it[4]))
        }
        return trendingTopics
    }

    static List<Topic> trendingTopics() {
        return Resource.createCriteria().list {
            projections {
                createAlias('topic', 't')
                groupProperty('t.id')
                property('t.name')
                property('t.visibility')
                count('t.id', 'topicCount')
                property('t.createdBy')
            }
            order('topicCount', 'desc')
            order('t.name', 'asc')
            maxResults(5)
        }
    }

    static TopicVO getTopicDetails(Topic topic) {
        TopicVO topicDetails = new TopicVO()
        topicDetails.id = topic.id
        topicDetails.name = topic.name
        topicDetails.visibility = topic.visibility
        topicDetails.createdBy = topic.createdBy
        return topicDetails
    }

    Topic topicDetails() {
        return this
    }

    static List<UserVO> getSubscribedUsers(Topic topic) {
        List<UserVO> subscribedUsers = []
        Subscription.createCriteria().list {
            projections {
                'user' {
                    property('id')
                    property('userName')
                    property('firstName')
                    property('lastName')
                    property('email')
                    property('photo')
                    property('isAdmin')
                    property('isActive')
                }
                eq('topic.id', topic.id)
            }
        }?.each {
            subscribedUsers.add(new UserVO(id: it[0], name: it[1], firstName: it[2], lastName: it[3], email: it[4], photo:
                    it[5], isAdmin: it[6], isActive: it[7]))
        }
        return subscribedUsers
    }

    List<User> subscribedUsers() {
        return Subscription.createCriteria().list {
            projections {
                property('user')
            }
            eq('topic', this)
        }
    }
}
