package com.ttnd.linksharing

import co.ResourceSearchCO
import co.TopicSearchCO
import enums.Visibility
import vo.PostVO
import vo.TopicVO
import vo.UserVO

class UserController {

    def assetResourceLocator
    def userService
    def topicService
    def subscriptionService
    def resourceService

    def index() {
        List<TopicVO> trendingTopics = Topic.getTrendingTopics()
        List<PostVO> recentPostVOList = Resource.getRecentPosts()
        UserVO userDetail = session.user.getUserDetails()
        List<PostVO> readingResource = User.getReadingItems(session.user)
        render(view: 'index', model: [subscribedTopics                      : session.user.subscribedTopics, trendingTopics: trendingTopics,
                                      userDetails                           : userDetail, recentPosts: recentPostVOList, readingItems:
                                              readingResource, subscriptions: session.user.getUserSubscriptions()])
    }

    def profile(ResourceSearchCO resourceSearchCO) {
        User user = User.get(resourceSearchCO.id)
        if (session.user) {
            if (!(userService.isAdmin(session.user.id) || session.user.equals(User.load(resourceSearchCO.id)))) {
                resourceSearchCO.visibility = Visibility.PUBLIC
            }
        } else
            resourceSearchCO.visibility = Visibility.PUBLIC
        render view: 'profile', model: [userDetails: user.getUserDetails(), createdResources: resourceService.search
                (resourceSearchCO)]
    }

    def image(Long id) {
        User user = User.get(id)
        if (user?.photo) {
            Byte[] img = user.photo
            response.outputStream.write(img)
        } else {
            response.outputStream << assetResourceLocator.findAssetForURI('silhouette.jpg').getInputStream()
        }
        response.outputStream.flush()
    }

    def topics(Long id) {
        TopicSearchCO topicSearchCO = new TopicSearchCO(id: id)
        if (session.user) {
            if (!(userService.isAdmin(session.user.id) || userService.isCurrentUser(session.user.id, id))) {
                topicSearchCO.visibility = Visibility.PUBLIC
            }
        } else
            topicSearchCO.visibility = Visibility.PUBLIC
        List<TopicVO> createdTopics = topicService.search(topicSearchCO)

        render(template: '/topic/list', model: [topicList: createdTopics])

    }

    def subscriptions(Long id) {

        TopicSearchCO topicSearchCO = new TopicSearchCO(id: id)

        if (session.user) {
            if (!(userService.isAdmin(session.user.id) || userService.isCurrentUser(session.user.id, id))) {
                topicSearchCO.visibility = Visibility.PUBLIC
            }
        } else
            topicSearchCO.visibility = Visibility.PUBLIC

        List<Topic> subscribedTopics = subscriptionService.search(topicSearchCO)

        render(template: '/topic/list', model: [topicList: subscribedTopics])

    }
}
