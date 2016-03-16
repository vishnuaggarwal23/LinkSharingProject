package com.ttnd.linksharing

import co.*
import enums.Visibility
import util.Linksharing
import vo.PostVO
import vo.TopicVO
import vo.UserVO
import vo.conversion.DomainToVO

class UserController {

    def userService
    def resourceService

    def index(SearchCO searchCO) {
        searchCO.max = searchCO.max ?: 5
        searchCO.offset = searchCO.offset ?: 0

        List<TopicVO> trendingTopicsVO = DomainToVO.trendingTopics()
        UserVO userDetailsVO = DomainToVO.userDetails(session.user)
        List<PostVO> recentPostVO = DomainToVO.recentPosts()
        List<PostVO> readingItemsVO = DomainToVO.readingItems(session.user, searchCO)

        render(view: 'index', model: [subscribedTopics : session.user.subscribedTopics,
                                      trendingTopics   : trendingTopicsVO,
                                      userDetails      : userDetailsVO,
                                      recentPosts      : recentPostVO,
                                      readingItems     : readingItemsVO,
                                      subscriptions    : DomainToVO.userSubscriptions(session.user),
                                      searchCO         : searchCO,
                                      totalReadingItems: session.user.getTotalReadingItem()])
    }

    def profile(ResourceSearchCO resourceSearchCO) {
        resourceSearchCO.max = resourceSearchCO.max ?: 5
        resourceSearchCO.offset = resourceSearchCO.offset ?: 0
        User user = User.get(resourceSearchCO.id)
        if (session.user) {
            if (!(Linksharing.isUserAdmin(session.user) || Linksharing.isCurrentUser(session.user, User.load(resourceSearchCO.id)))) {
                resourceSearchCO.visibility = Visibility.PUBLIC
            }
        } else {
            resourceSearchCO.visibility = Visibility.PUBLIC
        }
        render view: 'profile', model: [userDetails     : DomainToVO.userDetails(user),
                                        createdResources: DomainToVO.createdResources(resourceSearchCO),
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
            flash.error = g.message(code: "com.ttnd.linksharing.user.image.image.not.found")
        }
    }

    def topics(Long id) {
        TopicSearchCO topicSearchCO = new TopicSearchCO(id: id)
        if (session.user) {
            if (!(Linksharing.isUserAdmin(session.user) || Linksharing.isCurrentUser(session.user, User.load(id)))) {
                topicSearchCO.visibility = Visibility.PUBLIC
            }
        } else {
            topicSearchCO.visibility = Visibility.PUBLIC
        }
        List<TopicVO> createdTopics = DomainToVO.createdTopics(topicSearchCO)
        render(template: '/topic/list', model: [topicList: createdTopics])
    }

    def subscriptions(Long id) {
        TopicSearchCO topicSearchCO = new TopicSearchCO(id: id)
        if (session.user) {
            if (!(Linksharing.isUserAdmin(session.user) || Linksharing.isCurrentUser(session.user, User.load(id)))) {
                topicSearchCO.visibility = Visibility.PUBLIC
            }
        } else {
            topicSearchCO.visibility = Visibility.PUBLIC
        }
        List<Topic> subscribedTopics = DomainToVO.subscribedTopics(topicSearchCO)
        render(template: '/topic/list', model: [topicList: subscribedTopics])
    }

    def registeredUsers(UserSearchCO userSearchCO) {
        userSearchCO.max = userSearchCO.max ?: 20
        userSearchCO.offset = userSearchCO.offset ?: 0
        List<UserVO> registeredUsers = DomainToVO.registeredUsers(userSearchCO, session.user)
        if (registeredUsers) {
            render(view: 'users', model: [userList    : registeredUsers,
                                          totalUsers  : User.countByIsAdmin(false) ?: 0,
                                          userSearchCO: userSearchCO])
        } else {
            redirect(url: request.getHeader("referer"))
        }
    }

    def updateUserActiveStatus(Long id) {
        User admin = session.user
        User normal = User.get(id)
        if (admin && normal) {
            User tempUser = userService.toggleActiveStatus(admin, normal)
            if (tempUser) {
                flash.message = g.message(code: "com.ttnd.linksharing.user.update.User.Active.Status.active.status.toggled")
            } else {
                flash.error = g.message(code: "com.ttnd.linksharing.user.update.User.Active.Status.active.status.not.toggled")
            }
        }
        redirect(url: request.getHeader("referer"))
    }

    def save(UpdateProfileCO updateProfileCO) {
        if (session.user) {
            updateProfileCO.file = params.file
            log.info params.file
            if (updateProfileCO.hasErrors()) {
                render(view: 'edit', model: [userDetails: DomainToVO.userDetails(session.user), userCo: session.user])
            } else {
                User user = userService.updateProfile(updateProfileCO)
                if (user) {
                    session.user = user
                    flash.message = g.message(code: "com.ttnd.linksharing.user.save.profile.updated")
                    render(view: 'edit', model: [userDetails: DomainToVO.userDetails(user),
                                                 userCo     : user])
                } else {
                    flash.error = g.message(code: "com.ttnd.linksharing.user.save.profile.not.updated")
                    render(view: 'edit', model: [userDetails: DomainToVO.userDetails(session.user),
                                                 userCo     : session.user])
                }
            }
        }
    }

    def updatePassword(UpdatePasswordCO updatePasswordCO) {
        if (session.user) {
            updatePasswordCO.id = session.user.id
            if (updatePasswordCO.hasErrors()) {
                render(view: 'edit', model: [userDetails: DomainToVO.userDetails(session.user), userCo: session.user])
            } else {
                User user = userService.updatePassword(updatePasswordCO)
                if (user) {
                    session.user = user
                    flash.message = g.message(code: "com.ttnd.linksharing.user.save.password.updated")
                    render(view: 'edit', model: [userDetails: DomainToVO.userDetails(user), userCo: user])
                } else {
                    flash.error = g.message(code: "com.ttnd.linksharing.user.save.password.not.updated")
                    render(view: 'edit', model: [userDetails: DomainToVO.userDetails(session.user), userCo: session.user])
                }
            }
        }
    }

    def edit() {
        User user = session.user
        if (user) {
            render(view: 'edit', model: [userDetails: DomainToVO.userDetails(user), userCo: user])
        }
    }

    def validatePassword() {
        Boolean valid = User.findByUserNameAndPassword(session.user.userName, params.oldPassword) ? true : false
        render valid
    }
}
