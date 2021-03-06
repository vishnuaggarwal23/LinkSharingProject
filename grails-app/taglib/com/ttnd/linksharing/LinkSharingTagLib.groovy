package com.ttnd.linksharing

import constants.AppConstants
import util.Linksharing

class LinkSharingTagLib {
    //static defaultEncodeAs = [taglib: 'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    static namespace = "ls"

    def markRead = { attrs, body ->
        User user = session.user
        if (user) {
            Boolean isRead = Boolean.valueOf(attrs.isRead)
            String readStatus
            if (isRead) {
                readStatus = "Mark as Unread"
            } else {
                readStatus = "Mark as Read"
            }
            out << "<a href='' class='pull-right toggleIsRead' resourceId='${attrs.id}' " +
                    "isRead='${!isRead}'>'${readStatus}'</a>"
        }
    }

    def resourceType = { attrs, body ->
        Long resourceID = attrs.resourceID
        String resourceType = Linksharing.checkResourceType(Resource?.get(resourceID))
        String resourceLink = attrs.url
        if (resourceType == AppConstants.LINK_RESOURCE_TYPE) {
            out << "<a href='${resourceLink}' class='pull-right' target='_blank'>View Full Site</a>"
        } else if (resourceType == AppConstants.DOCUMENT_RESOURCE_TYPE) {
            out << "<a href='${createLink(controller: 'documentResource', action: 'download', params: [id: resourceID])}' class='pull-right'>Download</a>"
        }
    }

    def canDeleteResource = { attrs, body ->
        Long resourceID = attrs.resourceID
        User user = session.user
        if (user) {
            if (Linksharing.ifUserCanEditDeleteResource(Resource?.get(resourceID), user)) {
                out << "<a href='${createLink(controller: 'Resource', action: 'delete', params: [id: resourceID])}' " +
                        "class='pull-right'>Delete</a>"
            }
        }
    }

    def showSubscribe = { attrs, body ->
        if (session.user) {
            Long topicID = attrs.topicID
            if (topicID) {
                if (Linksharing.isTopicSubscribedByUser(Topic?.get(topicID), session.user)) {
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

    def showVisibility = { attrs, body ->
        User user = session.user
        Long topicId = attrs.topicId
        Topic topic = Topic.get(topicId)
        if (user) {
            if (topic) {
                if (Linksharing.isUserAdmin(user) || Linksharing.ifTopicIsCreatedBy(topic, user)) {
                    out << "${g.select(name: 'visibility', from: enums.Visibility.values(), class: 'btn btn-xs btn-default dropdown-toggle visibility', topicId: topic.id, topicName: topic.name, createdBy: user, value: topic.visibility)}"
                }
            }
        }
    }

    def userImage = { attrs, body ->
        Long userId = attrs.userId
        String height = attrs.height
        String width = attrs.width
        String tagClass = attrs.class
        out << "<img src='${createLink(controller: 'user', action: 'image', params: [id: userId])}' " +
                "class='${tagClass}' height='${height}' width='${width}'>"
    }

    def canDeleteTopic = { attrs, body ->
        User user = session.user
        Long topicId = attrs.topicId
        Topic topic = Topic.get(topicId)
        if (user && topic) {
            if (Linksharing.isUserAdmin(user) || Linksharing.ifTopicIsCreatedBy(topic, user)) {
                out << "<a href='${createLink(controller: 'topic', action: 'delete', params: [id: topicId])}'><span " +
                        "class='fa fa-trash' style='font-size:20px'></span></a>"
            }
        }
    }

    def sendTopicInvite = { attrs, body ->
        User user = session.user
        Long topicId = attrs.topicId
        Topic topic = Topic.get(topicId)
        if (user && topic) {
            if (Linksharing.isUserAdmin(user) || Linksharing.ifTopicIsCreatedBy(topic, user) || Subscription.findByUserAndTopic(user, topic)) {
                out << "<a class='btn' id='inviteModalBtn' role='button' data-toggle='modal' data-target='#sendinviteModal'>" +
                        "<span class='glyphicon glyphicon-envelope'></span></a>"
            }
        }
    }

    def editTopicDetails = { attrs, body ->
        User user = session.user
        Long topicId = attrs.topicId
        Topic topic = Topic.get(topicId)
        if (user && topic) {
            if (Linksharing.isUserAdmin(user) || Linksharing.ifTopicIsCreatedBy(topic, user)) {
                out << "<span name='editTopic_${topicId}' onclick='openTopicEdit(${topicId})' " +
                        "id='editTopic_${topicId}' " +
                        "class='fa fa-file-o' " +
                        "style='font-size:20px'></span>"
            }
        }
    }

    def editResourceDetails = { attrs, body ->
        User user = session.user
        Long resourceId = attrs.resourceId
        Resource resource = Resource.get(resourceId)
        if (user && resource) {
            if (Linksharing.isUserAdmin(user) || Linksharing.isResourceCreatedBy(resource, user)) {
                out << "<a class=\"btn\" id=\"resourceEdit\" role=\"button\" data-toggle=\"modal\"\n" +
                        "                           data-target=\"#resourceEditModal\"\n" +
                        "                           params=\"[id:${resourceId},description:${resource.description}]\">\n" +
                        "                            <ins>Edit</ins>\n" +
                        "                        </a>"

            }
        }
    }

    def canUpdateTopic = { attrs, body ->
        out << body()
    }

    def showSubscribedTopics = {
//        User user = session.user
        print("x" * 10);
        println session.dump();
//        println session.user;
//        println user?.getSubscribedTopics();
        /*if (user) {
            out << "${g.select(class: 'btn dropdown-toggle col-sm-8 form-control', name: 'topic', from: user?.getSubscribedTopics(), optionKey: 'id')}"
        }*/
    }

}
