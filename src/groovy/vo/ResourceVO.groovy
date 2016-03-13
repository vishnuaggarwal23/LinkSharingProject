package vo

import com.ttnd.linksharing.User
import enums.Visibility

class ResourceVO {
    Long id
    String name
    Visibility visibility
    Integer count
    User createdBy

    String toString() {
        return "${id},${name},${visibility},${count},${createdBy}"
    }
}
