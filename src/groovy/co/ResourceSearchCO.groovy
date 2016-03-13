package co

import com.ttnd.linksharing.User
import enums.Visibility

/**
 * Created by vishnu on 23/2/16.
 */
class ResourceSearchCO extends SearchCO {
    Long id
    Long topicID
    Visibility visibility
    Boolean global

    User getUser() {
        return User.get(id)
    }
}
