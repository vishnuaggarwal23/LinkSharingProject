package com.ttnd.linksharing

import enums.Seriousness

class Subscription {
    //Topic topic
    //User user
    Seriousness seriousness
    Date dateCreated
    Date lastUpdated
    static constraints = {
        user(unique: ['topic'])
    }
    
    static belongsTo = [user:User,topic:Topic]

    String toString(){
        return "${user} subscribed ${topic}"
    }
}
