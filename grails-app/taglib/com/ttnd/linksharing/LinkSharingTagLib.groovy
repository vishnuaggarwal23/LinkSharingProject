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
        Long userId = attrs.userId
        if (userId) {
            out << Topic.countByCreatedBy(User.load(userId))
        }
    }

    def showSeriousness = { attrs, body ->
        User user = session.user
        Long topicId = attrs.topicId
        Topic topic = Topic.get(topicId)
        if (user) {
            if (topic) {
                Subscription subscription = user.getSubscription(topicId)
                if (subscription) {
                    out << "${g.select(name: 'seriousness', id: 'seriousness', from: enums.Seriousness.values(), class: 'btn btn-xs btn-default dropdown-toggle seriousness', value: subscription.seriousness, topicId: topicId)}"
                }
            }
        }
    }

    //To be done
    def showVisibility = { attrs, body ->
        User user = session.user
        Long topicId = attrs.topicId
        Topic topic = Topic.get(topicId)
        if (user) {
            if (topic) {
                if (user.isAdmin || user.equals(topicId)) {
                    out << "${g.select(name: 'visibility', from: enums.Visibility.values(), class: 'btn btn-xs btn-default dropdown-toggle visibility', topicName: topic.name, value: topic.visibility)}"
                }
            }
        }
    }

    def userImage = { attrs, body ->
        Long userId = attrs.userId
        def height = attrs.height
        def width = attrs.width
        def tagClass = attrs.class
        out << "<img src='${createLink(controller: 'user', action: 'image', params: [id: userId])}' " +
                "class='${tagClass}' height='${height}' width='${width}'>"
    }

    def canDeleteTopic = { attrs, body ->
        User user = session.user
        Long topicId = attrs.topicId
        Topic topic = Topic.get(topicId)
        if (user && topic) {
            if (user.isAdmin || topic.createdBy.id == user.id) {
                out << "<a href='${createLink(controller: 'topic', action: 'delete', params: [id: topicId])}'><span " +
                        "class='fa fa-trash' style='font-size:20px'></span></a>"
            }
        }
    }

    def editTopicVisibility = { attrs, body ->
        User user = session.user
        Long topicId = attrs.topicId
        Topic topic = Topic.get(topicId)
        if (user && topic) {
            if (user.isAdmin || topic.createdBy.id == user.id) {
                out << g.render(template: '/templates/visibilitySelect')
            }
        }
    }

    /*def editTopicSeriousness = { attrs, body ->
        User user = session.user
        Long topicId = attrs.topicId
        Topic topic = Topic.get(topicId)
        if (user && topic) {
            if (user.isAdmin || topic.createdBy.id == user.id || Subscription.findByUserAndTopic(user, topic)) {
                out << g.render(template: '/templates/seriousnessSelect')
            }
        }
    }*/

    def sendTopicInvite = { attrs, body ->
        User user = session.user
        Long topicId = attrs.topicId
        Topic topic = Topic.get(topicId)
        if (user && topic) {
            if (user.isAdmin || topic.createdBy.id == user.id || Subscription.findByUserAndTopic(user, topic)) {
                out << "<a href='#'><span class='glyphicon glyphicon-envelope' style='font-size:20px'></span></a>"
            }
        }
    }

    def editTopicDetails = { attrs, body ->
        User user = session.user
        Long topicId = attrs.topicId
        Topic topic = Topic.get(topicId)
        if (user && topic) {
            if (user.isAdmin || topic.createdBy.id == user.id) {
                out << "<a href='#'><span class='fa fa-file-o' style='font-size:20px'></span></a>"
            }
        }
    }

    def editResourceDetails = { attrs, body ->
        User user = session.user
        Long topicId = attrs.topicId
        Long resourceId = attrs.resourceId
        Topic topic = Topic.get(topicId)
        Resource resource = Resource.get(resourceId)
        if (user && topic && resource) {
            if (user.isAdmin || resource.createdBy.id == user.id) {
                out << "<a href='#'><ins>Edit</ins></a>"
            }
        }
    }

    def canUpdateTopic = { attrs, body ->
        out << body()
    }

    def showSubscribedTopics = {
        User user = session.user
        if (user) {
            out << "${g.select(class: 'btn dropdown-toggle', name: 'topic', id: 'linkTopic', from: user.getSubscribedTopics(), optionKey: 'id')}"
        }
    }

}
