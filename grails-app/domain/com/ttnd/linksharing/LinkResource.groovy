package com.ttnd.linksharing

class LinkResource extends Resource {
    String url
    Date dateCreated
    Date lastUpdated
    static constraints = {
        url(blank: false,url:true)
    }

    String toString(){
        return url
    }
}
