package com.ttnd.linksharing

import constants.AppConstants
import enums.Seriousness
import grails.converters.JSON

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

    def update(Long id, String serious) {
        Subscription subscription = Subscription.findByUserAndTopic(session.user, Topic.load(id))
        Map jsonResponse = [:]
        try {
            subscription.seriousness = Seriousness.checkSeriousness(serious)
            if (subscription.validate()) {
                subscription.save(flush: true)
                flash.message = "Subscription Updated"
                jsonResponse.message = flash.message
            } else {
                log.info = "${subscription.errors.allErrors.collect { message(error: it) }.join(',')}"
                flash.error = "Subscription not Updated"
                jsonResponse.error = flash.error
            }
        }
        catch (Exception e) {
            log.info e.message
            flash.error = "Subscription not Found"
            jsonResponse.error = flash.error
        }

        JSON jsonObject = jsonResponse as JSON
        render jsonObject
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
