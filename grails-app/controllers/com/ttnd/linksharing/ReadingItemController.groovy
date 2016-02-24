package com.ttnd.linksharing

class ReadingItemController {

    def index() {}

    def changeIsRead(Long id, Boolean isRead){
        ReadingItem.executeUpdate("update readingItem as r set isRead")
    }
}
