package com.ttnd.linksharing

import com.sun.org.apache.xpath.internal.operations.Bool
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(User)
class UserSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
    }

    def "Email Validation for - #email"() {
        setup:
        String email = "vishnu.aggarwal@tothenew.com"
        String password = "123456"
        User user = new User(userName: userName, firstName: firstName, lastName: lastName, email: emailId, password: passWord)

        when:
        Boolean result = user.validate()

        then:
        result == valid

        where:
        userName          | firstName | lastName   | emailId                        | passWord | valid
        ""                | ""        | ""         | ""                             | ""       | false
        "vishnu.aggarwal" | "vishnu"  | "aggarwal" | "vishnu.aggarwal@"             | "123456" | false
        "vishnu.aggarwal" | "vishnu"  | "aggarwal" | "vishnu.aggarwal@tothenew.com" | "123456" | true
    }

    def "Check First Name"() {
        setup:
        User user = new User(firstName: name)

        expect:
        result == user.getFirstName()

        where:
        name     | result
        ""       | null
        "Vishnu" | "Vishnu"
    }

    def "Check Last Name"() {
        setup:
        User user = new User(lastName: name)

        expect:
        result == user.getLastName()

        where:
        name       | result
        ""         | null
        "Aggarwal" | "Aggarwal"
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

    def "Check Password"() {
        setup:
        User user = new User(password: password)


    }

    def "Email Unique - #email"() {
        setup:
        String email = "vishnu.aggarwal@tothenew.com"
        String password = 'password'
        User user = new User(firstName: "Vishnu", lastName: "Aggarwal", email: email, password: password)

        when:
        user.save()

        then:
        user.count() == 1

        when:
        User newUser = new User(firstName: "Vishnu A", lastName: "V Aggarwal", email: email, password: password)
        newUser.save()

        then:
        User.count() == 1
        newUser.errors.allErrors.size() == 1
        newUser.errors.getFieldErrorCount('email') == 1
    }
}
