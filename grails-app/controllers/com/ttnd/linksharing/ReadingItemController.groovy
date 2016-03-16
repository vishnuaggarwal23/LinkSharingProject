package com.ttnd.linksharing

import grails.converters.JSON

class ReadingItemController {

    def resourceService

    def changeIsRead(Long id, Boolean isRead) {
        Map jsonResponse = [:]
        User user = session.user
        Resource resource = Resource?.get(id)
        if (user && resource) {
            if (resourceService.toggleIsReadResource(isRead, ReadingItem?.findByUserAndResource(user, resource))) {
                jsonResponse.message = g.message(code: "com.ttnd.linksharing.reading.item.change.is.read.successful")
            } else {
                jsonResponse.error = g.message(code: "com.ttnd.linksharing.reading.item.change.is.read.not.successful")
            }
        } else {
            jsonResponse.error = g.message(code: "com.ttnd.linksharing.reading.item.user.resource.not.set")
        }
        render jsonResponse as JSON
    }
    /*User user = session.user
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
}*/
}
