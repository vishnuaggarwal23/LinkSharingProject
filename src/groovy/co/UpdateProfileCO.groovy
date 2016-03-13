package co

import com.ttnd.linksharing.User
import grails.validation.Validateable

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

    User getUser() {
        return User.get(id)
    }
}
