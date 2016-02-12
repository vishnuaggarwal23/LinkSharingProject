package com.ttnd.linksharing

import com.sun.org.apache.xpath.internal.operations.Bool
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(User)
class UserSpec extends Specification {
    def "User Validation for - #email"() {
        setup:
        User user = new User(userName: userName, firstName: firstName, lastName: lastName, email: emailId, password:
                passWord)

        when:
        Boolean result = user.validate()

        then:
        result == valid

        where:
        userName          | firstName | lastName   | emailId                        | passWord | valid
        null              | null      | null       | null                           | null     | false
        ""                | ""        | ""         | ""                             | ""       | false
        "vishnu.aggarwal" | "vishnu"  | "aggarwal" | "vishnu.aggarwal@"             | "123456" | false
        "vishnu.aggarwal" | "vishnu"  | "aggarwal" | "vishnu.aggarwal@tothenew.com" | "123456" | true
    }

    def "Check Full Name"() {
        setup:
        User user = new User(firstName: firstName, lastName: lastName)

        expect:
        result == user.getName()

        where:
        firstName | lastName   | result
        ""        | ""         | ""
        "Vishnu"  | ""         | "Vishnu"
        ""        | "Aggarwal" | "Aggarwal"
        "Vishnu"  | "Aggarwal" | "Vishnu Aggarwal"
    }

    def "Email Unique - #email"() {
        setup:
        String email = "vishnu.aggarwal@tothenew.com"
        String password = 'password'
        User user = new User(firstName: "Vishnu", lastName: "Aggarwal", email: email, password: password, userName:
                "vishnu.aggarwal")

        when:
        user.save()

        then:
        User.count() == 1

        when:
        User newUser = new User(firstName: "Vishnu A", lastName: "V Aggarwal", email: email, password: password,
                userName: "v.a")
        newUser.save()

        then:
        User.count() == 1
        newUser.errors.allErrors.size() == 1
        newUser.errors.getFieldErrorCount('email') == 1
    }
}
