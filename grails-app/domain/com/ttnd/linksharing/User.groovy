package com.ttnd.linksharing

class User {
    String userName
    String password
    String firstName
    String lastName
    String email
    byte[] photo
    boolean isAdmin
    boolean isActive
    //static transients = ['name']
    Date dateCreated
    Date lastUpdated

    static hasMany = [topics: Topic, subscriptions: Subscription, readingItems: ReadingItem, documentResources: DocumentResource, linkResources: LinkResource]

    static mapping = {
        photo(sqlType: 'longblob')
    }

    static constraints = {
        email(unique: true, blank: false, email: true)
        password(blank: false, minSize: 5)
        firstName(blank: false)
        lastName(blank: false)
        userName(blank: false)
        photo(nullable: true)
        isAdmin(nullable: true)
        isActive(nullable: true)
    }

    String getName() {
        [this.firstName, this.lastName].findAll { it }.join(' ')
    }
}
