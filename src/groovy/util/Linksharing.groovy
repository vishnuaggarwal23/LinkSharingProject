package util

import co.UserCO
import com.ttnd.linksharing.*
import constants.AppConstants
import enums.Visibility

class Linksharing {

    static Boolean isUserAdmin(User user) {
        return user && user.isAdmin
    }

    static Boolean isUserActive(User user) {
        return user && user.isActive
    }

    static Boolean isCurrentUser(User user1, User user2) {
        return user1 && user2 && (user1.id == user2.id)
    }

    static Boolean ifUserExists(UserCO user) {
        return (user && User.countByUserNameAndEmail(user.userName, user.password))
    }

    static List<User> getUserWithUnreadResources() {
        return Resource.usersWithUnreadResources()
    }

    static List<Resource> getUnreadResources(User user) {
        return user.unreadResources()
    }

    static Boolean isTopicPublic(Topic topic) {
        return topic && topic.visibility == Visibility.PUBLIC
    }

    static Boolean ifTopicCanbeViewdBy(Topic topic, User user) {
        return user && topic && (isTopicPublic(topic) || isUserAdmin(user) || Subscription.findByUserAndTopic(user,
                topic))
    }

    static Boolean ifTopicIsCreatedBy(Topic topic, User user) {
        return user && topic && (topic.createdBy.id == user.id)
    }

    static Boolean isResourceCreatedBy(Resource resource, User user) {
        return user && resource && (resource.createdBy.id == user.id)
    }

    static Boolean ifUserCanEditDeleteResource(Resource resource, User user) {
        return user && resource && (isUserAdmin(user) || isResourceCreatedBy(resource, user))
    }

    static Boolean ifResourceCanBeViewdBy(Resource resource, User user) {
        return user && resource && (ifTopicCanbeViewdBy(resource.topic, user))
    }

    static String checkResourceType(Resource resource) {
        if (resource) {
            if (resource.class.equals(LinkResource)) {
                return AppConstants.LINK_RESOURCE_TYPE
            } else if (resource.class.equals(DocumentResource)) {
                return AppConstants.DOCUMENT_RESOURCE_TYPE
            }
        }
    }

    static UUID uniqueFileName() {
        return UUID.randomUUID()
    }

}
