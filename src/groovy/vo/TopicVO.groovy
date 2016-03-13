package vo

import com.ttnd.linksharing.User
import enums.Visibility

class TopicVO {
    Long id
    String name
    Visibility visibility
    Integer count
    User createdBy

    String toString() {
        return "${name}"
    }
}
