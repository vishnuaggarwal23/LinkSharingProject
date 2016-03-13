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
        if (user && topic) {
            if (topicService.deleteTopic(topic, user)) {
                flash.message = "Topic Deleted"
            } else {
                flash.error = "Topic not Deleted"
            }
        } else {
            flash.error = "Topic/User not Set"
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
        if (topic) {
            if (session.user) {
                if (topicService.joinTopic(topic, session.user)) {
                    flash.message = "Topic Subscribed"
                } else {
                    flash.error = "Topic not Subscribed"
                }
            } else {
                flash.error = "Session User not Set, Re-login to join"
            }
        } else {
            flash.error = "Topic do not exists"
        }
        redirect(controller: "login", action: "index")
    }
}
