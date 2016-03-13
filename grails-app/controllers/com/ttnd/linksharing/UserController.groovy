package com.ttnd.linksharing

import co.*
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

    def index(SearchCO searchCO) {
        searchCO.max = searchCO.max ?: 5
        searchCO.offset = searchCO.offset ?: 0

        List<TopicVO> trendingTopics = Topic.getTrendingTopics()
        List<PostVO> recentPostVOList = Resource.getRecentPosts()
        UserVO userDetail = session.user.getUserDetails()
        List<PostVO> readingResource = User.getReadingItems(session.user, searchCO)
        render(view: 'index', model: [subscribedTopics : session.user.subscribedTopics,
                                      trendingTopics   : trendingTopics,
                                      userDetails      : userDetail,
                                      recentPosts      : recentPostVOList,
                                      readingItems     : readingResource,
                                      subscriptions    : session.user.getUserSubscriptions(),
                                      searchCO         : searchCO,
                                      totalReadingItems: session.user.getTotalReadingItem()])
    }

    def profile(ResourceSearchCO resourceSearchCO) {
        resourceSearchCO.max = resourceSearchCO.max ?: 5
        resourceSearchCO.offset = resourceSearchCO.offset ?: 0
        User user = User.get(resourceSearchCO.id)
        if (session.user) {
            if (!(userService.isAdmin(session.user) || userService.isCurrentUser(session.user, User.load(resourceSearchCO.id)))) {
                resourceSearchCO.visibility = Visibility.PUBLIC
            }
        } else {
            resourceSearchCO.visibility = Visibility.PUBLIC
        }
        render view: 'profile', model: [userDetails     : user.getUserDetails(),
                                        createdResources: resourceService.search(resourceSearchCO),
                                        totalPosts      : user.getPostsCount(),
                                        resourceSearchCO: resourceSearchCO]
    }

    def image(Long id) {
        User user = User.get(id)
        if (user) {
            def userPhoto = userService.image(user)
            if (userPhoto) {
                response.outputStream << userPhoto
                response.outputStream.flush()
            }
        } else {
            flash.error = "Photo not Available"
        }
    }

    def topics(Long id) {
        TopicSearchCO topicSearchCO = new TopicSearchCO(id: id)
        if (session.user) {
            if (!(userService.isAdmin(session.user) || userService.isCurrentUser(session.user, User.load(id)))) {
                topicSearchCO.visibility = Visibility.PUBLIC
            }
        } else {
            topicSearchCO.visibility = Visibility.PUBLIC
        }
        List<TopicVO> createdTopics = topicService.search(topicSearchCO)
        render(template: '/topic/list', model: [topicList: createdTopics])
    }

    def subscriptions(Long id) {
        TopicSearchCO topicSearchCO = new TopicSearchCO(id: id)
        if (session.user) {
            if (!(userService.isAdmin(session.user) || userService.isCurrentUser(session.user, User.load(id)))) {
                topicSearchCO.visibility = Visibility.PUBLIC
            }
        } else {
            topicSearchCO.visibility = Visibility.PUBLIC
        }
        List<Topic> subscribedTopics = subscriptionService.search(topicSearchCO)
        render(template: '/topic/list', model: [topicList: subscribedTopics])
    }

    def registeredUsers(UserSearchCO userSearchCO) {
        List<UserVO> registeredUsers = userService.registeredUsers(userSearchCO, session.user)
        if (registeredUsers) {
            render(view: 'users', model: [userList: registeredUsers])
        } else {
            redirect(controller: 'login', action: 'index')
        }
    }

    def updateUserActiveStatus(Long id) {
        User admin = session.user
        User normal = User.get(id)
        if (admin && normal) {
            User tempUser = userService.toggleActiveStatus(admin, normal)
            if (tempUser) {
                flash.message = "Active Status Toggled"
            } else {
                flash.error = "Active Status not Toggled"
            }
        }
        redirect(controller: 'user', action: 'registeredUsers')
    }

    def save(UpdateProfileCO updateProfileCO) {
        if (session.user) {
            updateProfileCO.file = params.file
            log.info params.file
            if (updateProfileCO.hasErrors()) {
                render(view: 'edit', model: [userDetails: session.user.getUserDetails(), userCo: session.user])
            } else {
                User user = userService.updateProfile(updateProfileCO)
                if (user) {
                    session.user = user
                    flash.message = "Profile Updated"
                    render(view: 'edit', model: [userDetails: user.getUserDetails(), userCo: user])
                } else {
                    flash.error = "Profile not Updated"
                    render(view: 'edit', model: [userDetails: session.user.getUserDetails(), userCo: session.user])
                }
            }
        }
    }

    def updatePassword(UpdatePasswordCO updatePasswordCO) {
        if (session.user) {
            if (updatePasswordCO.hasErrors()) {
                render(view: 'edit', model: [userDetails: session.user.getUserDetails(), userCo: session.user])
            } else {
                User user = userService.updatePassword(updatePasswordCO)
                if (user) {
                    session.user = user
                    flash.message = "Password Updated"
                    render(view: 'edit', model: [userDetails: user.getUserDetails(), userCo: user])
                } else {
                    flash.error = "Password not Updated"
                    render(view: 'edit', model: [userDetails: session.user.getUserDetails(), userCo: session.user])
                }
            }
        }
    }

    def edit() {
        User user = session.user
        if (user) {
            render(view: 'edit', model: [userDetails: user.getUserDetails(), userCo: user])
        }
    }
}
