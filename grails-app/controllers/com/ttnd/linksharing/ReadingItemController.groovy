package com.ttnd.linksharing

class ReadingItemController {

    def index() {}

    def changeIsRead(Long id, Boolean isRead) {
        if (ReadingItem.executeUpdate("update ReadingItem set isRead=:isRead where resource.id=:id", [isRead: isRead, id: id])) {
            flash.message = "Reading Item Status Changed"
        } else {
            flash.error = "Reading Item Status not Changed"
        }
        redirect(uri: '/')
    }
}
