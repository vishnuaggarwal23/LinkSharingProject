package com.ttnd.linksharing

class LinkSharingTagLib {
    //static defaultEncodeAs = [taglib: 'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    static namespace = "ls"

    def markRead = { attrs, body ->
        User user = session.user
        if (user) {
            String link = "${createLink(controller: 'readingItem', action: 'changeIsRead', params: [id: attrs.id, isRead: true])}"
            Boolean isRead = Boolean.valueOf(attrs.isRead)
            if (isRead) {
                out << "<p class='pull-right'>Post Read</p>"
            } else {
                out << "<a href=$link class='pull-right'>Mark as Read</a>"
            }
        }
    }

}
