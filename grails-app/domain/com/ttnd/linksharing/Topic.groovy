package com.ttnd.linksharing

import constants.AppConstants
import enums.Visibility
import vo.TopicVO

class Topic {
    String name
    User createdBy
    Visibility visibility
    Date dateCreated
    Date lastUpdated

    static hasMany = [subscriptions: Subscription,
                      resources    : Resource]

    static constraints = {
        name(blank: false, unique: ['createdBy'])
        visibility(inList: Visibility.values().toList())
    }

    static mapping = {
        sort name: 'asc'
    }

    def afterInsert() {
        Topic.withNewSession {
            Subscription subscription = new Subscription(
                    user: this.createdBy,
                    topic: this,
                    seriousness: AppConstants.SERIOUSNESS)
            if (subscription.validate() && subscription.save(flush: true)) {
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

    Topic topicDetails() {
        return this
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
