package com.ttnd.linksharing

import co.SearchCO
import co.UserSearchCO
import vo.PostVO
import vo.TopicVO
import vo.UserVO

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

    static transients = ['confirmPassword', 'subscribedTopics']

    static hasMany = [topics: Topic, subscriptions: Subscription, readingItems: ReadingItem, resources: Resource, resourceRatings: ResourceRating]

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
        List<Topic> topicList = Subscription.createCriteria().list() {
            projections {
                property('topic')
            }
            eq('user.id', id)
        }
        return topicList
    }

    UserVO getUserDetails() {
        return new UserVO(id: id, name: userName, firstName: firstName, lastName: lastName, email: email, photo: photo, isActive: isActive, isAdmin: isAdmin)
    }

    static List<PostVO> getReadingItems(User user, SearchCO searchCO) {
        List<ReadingItem> readingItems = ReadingItem.findAllByUser(user, [max: searchCO.max, offset: searchCO.offset])
        List<PostVO> readingItemsList = []
        readingItems.each {
            readingItemsList.add(new PostVO(resourceID: it.resource.id, description: it.resource.description, topicID: it
                    .resource.topic.id, topicName: it.resource.topic.name, userID: it.resource.createdBy.id, userName:
                    it.resource.createdBy.userName, userFirstName: it.resource.createdBy.firstName, userLastName: it
                    .resource.createdBy.lastName, userPhoto: it.resource.createdBy.photo, isRead: it.isRead, url: it
                    .resource, filePath: it.resource, postDate: it.resource.lastUpdated))
        }
        return readingItemsList
    }

    public Integer getScore(Resource resource) {
        ResourceRating resourceRating = ResourceRating.findByUserAndResource(this, resource)
        if (!resourceRating) {
            return 1
        } else {
            return resourceRating.score
        }

    }

    public Boolean isSubscribed(Long topicId) {
        if (Subscription.findByUserAndTopic(this, Topic.load(topicId))) {
            return true
        } else {
            return false
        }
    }

    public List<TopicVO> getUserSubscriptions() {
        List<TopicVO> userSubscriptions = []
        Subscription.createCriteria().list(max: 5) {
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
        }.each {
            userSubscriptions.add(new TopicVO(id: it[0], name: it[1], visibility: it[2], count: 0, createdBy: this))
        }
        return userSubscriptions
    }

    public Subscription getSubscription(Long topicId) {
        return Subscription.findByUserAndTopic(this, Topic.load(topicId))
    }

    def getCreatedTopics() {
        return this.topics
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
