package com.ttnd.linksharing

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
    boolean isAdmin
    boolean isActive
    Date dateCreated
    Date lastUpdated
    String confirmPassword

    static transients = ['confirmPassword', 'subscribedTopics']

    static hasMany = [topics: Topic, subscriptions: Subscription, readingItems: ReadingItem, resources: Resource]

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

    String getName() {
        [this.firstName, this.lastName].findAll { it }.join(' ')
    }

    String toString() {
        return this.userName
    }

    public static User save(User user) {
        user.validate()
        if (user.hasErrors()) {
            user.errors.each {
                log.error "error while saving user ${it}--- ${it.allErrors}"
            }
            return null
        } else {
            user.save(flush: true)
            return user
        }
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

    static List<PostVO> getReadingItems(User user) {
        List<ReadingItem> readingItems = ReadingItem.findAllByUserAndIsRead(user, false)
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

    public static Boolean canDeleteResource(User user, Long resourceID) {
        Resource resource = Resource.read(resourceID)
        if (user.isAdmin || resource.createdBy.id == user.id)
            return true
        return false
    }

    public Integer getScore(Resource resource) {
        ResourceRating resourceRating = ResourceRating.findByUserAndResource(this, resource)
        return resourceRating.score
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
}
