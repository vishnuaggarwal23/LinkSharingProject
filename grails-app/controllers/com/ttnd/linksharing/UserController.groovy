package com.ttnd.linksharing

import vo.TopicVO
import vo.UserVO

class UserController {

    def index() {
        List<TopicVO> topicVOList=Topic.getTrendingTopics()
        UserVO userDetail=session.user.getUserDetails()
        render (view:'index', model:[subscribedTopics:session.user.subscribedTopics,topicVOList:topicVOList,
                                     userDetails:userDetail])
    }
}
