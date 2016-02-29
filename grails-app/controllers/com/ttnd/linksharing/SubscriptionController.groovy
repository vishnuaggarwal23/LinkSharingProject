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

    def delete(Integer id) {
        Subscription subscription = Subscription.load(id)
        try {
            subscription.delete(flush: true)
            render "Subscription Deleted"
        }
        catch (Exception e) {
            log.info e.message
            render "Subscription not Deleted"
        }
    }
}
