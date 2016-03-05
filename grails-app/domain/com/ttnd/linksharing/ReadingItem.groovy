package com.ttnd.linksharing

class ReadingItem {
    boolean isRead
    Date dateCreated
    Date lastUpdated
    static constraints = {
        resource(unique: ['user'])
    }

    static belongsTo = [resource:Resource,user:User]

    public static ReadingItem save(ReadingItem readingItem) {
        readingItem.validate()
        if (readingItem.hasErrors()) {
            readingItem.errors.each {
                log.error "error while saving readingItem ${it.allErrors}"
            }
            return null
        } else {
            readingItem.save(flush: true)
            return readingItem
        }
    }

    String toString(){
        return "${user} read the ${resource}: ${isRead}"
    }
}
