package co

import com.ttnd.linksharing.User
import enums.Visibility

/**
 * Created by vishnu on 10/3/16.
 */
class TopicSearchCO extends SearchCO {
    Long id
    Visibility visibility

    public User getUser() {
        return User.get(id)
    }
}