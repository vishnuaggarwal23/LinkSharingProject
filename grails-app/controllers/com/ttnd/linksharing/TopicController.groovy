package com.ttnd.linksharing

import co.TopicCO
import enums.Visibility
import grails.converters.JSON
import vo.PostVO
import vo.TopicVO
import vo.UserVO
import vo.conversion.DomainToVO

class TopicController {

    def emailService
    def topicService

    def show(Long id) {
        Topic topic = Topic.read(id)
        if (topic) {

            TopicVO topicDetailsVO = DomainToVO.topicDetails(topic)
            List<UserVO> subscribedUsersVO = DomainToVO.subscribedUsers(topic)
            List<PostVO> topicPostsVO = DomainToVO.topicPosts(topic)

            if (topic.visibility == Visibility.PUBLIC) {
                render(view: 'show', model: [topicDetails: topicDetailsVO, subscribedUsers: subscribedUsersVO,
                                             topicPosts  : topicPostsVO])
            } else if (topic.visibility == Visibility.PRIVATE) {
                User user = session.user
                Subscription subscription = Subscription.findByUserAndTopic(user, topic)
                if (subscription) {
                    render(view: 'show', model: [topicDetails: topicDetailsVO, subscribedUsers: subscribedUsersVO,
                                                 topicPosts  : topicPostsVO])
                } else {
                    flash.error = g.message(code: "com.ttnd.linksharing.topic.show.private.topic.cannot.access")
                }
            }
        } else {
            flash.error = g.message(code: "com.ttnd.linksharing.topic.show.do.not.exists")
            redirect(controller: 'login', action: 'index')
        }
    }

    def save(TopicCO topicCO) {
        Map jsonResponse = [:]
        if (session.user) {
            topicCO.createdBy = session.user
            if (topicCO.hasErrors()) {
                jsonResponse.error = g.message(code: "com.ttnd.linksharing.topic.topic.save.update.error")
            } else {
                Topic topic = topicService.saveTopic(topicCO)
                if (topic) {
                    jsonResponse.message = g.message(code: "com.ttnd.linksharing.topic.topic.saved.updated")
                } else {
                    jsonResponse.error = g.message(code: "com.ttnd.linksharing.topic.topic.not.saved.updated")
                }
            }
        }
        render jsonResponse as JSON
    }

    def delete(Long id) {
        Topic topic = Topic.get(id)
        User user = session.user
        if (user && topic) {
            if (topicService.deleteTopic(topic, user)) {
                flash.message = g.message(code: "com.ttnd.linksharing.topic.delete.topic.deleted")
            } else {
                flash.error = g.message(code: "com.ttnd.linksharing.topic.delete.topic.not.deleted")
            }
        } else {
            flash.error = g.message(code: "com.ttnd.linksharing.topic.delete.topic.user.not.set")
        }
        redirect(controller: 'login', action: 'index')
    }

    def invite(Long topic, String email) {
        if (topic && email) {
            if (emailService.invite(topic, email)) {
                flash.message = g.message(code: "com.ttnd.linksharing.topic.invite.invitation.sent")
            } else {
                flash.error = g.message(code: "com.ttnd.linksharing.topic.invite.invitation.not.sent")
            }
        }
        redirect(controller: 'login', action: 'index')
    }

    def join(Long id) {
        Topic topic = Topic.get(id)
        if (topic) {
            if (session.user) {
                if (topicService.joinTopic(topic, session.user)) {
                    flash.message = g.message(code: "com.ttnd.linksharing.topic.join.topic.subscribed")
                } else {
                    flash.error = g.message(code: "com.ttnd.linksharing.topic.join.topic.not.subscribed")
                }
            } else {
                flash.error = g.message(code: "com.ttnd.linksharing.topic.join.session.not.set")
            }
        } else {
            flash.error = g.message(code: "com.ttnd.linksharing.topic.join.topic.do.not.esists")
        }
        redirect(controller: "login", action: "index")
    }

    def validateUniqueTopicPerUser() {
        Boolean valid = Topic.findByCreatedByAndName(session.user, params.topicName) ? false : true
        render valid
    }
}
