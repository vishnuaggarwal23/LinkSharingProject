package co

import com.ttnd.linksharing.User
import grails.validation.Validateable

/**
 * Created by vishnu on 6/3/16.
 */
@Validateable
class UserCO {
    String userName
    String password
    String firstName
    String lastName
    String email
    byte[] photo
    Boolean isAdmin=false
    String confirmPassword

    static constraints = {
        email(unique: true, blank: false, email: true)
        password(blank: false, minSize: 5)
        firstName(blank: false)
        lastName(blank: false)
        userName(blank: false, unique: true)
        photo(nullable: true)
        confirmPassword(bindable: true, nullable: true, blank: true, validator: { val, obj ->
            if (obj.password != val || !val || !obj.password) {
                return 'password.do.not.match.confirm.password'
            }
        })
    }
}
