package com.ttnd.linksharing

import grails.converters.JSON
import util.Linksharing

class SubscriptionController {

    def subscriptionService

    def save(Long userId, Long topicId) {
        User user = User?.get(userId)
        Topic topic = Topic?.get(topicId)
        if (user && topic) {
            if (subscriptionService.saveSubscription(topic, user)) {
                flash.message = g.message(code: "com.ttnd.linksharing.subscription.save.subscription.saved")
            } else {
                flash.error = g.message(code: "com.ttnd.linksharing.subscription.save.subscription.not.saved")
            }
        } else {
            flash.error = g.message(code: "com.ttnd.linksharing.subscription.save.topic.user.not.set")
        }
        redirect(url: request.getHeader("referer"))
    }

    def update(Long id, String serious) {
        User user = session.user
        Topic topic = Topic?.get(id)
        Map jsonResponse = [:]
        if (user && topic && serious) {
            Subscription subscription = Subscription?.findByUserAndTopic(user, topic)
            if (subscription) {
                if (subscriptionService.updateSubscription(subscription, serious)) {
                    jsonResponse.message = g.message(code: "com.ttnd.linksharing.subscription.update.subscription.updated")
                } else {
                    jsonResponse.error = g.message(code: "com.ttnd.linksharing.subscription.update.subscription.not.updated")
                }
            } else {
                jsonResponse.error = g.message(code: "com.ttnd.linksharing.subscription.update.subscription.not.set")
            }
        } else {
            jsonResponse.error = g.message(code: "com.ttnd.linksharing.subscription.update.topic.user.not.set")
        }
        render jsonResponse as JSON
    }

    def delete(Long userId, Long topicId) {
        User user = User?.get(userId)
        Topic topic = Topic?.get(topicId)
        if (user && topic) {
            if (Linksharing.ifTopicIsCreatedBy(topic, user)) {
                flash.error = g.message(code: "com.ttnd.linksharing.subscription.delete.user.cannot.unsubscribe.created.topic")
            } else {
                if (subscriptionService.deleteSubscription(topic, user)) {
                    flash.message = g.message(code: "com.ttnd.linksharing.subscription.delete.subscription.deleted")
                } else {
                    flash.error = g.message(code: "com.ttnd.linksharing.subscription.delete.subscription.not.deleted")
                }
            }
        } else {
            flash.error = g.message(code: "com.ttnd.linksharing.subscription.delete.topic.user.not.set")
        }
        redirect(url: request.getHeader("referer"))
    }
}
