package com.ttnd.linksharing


class UnreadItemsMailJob {

    def userService

    static triggers = {
        cron name: 'myTrigger', cronExpression: "0 0 1 ? * MON"
    }

    def execute() {
        userService.sendUnreadItemsMail()
    }
}
