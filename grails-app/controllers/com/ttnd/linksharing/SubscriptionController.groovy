package com.ttnd.linksharing

import constants.AppConstants
import enums.Seriousness

class SubscriptionController {

    def index() {}

    def save(Long userId, Long topicId) {
        Subscription subscription = new Subscription(user: User.load(userId), topic: Topic.load(topicId), seriousness:
                AppConstants.SERIOUSNESS)
        if (subscription.validate(flush: true)) {
            if (subscription.save(flush: true)) {
                flash.message = "Subscription Saved"
            } else {
                flash.error = "Subscription not Saved"
            }
        } else {
            flash.error = "Subscription not Saved"
        }
        redirect(uri: '/')
    }

    /*def save(Integer topicID) {
        String seriousness = 'serious'
        User user = session.user
        Topic topic = Topic.findById(topicID)
        Subscription subscription = new Subscription(topic: topic, user: user, seriousness: Seriousness
                .checkSeriousness(seriousness))
        if (subscription.validate()) {
            subscription.save(flush: true, failOnError: true)
            topic.addToSubscriptions(subscription)
            user.addToSubscriptions(subscription)
            addToSubscriptionList(subscription)
            render "subscription saved"
        } else {
            flash.error = "${subscription.errors.allErrors.collect { message(error: it) }.join(',')}"
            render "Subscription not saved"
        }
    }*/

    /*private void addToSubscriptionList(Subscription subscription) {
        List<Subscription> subscriptions = Subscription.list()
        subscriptions.add(subscription)
    }*/

    def update(Integer id, String serious) {
        Subscription subscription = Subscription.load(id)
        try {
            subscription.seriousness = Seriousness.checkSeriousness(serious)
            if (subscription.validate()) {
                subscription.save(flush: true, failOnError: true)
                render "Subscription Updated"
            } else {
                render "Subscription not updated"
                flash.error = "${subscription.errors.allErrors.collect { message(error: it) }.join(',')}"
            }
        }
        catch (Exception e) {
            log.info e.message
            render "Subscription not Found"
        }
    }

    def delete(Long userId, Long topicId) {
        Subscription subscription = Subscription.findByUserAndTopic(User.load(userId), Topic.load(topicId))
        try {
            subscription.delete(flush: true)
            flash.message = "Subscription Deleted"
        }
        catch (Exception e) {
            log.error e.message
            flash.error = "Subscription not Deleted"
        }
        redirect(uri: '/')
    }
}
