package vo.conversion

import co.SearchCO
import com.ttnd.linksharing.Resource
import com.ttnd.linksharing.Topic
import com.ttnd.linksharing.User
import vo.PostVO
import vo.TopicVO
import vo.UserVO

/**
 * Created by vishnu on 14/3/16.
 */
class DomainToVO {
    static List<UserVO> subscribedUsers(Topic topic) {
        if (topic) {
            List<User> subscribedUsers = topic.subscribedUsers()
            List<UserVO> subscribedUsersVO = []
            subscribedUsers.each { User user ->
                subscribedUsersVO.add(new UserVO(
                        id: user.id,
                        name: user.userName,
                        firstName: user.firstName,
                        lastName: user.lastName,
                        email: user.email)
                )
            }
            return subscribedUsersVO
        }
    }

    static TopicVO topicDetails(Topic topic) {
        if (topic) {
            Topic topicDetails = topic.topicDetails()
            TopicVO topicVO = new TopicVO()
            topicVO.id = topicDetails.id
            topicVO.name = topicDetails.name
            topicVO.createdBy = topicDetails.createdBy
            topicVO.visibility = topicDetails.visibility
            return topicVO
        }
    }

    static List<PostVO> topicPosts(Topic topic) {
        if (topic) {
            List<PostVO> topicPostsVO = []
            Resource.topicPosts(topic.id).each {
                topicPostsVO.add(new PostVO(
                        resourceID: it[0],
                        description: it[1],
                        url: it[2],
                        filePath: it[3],
                        topicID: it[4],
                        topicName: it[5],
                        userID: it[6],
                        userName: it[7],
                        userFirstName: it[8],
                        userLastName: it[9],
                        userPhoto: it[10],
                        isRead: "",
                        resourceRating: 0,
                        postDate: it[11])
                )
            }
            return topicPostsVO
        }
    }

    static List<PostVO> topPosts() {
        List<PostVO> topPostsVO = []
        Resource.topPosts().each {
            topPostsVO.add(new PostVO(
                    resourceID: it[0],
                    description: it[1],
                    url: it[2],
                    filePath: it[3],
                    topicID: it[4],
                    topicName: it[5],
                    userID: it[6],
                    userName: it[7],
                    userFirstName: it[8],
                    userLastName: it[9],
                    userPhoto: it[10],
                    isRead: "",
                    resourceRating: 0,
                    postDate: it[11]))
        }
        return topPostsVO
    }

    static List<PostVO> recentPosts() {
        List<PostVO> recentPostsVO = []
        Resource.recentPosts().each {
            recentPostsVO.add(new PostVO(
                    resourceID: it[0],
                    description: it[1],
                    url: it[2],
                    filePath: it[3],
                    topicID: it[4],
                    topicName: it[5],
                    userID: it[6],
                    userName: it[7],
                    userFirstName: it[8],
                    userLastName: it[9],
                    userPhoto: it[10],
                    isRead: "",
                    resourceRating: 0,
                    postDate: it[11])
            )
        }
        return recentPostsVO
    }

    static PostVO post(Resource resource) {
        def post = resource.post()
        return new PostVO(
                resourceID: post[0],
                description: post[1],
                url: post[2],
                filePath: post[3],
                topicID: post[4],
                topicName: post[5],
                userID: post[6],
                userName: post[7],
                userFirstName: post[8],
                userLastName: post[9],
                userPhoto: post[10],
                isRead: "",
                postDate: post[11],
                resourceRating: 0
        )
    }

    static List<TopicVO> trendingTopics() {
        List<TopicVO> trendingTopicsVO = []
        Topic.trendingTopics().each {
            trendingTopicsVO.add(new TopicVO(
                    id: it[0],
                    name: it[1],
                    visibility: it[2],
                    count: it[3],
                    createdBy: it[4])
            )
        }
        return trendingTopicsVO
    }

    static List<PostVO> readingItems(User user, SearchCO searchCO) {
        List<PostVO> readingItemsVO = []
        user.readingItems(searchCO).each {
            readingItemsVO.add(new PostVO(
                    resourceID: it.resource.id,
                    description: it.resource.description,
                    topicID: it.resource.topic.id,
                    topicName: it.resource.topic.name,
                    userID: it.resource.createdBy.id,
                    userName: it.resource.createdBy.userName,
                    userFirstName: it.resource.createdBy.firstName,
                    userLastName: it.resource.createdBy.lastName,
                    userPhoto: it.resource.createdBy.photo,
                    isRead: it.isRead,
                    url: it.resource,
                    filePath: it.resource,
                    postDate: it.resource.lastUpdated)
            )
        }
        return readingItemsVO
    }

    static UserVO userDetails(User user) {
        if (user) {
            User userDetails = user.userDetails()
            UserVO userDetailsVO = new UserVO()
            userDetailsVO.id = userDetails.id
            userDetailsVO.name = userDetails.userName
            userDetailsVO.firstName = userDetails.firstName
            userDetailsVO.lastName = userDetails.lastName
            userDetailsVO.email = userDetails.email
            userDetailsVO.photo = userDetails.photo
            userDetailsVO.isActive = userDetails.isActive
            userDetailsVO.isAdmin = userDetails.isAdmin
            return userDetailsVO
        }
    }

    static List<TopicVO> userSubscriptions(User user) {
        if (user) {
            List<TopicVO> userSubscriptionsVO = []
            user.userSubscriptions().each {
                userSubscriptionsVO.add(new TopicVO(
                        id: it[0],
                        name: it[1],
                        visibility: it[2],
                        count: 0,
                        createdBy: user)
                )
            }
            return userSubscriptionsVO
        }
    }
}
