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

    def resourceType = { attrs, body ->
        def resourceID = attrs.resourceID
        def resourceType = Resource.checkResourceType(resourceID)
        def resourceLink = attrs.url
        def resourcePath = attrs.filePath
        if (resourceType == "LinkResource") {
            out << "<a href='${resourceLink}' class='pull-right' target='_blank'>View Full Site</a>"
        } else if (resourceType == "DocumentResource") {
            out << "<a href='${createLink(controller: 'documentResource', action: 'download', params: [resourceId: resourceID])}' class='pull-right'>Download</a>"
        }
    }

    def canDeleteResource = { attrs, body ->
        Long resourceID = attrs.resourceID
        User user = session.user
        if (user) {
            Boolean canDelete = User.canDeleteResource(user, resourceID)
            if (canDelete) {
                out << "<a href='${createLink(controller: 'Resource', action: 'delete', params: [id: resourceID])}' " +
                        "class='pull-right'>Delete</a>"
            }
        }
    }

    def showSubscribe = { attrs, body ->
        if (session.user) {
            Long topicID = attrs.topicID
            if (topicID) {
                if (session.user.isSubscribed(topicID)) {
                    out << "<a href='${createLink(controller: 'subscription', action: 'delete', params: [userId: session.user.id, topicId: topicID])}'>Un-Subscribe</a>"
                } else {
                    out << "<a href='${createLink(controller: 'subscription', action: 'save', params: [userId: session.user.id, topicId: topicID])}'>Subscribe</a>"
                }
            }
        }
    }

    def subscriptionCount = { attrs, body ->
        Long userId = attrs.userId
        Long topicId = attrs.topicId
        if (userId) {
            out << Subscription.countByUser(User.load(userId))
        }
        if (topicId) {
            out << Subscription.countByTopic(Topic.load(topicId))
        }
    }

    def resourceCount = { attrs, body ->
        Long topicId = attrs.topicId
        if (topicId) {
            out << Resource.countByTopic(Topic.load(topicId))
        }
    }

    def topicCount = { attrs, body ->
        Long userId = session.user.id
        if (userId) {
            out << Topic.countByCreatedBy(User.load(userId))
        }
    }

}
