package co

import com.ttnd.linksharing.User
import grails.validation.Validateable

/**
 * Created by vishnu on 12/3/16.
 */
@Validateable
class UpdateProfileCO {
    Long id
    String firstName
    String lastName
    String userName
    def file

    static constraints = {
        id(nullable: false, blank: false)
        firstName(nullable: false, blank: false)
        lastName(nullable: false, blank: false)
        userName(nullable: true, blank: true)
        file(nullable: true, blank: true)
    }

    public User getUser() {
        return User.get(id)
    }
}
