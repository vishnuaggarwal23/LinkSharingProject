package com.ttnd.linksharing

class ResourceRating {
    Integer score
    Date dateCreated
    Date lastUpdated
    static constraints = {
        score(min:1,max:5)
        resource(unique: ['user'])
    }

    static belongsTo = [user:User,resource:Resource]
}
