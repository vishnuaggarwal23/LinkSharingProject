package com.ttnd.linksharing

import enums.Visibility
import grails.converters.JSON
import vo.PostVO
import vo.TopicVO
import vo.UserVO

class TopicController {

    def emailService

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
                //render "Success, Subscribed to Public Topic"
                render(view: 'show', model: [topicDetails: topicDetails, subscribedUsers: subscribedUsers,
                                             topicPosts  : topicPosts])
            } else if (topic.visibility == Visibility.PRIVATE) {
                User user = session.user
                Subscription subscription = Subscription.findByUserAndTopic(user, topic)
                if (!subscription) {
                    flash.put("error", "Topic is Private, User is not Subscribed to it")
                    //redirect(controller: 'login', action: 'index')
                } else {
                    render(view: 'show', model: [topicDetails: topicDetails, subscribedUsers: subscribedUsers,
                                                 topicPosts  : topicPosts])
                    //render "Success, Subscribed to Private Topic"
                }
            }
        }
    }

    def save(String topicName, String visibility) {
        /*User user = session.user
        Topic topic = new Topic(createdBy: user, name: topicName, visibility: Visibility.checkVisibility(visibility))
        if (topic.validate()) {
            topic.save(flush: true)
            flash.message = "${topic} Saved"
            //render flash.message
            //user.addToTopics(topic)
            //render "${topic} Saved"
        } else {
            flash.error = "${topic} Not Saved"
            log.error "${topic.errors.allErrors.collect { message(error: it) }.join(',')}"
            //render flash.error
            //render "${topic} Not Saved"
        }
        redirect(uri: "/")*/

        Topic topic = Topic.findOrCreateByNameAndCreatedBy(topicName, session.user)
        Map jsonResponse = [:]
        try {
            if (topic) {
                topic.visibility = Visibility.checkVisibility(visibility)
                if (topic.validate()) {
                    topic.save(flush: true)
                    flash.message = "Topic Saved/Updated"
                    jsonResponse.message = flash.message
                } else {
                    flash.error = "Topic not Saved/Updated"
                    jsonResponse.error = flash.error
                }
            } else {
                flash.error = "Topic not Found"
                jsonResponse.error = flash.error
            }
        }
        catch (Exception e) {
            log.info e.message
            flash.error = "Topic not Found"
            jsonResponse.error = flash.error
        }
        JSON jsonObject = jsonResponse as JSON
        render jsonObject
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
