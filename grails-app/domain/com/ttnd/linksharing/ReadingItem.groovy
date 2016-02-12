package com.ttnd.linksharing

class ReadingItem {
    Resource resource
    User user
    boolean isRead
    Date dateCreated
    Date lastUpdated
    static constraints = {
        resource(unique: ['user'])
    }
}
