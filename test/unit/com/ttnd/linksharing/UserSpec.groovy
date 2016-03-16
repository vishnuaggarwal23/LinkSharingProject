package com.ttnd.linksharing

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(User)
class UserSpec extends Specification {

    @Unroll("User Constraints Except Email and Username----- #sno")
    def "userConstraintsExceptEmailAndUsername"() {
        given:
        User user = new User(userName: userName, firstName: firstName, lastName: lastName, email: email, password:
                password, confirmPassword: confirmPassword, photo: photo, isAdmin: isAdmin, isActive: isActive)

        when:
        Boolean result = user.validate()

        then:
        result == valid

        where:
        sno | email                          | userName          | password         | confirmPassword  | firstName | lastName   | photo | isAdmin | isActive | valid
        1   | "vishnu.aggarwal@tothenew.com" | "vishnu.aggarwal" | "vishnuaggarwal" | "vishnuaggarwal" | "vishnu"  | "aggarwal" | 12    | true    | true     | true
        2   | "vishnu"                       | "vishnu.aggarwal" | "vishnuaggarwal" | "vishnuaggarwal" | "vishnu"  | "aggarwal" | 12    | true    | true     | false
        3   | " "                            | "vishnu.aggarwal" | "vishnuaggarwal" | "vishnuaggarwal" | "vishnu"  | "aggarwal" | 12    | true    | true     | false
        4   | null                           | "vishnu.aggarwal" | "vishnuaggarwal" | "vishnuaggarwal" | "vishnu"  | "aggarwal" | 12    | true    | true     | false
        5   | "vishnu.aggarwal@tothenew.com" | " "               | "vishnuaggarwal" | "vishnuaggarwal" | "vishnu"  | "aggarwal" | 12    | true    | true     | false
        6   | "vishnu.aggarwal@tothenew.com" | null              | "vishnuaggarwal" | "vishnuaggarwal" | "vishnu"  | "aggarwal" | 12    | true    | true     | false
        7   | "vishnu.aggarwal@tothenew.com" | "vishnu.aggarwal" | "1234"           | "vishnuaggarwal" | "vishnu"  | "aggarwal" | 12    | true    | true     | false
        8   | "vishnu.aggarwal@tothenew.com" | "vishnu.aggarwal" | " "              | "vishnuaggarwal" | "vishnu"  | "aggarwal" | 12    | true    | true     | false
        9   | "vishnu.aggarwal@tothenew.com" | "vishnu.aggarwal" | null             | "vishnuaggarwal" | "vishnu"  | "aggarwal" | 12    | true    | true     | false
        10  | "vishnu.aggarwal@tothenew.com" | "vishnu.aggarwal" | "vishnuaggarwal" | "vishnuaggarwal" | " "       | "aggarwal" | 12    | true    | true     | false
        11  | "vishnu.aggarwal@tothenew.com" | "vishnu.aggarwal" | "vishnuaggarwal" | "vishnuaggarwal" | null      | "aggarwal" | 12    | true    | true     | false
        12  | "vishnu.aggarwal@tothenew.com" | "vishnu.aggarwal" | "vishnuaggarwal" | "vishnuaggarwal" | "vishnu"  | " "        | 12    | true    | true     | false
        13  | "vishnu.aggarwal@tothenew.com" | "vishnu.aggarwal" | "vishnuaggarwal" | "vishnuaggarwal" | "vishnu"  | null       | 12    | true    | true     | false
        14  | "vishnu.aggarwal@tothenew.com" | "vishnu.aggarwal" | "vishnuaggarwal" | "vishnuaggarwal" | "vishnu"  | "aggarwal" | null  | true    | true     | true
        15  | "vishnu.aggarwal@tothenew.com" | "vishnu.aggarwal" | "vishnuaggarwal" | "vishnuaggarwal" | "vishnu"  | "aggarwal" | 12    | null    | true     | true
        16  | "vishnu.aggarwal@tothenew.com" | "vishnu.aggarwal" | "vishnuaggarwal" | "vishnuaggarwal" | "vishnu"  | "aggarwal" | 12    | true    | null     | true
        17  | "vishnu.aggarwal@tothenew.com" | "vishnu.aggarwal" | "vishnuaggarwal" | "asdasd"         | "vishnu"  | "aggarwal" | 12    | true    | null     | false

    }

    def "emailUniqueness"() {
        given:
        String email = "vishnu.aggarwal@tothenew.com"
        String password = 'password'
        User user = new User(firstName: "Vishnu", lastName: "Aggarwal", email: email, password: password,
                confirmPassword: password, userName: "vishnu.aggarwal")

        when:
        user.save()

        then:
        User.count() == 1

        when:
        User newUser = new User(firstName: "Vishnu A", lastName: "V Aggarwal", email: email, password: password,
                confirmPassword: password, userName: "v.a")
        newUser.save()

        then:
        User.count() == 1
        newUser.errors.allErrors.size() == 1
        newUser.errors.getFieldErrorCount('email') == 1
    }

    def "usernameUniqueness"() {
        given:
        String username = "vishnu.aggarwal"
        String password = 'password'
        User user = new User(firstName: "Vishnu", lastName: "Aggarwal", email: "vishnu.aggarwal@tothenew.com",
                password: password, confirmPassword: password, userName: username)

        when:
        user.save()

        then:
        User.count() == 1

        when:
        User newUser = new User(firstName: "Vishnu", lastName: "Aggarwal", email: "vishnu.aggarwal@ttnd.com", password:
                password, confirmPassword: password, userName: username)
        newUser.save()

        then:
        User.count() == 1
        newUser.errors.allErrors.size() == 1
        newUser.errors.getFieldErrorCount('userName') == 1
    }


    def "fullName"() {
        given:
        User user = new User(firstName: firstName, lastName: lastName)

        expect:
        result == user.getName()

        where:
        firstName | lastName   | result
        ""        | ""         | ""
        "Vishnu"  | ""         | "Vishnu"
        ""        | "Aggarwal" | "Aggarwal"
        "Vishnu"  | "Aggarwal" | "Vishnu Aggarwal"
        null      | "Aggarwal" | "Aggarwal"
        "Vishnu"  | null       | "Vishnu"
        null      | null       | ""
    }

    def "tostringCheck"() {
        given:
        User user = new User(userName: userName)

        when:
        result == user.toString()

        then:
        noExceptionThrown()

        where:
        userName          | result
        "vishnu.aggarwal" | "vishnu.aggarwal"
    }

    def "userDetails"() {
        setup:
        String email = "vishnu.aggarwal@tothenew.com"
        String password = 'password'
        User user = new User(firstName: "Vishnu", lastName: "Aggarwal", email: email, password: password,
                confirmPassword: password, userName: "vishnu.aggarwal").save(flush: true)

        when:
        User tempUser = user.userDetails()

        then:
        tempUser.with {
            it.id = user.id
        }
    }
}
