package com.ttnd.linksharing

import co.TopicCO
import enums.Visibility
import grails.converters.JSON
import vo.PostVO
import vo.TopicVO
import vo.UserVO

class TopicController {

    def emailService
    def topicService

    def index() {}

    def show(Long id) {
        Topic topic = Topic.read(id)
        if (!topic) {
            flash.put("error", "Topic do not exists")
            redirect(controller: 'login', action: 'index')
        } else {
            TopicVO topicDetails = Topic.getTopicDetails(topic)
            List<UserVO> subscribedUsers = Topic.getSubscribedUsers(topic)
            List<PostVO> topicPosts = Resource.getTopicPosts(topic.id)
            if (topic.visibility == Visibility.PUBLIC) {
                render(view: 'show', model: [topicDetails: topicDetails, subscribedUsers: subscribedUsers,
                                             topicPosts  : topicPosts])
            } else if (topic.visibility == Visibility.PRIVATE) {
                User user = session.user
                Subscription subscription = Subscription.findByUserAndTopic(user, topic)
                if (!subscription) {
                    flash.put("error", "Topic is Private, User is not Subscribed to it")
                } else {
                    render(view: 'show', model: [topicDetails: topicDetails, subscribedUsers: subscribedUsers,
                                                 topicPosts  : topicPosts])
                }
            }
        }
    }

    def save(TopicCO topicCO) {
        Map jsonResponse = [:]
        if (session.user) {
            topicCO.createdBy = session.user
            if (topicCO.hasErrors()) {
                jsonResponse.error = "Topic Save/Update Error"
            } else {
                Topic topic = topicService.saveTopic(topicCO)
                if (topic) {
                    jsonResponse.message = "Topic Saved/Updated"
                } else {
                    jsonResponse.error = "Topic not Saved/Updated"
                }
            }
        }
        render jsonResponse as JSON
    }

    def getTrendingTopics() {
        TopicVO topicList = Resource.trendingTopics

        render "${topicList}"
    }

    def delete(Long id) {
        Topic topic = Topic.get(id)
        User user = session.user
        if (user) {
            if (topic && (user.isAdmin || topic.createdBy.id == user.id)) {
                topic.delete(flush: true)
                flash.message = "Topic Deleted"
            } else {
                flash.error = "Not Enough Rights"
            }
        }
        redirect(controller: 'login', action: 'index')
    }

    def invite(Long topic, String email) {
        if (topic && email) {
            if (emailService.invite(topic, email)) {
                flash.message = "Invitation Sent"
            } else {
                flash.error = "Invitation not Sent"
            }
        }
        redirect(controller: 'login', action: 'index')
    }

    def join(Long id) {
        Topic topic = Topic.get(id)
        if (topic && session.user) {
            Subscription subscription = new Subscription(user: session.user, topic: topic)

            if (subscription.validate()) {
                subscription.save(flush: true)
                flash.message = "You have subscribed to this topic successfully."
            } else
                flash.error = "Failure. Could not subscribe to the topic."
        }
        redirect(controller: "login", action: "index")
    }
}
