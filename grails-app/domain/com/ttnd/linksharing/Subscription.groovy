package com.ttnd.linksharing

import enums.Seriousness

class Subscription {
    Seriousness seriousness
    Date dateCreated
    Date lastUpdated
    static constraints = {
        topic(unique: ['user'])
    }
    
    static belongsTo = [user:User,topic:Topic]

    static mapping = {
        seriousness defaultValue:Seriousness.SERIOUS
    }

    String toString(){
        return "${user} subscribed ${topic}"
    }

    public static Subscription save(Subscription subscription) {
        subscription.validate()
        if (subscription.hasErrors()) {
            subscription.errors.each {
                log.error "error while saving subscription ${it}--- ${it.allErrors}"
            }
            return null
        } else {
            subscription.save(flush: true,failOnError: true)
            return subscription
        }
    }
}
