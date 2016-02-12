package com.ttnd.linksharing

class DocumentResource extends Resource {
    String filePath
    Date dateCreated
    Date lastUpdated
    static constraints = {
        filePath(blank: false)
    }
}