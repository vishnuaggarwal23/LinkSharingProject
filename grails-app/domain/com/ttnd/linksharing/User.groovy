package com.ttnd.linksharing

import co.SearchCO
import co.UserSearchCO

class User {
    String userName
    String password
    String firstName
    String lastName
    String email
    byte[] photo
    Boolean isAdmin
    Boolean isActive
    Date dateCreated
    Date lastUpdated
    String confirmPassword

    static transients = ['confirmPassword',
                         'subscribedTopics']

    static hasMany = [topics         : Topic,
                      subscriptions  : Subscription,
                      readingItems   : ReadingItem,
                      resources      : Resource,
                      resourceRatings: ResourceRating]

    static mapping = {
        photo(sqlType: 'longblob')
        sort id: 'desc'
    }

    static constraints = {
        email(unique: true, blank: false, email: true)
        password(blank: false, minSize: 5)
        firstName(blank: false)
        lastName(blank: false)
        userName(blank: false, unique: true)
        photo(nullable: true)
        isAdmin(nullable: true)
        isActive(nullable: true)
        confirmPassword(bindable: true, nullable: true, blank: true, validator: { val, obj ->
            if (!obj.id && (obj.password != val || !val)) {
                return 'password.do.not.match.confirm.password'
            }
        })
    }

    static namedQueries = {
        search { UserSearchCO userSearchCO ->
            if (userSearchCO.isActive != null) {
                eq('isActive', userSearchCO.isActive)
            }
            if (userSearchCO.q) {
                or {
                    ilike('firstName', "%${userSearchCO.q}%")
                    ilike('lastName', "%${userSearchCO.q}%")
                    ilike('userName', "%${userSearchCO.q}%")
                    ilike('email', "%${userSearchCO.q}%")
                }
            }
            eq('isAdmin', false)
        }
    }

    String getName() {
        [this.firstName, this.lastName].findAll { it }.join(' ')
    }

    String toString() {
        return this.userName
    }

    List<Topic> getSubscribedTopics() {
        List<Topic> topicList = Subscription.createCriteria().list {
            projections {
                property('topic')
            }
            eq('user.id', id)
        }
        return topicList
    }

    User userDetails() {
        return this
    }

    def readingItems(SearchCO searchCO) {
        return ReadingItem?.findAllByUser(this, [max: searchCO.max, offset: searchCO.offset])
    }

    Integer getScore(Resource resource) {
        ResourceRating resourceRating = ResourceRating.findByUserAndResource(this, resource)
        return resourceRating ? resourceRating.score : 1

    }

    Boolean isSubscribed(Long topicId) {
        return Subscription.findByUserAndTopic(this, Topic.load(topicId)) ? true : false
    }

    def userSubscriptions() {
        return Subscription.createCriteria().list(max: 5) {
            projections {
                'topic' {
                    property('id')
                    property('name')
                    property('visibility')
                }
                'user' {
                    eq('id', this.id)
                }
            }
        }
    }

    Subscription getSubscription(Long topicId) {
        return Subscription.findByUserAndTopic(this, Topic.load(topicId))
    }

    List<Resource> unreadResources() {
        return ReadingItem.createCriteria().list {
            projections {
                property('resource')
            }
            eq('user', this)
            eq('isRead', false)
        }
    }

    Integer getTotalReadingItem() {
        return ReadingItem.countByUser(this) ?: 0
    }

    Integer getPostsCount() {
        return Resource.countByCreatedBy(this) ?: 0
    }
}
