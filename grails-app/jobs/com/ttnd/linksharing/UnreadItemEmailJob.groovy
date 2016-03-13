package com.ttnd.linksharing
/**
 * Created by vishnu on 13/3/16.
 */

class UnreadItemEmailJob {
    def userService
    static triggers = {
        cron name: 'myTrigger', cronExpression: "0 0 1 ? * MON"
    }

    def execute() {
        userService.sendUnreadItemsMail()
    }
}
