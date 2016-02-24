package vo

import com.ttnd.linksharing.User
import enums.Visibility

/**
 * Created by vishnu on 24/2/16.
 */
class TopicVO {
    Long id
    String name
    Visibility visibility
    Integer count
    User createdBy
}
