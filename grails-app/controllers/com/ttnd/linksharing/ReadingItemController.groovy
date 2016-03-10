package com.ttnd.linksharing

class ReadingItemController {

    def index() {}

    def changeIsRead(Long id, Boolean isRead) {
        User user = session.user
        if (ReadingItem.executeUpdate("update ReadingItem set isRead=:isRead where resource.id=:id and user" +
                ".id=:userID", [isRead: isRead, id: id, userID: user.id])) {
            flash.message = "Reading Item Status Changed"
        } else {
            flash.error = "Reading Item Status not Changed"
        }
        redirect(uri: '/')
    }
}
