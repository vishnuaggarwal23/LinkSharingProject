package com.ttnd.linksharing

import vo.PostVO
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
                    .resource.createdBy.lastName, userPhoto: it.resource.createdBy.photo, isRead: it.isRead, url: "", filePath: ""))
        }
        return readingItemsList
    }
}
