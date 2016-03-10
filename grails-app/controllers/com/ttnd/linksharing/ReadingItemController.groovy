package com.ttnd.linksharing

import grails.converters.JSON

class ReadingItemController {

    def index() {}

    def changeIsRead(Long id, Boolean isRead) {
        User user = session.user
        Map jsonMap = [:]
        if (ReadingItem.executeUpdate("update ReadingItem set isRead=:isRead where resource.id=:id and user" +
                ".id=:userID", [isRead: isRead, id: id, userID: user.id])) {
            flash.message = "Reading Item Status Changed"
            jsonMap.message = flash.message
        } else {
            flash.error = "Reading Item Status not Changed"
            jsonMap.error = flash.error
        }
        JSON jsonObject = jsonMap as JSON
        render jsonObject
    }
}
