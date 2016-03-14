package com.ttnd.linksharing

class ReadingItem {
    boolean isRead
    Date dateCreated
    Date lastUpdated

    static constraints = {
        resource(unique: ['user'])
    }

    static belongsTo = [resource: Resource,
                        user    : User]

    String toString() {
        return "${user} read the ${resource}: ${isRead}"
    }
}
