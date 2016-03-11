package com.ttnd.linksharing

import co.ResourceSearchCO
import co.TopicSearchCO
import co.UserSearchCO
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
            if (!(userService.isAdmin(session.user.id) || userService.isCurrentUser(session.user.id, resourceSearchCO.id))) {
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

    def users(UserSearchCO userSearchCO) {
        if (session.user && userService.isAdmin(session.user.id)) {
            List<User> users = User.search(userSearchCO).list([sort: userSearchCO.sort, order: userSearchCO.order])
            List<UserVO> userVOList = []
            users.each {
                userVOList.add(new UserVO(id: it.id, name: it.userName, firstName: it.firstName, lastName: it.lastName, email:
                        it.email, isActive: it.isActive))
            }
            render(view: 'users', model: [userList: userVOList])
        } else {
            redirect(controller: 'login', action: 'index')
        }
    }

    def updateUserActiveStatus(Long id) {
        User adminUser = session.user
        User user = User.get(id)
        if (adminUser) {
            if (userService.isAdmin(session.user.id) && userService.isActive(session.user.id)) {
                if (user) {
                    if (!userService.isAdmin(user.id)) {
                        user.isActive = !user.isActive
                        if (user.save(flush: true)) {
                            flash.message = "Toggle Success"
                        } else {
                            flash.error = "Toggle Error"
                        }
                    } else {
                        flash.error = "Cant Toggle Admins"
                    }
                } else {
                    flash.error = "User not Available"
                }
            } else {
                flash.error = "User is either not Admin or Active"
            }
        } else {
            flash.error = "User not Available"
        }
        redirect(controller: 'user', action: 'users')
    }
}
