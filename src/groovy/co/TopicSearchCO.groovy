package co

import com.ttnd.linksharing.User
import enums.Visibility

class TopicSearchCO extends SearchCO {
    Long id
    Visibility visibility

    User getUser() {
        return User.get(id)
    }
}