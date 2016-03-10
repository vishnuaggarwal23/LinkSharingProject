package com.ttnd.linksharing

import vo.PostVO
import vo.TopicVO
import vo.UserVO

class UserController {

    def index() {
        List<TopicVO> trendingTopics = Topic.getTrendingTopics()
        List<PostVO> recentPostVOList = Resource.getRecentPosts()
        UserVO userDetail = session.user.getUserDetails()
        List<PostVO> readingResource = User.getReadingItems(session.user)
        render(view: 'index', model: [subscribedTopics: session.user.subscribedTopics, trendingTopics: trendingTopics,
                                      userDetails: userDetail, recentPosts: recentPostVOList, readingItems:
                                              readingResource,subscriptions:session.user.getUserSubscriptions()])
    }
}
