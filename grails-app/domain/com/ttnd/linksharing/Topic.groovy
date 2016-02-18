package com.ttnd.linksharing

import constants.AppConstants
import enums.Visibility

class Topic {
    String name
    User createdBy
    Visibility visibility
    Date dateCreated
    Date lastUpdated

    static hasMany = [subscriptions: Subscription, resources:Resource]

    static constraints = {
        name(blank: false, unique: ['createdBy'])
        visibility(inList: Visibility.values().toList())
    }

    def afterInsert() {
        Topic.withNewSession {
            Subscription subscription = new Subscription(user: this.createdBy, topic: this, seriousness: AppConstants.SERIOUSNESS)
            if (subscription.save()) {
                log.info "Subscription saved for user ${this.createdBy} for topic ${this}"
            } else {
                log.info "Subscription did not saved"
            }
        }
    }
}
