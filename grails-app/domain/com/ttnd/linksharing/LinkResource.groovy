package com.ttnd.linksharing

import grails.transaction.Transactional

class LinkResource extends Resource {
    String url
    static constraints = {
        url(blank: false, url: true)
    }

    String toString() {
        return url
    }

    @Transactional
    Boolean deleteFile() {
        if (this.delete(flush: true))
            return true
        else
            return false
    }
}
