package vo

import com.ttnd.linksharing.User
import enums.Visibility

/**
 * Created by vishnu on 27/2/16.
 */
class ResourceVO {
    Long id
    String name
    Visibility visibility
    Integer count
    User createdBy

    String toString(){
        return "${id},${name},${visibility},${count},${createdBy}"
    }
}
