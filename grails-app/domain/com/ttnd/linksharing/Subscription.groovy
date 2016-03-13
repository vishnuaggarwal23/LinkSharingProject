package com.ttnd.linksharing

import enums.Seriousness

class Subscription {
    Seriousness seriousness = Seriousness.SERIOUS
    Date dateCreated
    Date lastUpdated
    static constraints = {
        topic(unique: ['user'])
    }

    static belongsTo = [user: User, topic: Topic]

    static mapping = {
        seriousness defaultValue: Seriousness.SERIOUS
    }

    String toString() {
        return "${user} subscribed ${topic}"
    }
}
