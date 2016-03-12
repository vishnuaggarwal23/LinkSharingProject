package co

import com.ttnd.linksharing.User
import grails.validation.Validateable

/**
 * Created by vishnu on 12/3/16.
 */
@Validateable
class UpdatePasswordCO {
    Long id
    String oldPassword
    String password

    static constraints = {
        importFrom(User, include: ['password'])
        oldPassword(validator: { val, obj ->
            if (!obj.id && (val.equals(obj.getUser()?.password))) {
                return "current.password.do.not.matches"
            }
        })
    }

    public User getUser() {
        return User.get(id)
    }
}
