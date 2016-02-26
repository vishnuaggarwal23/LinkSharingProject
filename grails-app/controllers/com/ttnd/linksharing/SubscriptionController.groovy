package com.ttnd.linksharing

import enums.Seriousness

class SubscriptionController {

    def index() {}

    def save(Integer topicID) {
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
    }

    private void addToSubscriptionList(Subscription subscription) {
        List<Subscription> subscriptions = Subscription.list()
        subscriptions.add(subscription)
    }

    def update(Integer id, String serious) {
        try {
            Subscription subscription = Subscription.load(id)
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
            render e.message
        }
    }

    def delete(Integer id) {
        try {
            Subscription subscription = Subscription.load(id)
            subscription.delete(flush: true)
            render "Subscription Deleted"
        }
        catch (Exception e) {
            render e.message
        }
    }
}
