package com.ttnd.linksharing

import vo.TopicVO

class UserController {

    def index() {
        List<TopicVO> topicVOList=Topic.getTrendingTopics()
        render (view:'index', model:[subscribedTopics:session.user.subscribedTopics,topicVOList:topicVOList])
    }
}
