package com.ttnd.linksharing

import enums.Visibility
import vo.PostVO
import vo.TopicVO
import vo.UserVO

class TopicController {

    def index() {}

    def show(Long id) {
        Topic topic = Topic.read(id)
        if (!topic) {
            flash.put("error", "Topic do not exists")
            redirect(controller: 'login', action: 'index')
        } else {
            TopicVO topicDetails = Topic.getTopicDetails(topic)
            List<UserVO> subscribedUsers = Topic.getSubscribedUsers(topic)
            List<PostVO> topicPosts=Resource.getTopicPosts(topic.id)
            if (topic.visibility == Visibility.PUBLIC) {
                //render "Success, Subscribed to Public Topic"
                render(view: 'show', model: [topicDetails: topicDetails, subscribedUsers: subscribedUsers,
                                             topicPosts:topicPosts])
            } else if (topic.visibility == Visibility.PRIVATE) {
                User user = session.user
                Subscription subscription = Subscription.findByUserAndTopic(user, topic)
                if (!subscription) {
                    flash.put("error", "Topic is Private, User is not Subscribed to it")
                    //redirect(controller: 'login', action: 'index')
                } else {
                    render(view: 'show', model: [topicDetails: topicDetails, subscribedUsers: subscribedUsers,
                                                 topicPosts: topicPosts])
                    //render "Success, Subscribed to Private Topic"
                }
            }
        }
    }

    def save(String topicName, String visibility) {
        User user = session.user
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
        redirect(uri: "/")
    }

    def getTrendingTopics() {
        TopicVO topicList = Resource.trendingTopics

        render "${topicList}"
    }
}
