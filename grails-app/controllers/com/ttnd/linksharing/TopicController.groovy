package com.ttnd.linksharing

import enums.Visibility

class TopicController {

    def index() {}

    def show(Integer id) {
        Topic topic = Topic.findById(id)
        if (topic == null) {
            flash.put("error", "Topic do not exists")
            redirect(controller: 'login', action: 'index')
        } else {
            if (topic.visibility == Visibility.PUBLIC) {
                render "Success"
            } else if (topic.visibility == Visibility.PRIVATE) {
                User user = session.user
                Subscription subscription = Subscription.findByUserAndTopic(user, topic)
                if (subscription == null) {
                    flash.put("error", "Topic is Private, User is not Subscribed to it")
                    redirect(controller: 'login', action: 'index')
                } else {
                    render "Success"
                }
            }
        }
    }
}
