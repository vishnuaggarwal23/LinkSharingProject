package com.ttnd.linksharing

class ReadingItemController {

    def index() {}

    def changeIsRead(Long id, Boolean isRead) {
        if (ReadingItem.executeUpdate("update ReadingItem set isRead=:isRead where id=:id", [isRead: isRead, id: id])) {
            render "success"
        } else {
            render "error"
        }
    }
}
