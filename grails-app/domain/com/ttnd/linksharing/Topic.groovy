package com.ttnd.linksharing

import constants.AppConstants
import enums.Visibility

class Topic {
    String name
    User createdBy
    Visibility visibility
    Date dateCreated
    Date lastUpdated

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
            if (Subscription.save(subscription)) {
                log.info "${subscription}-> ${this.createdBy} subscribed for ${this}"
                //this.addToSubscriptions(subscription)
                //this.createdBy.addToSubscriptions(subscription)
            } else {
                log.error "Subscription does not occurs--- ${subscription.errors.allErrors}"
            }
        }
    }

    public static Topic save(Topic topic) {
        topic.validate()
        if (topic.hasErrors()) {
            topic.errors.each {
                log.error "error while saving user ${it}--- ${it.allErrors}"
            }
            return null
        } else {
            topic.save(flush: true)
            return topic
        }
    }

    String toString() {
        return name
    }
}
