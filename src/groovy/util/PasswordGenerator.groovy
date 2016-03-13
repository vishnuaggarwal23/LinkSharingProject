package util

import org.apache.commons.lang.RandomStringUtils

class PasswordGenerator {
    static String getRandomPassword() {
        String charset = (('A'..'Z') + ('0'..'9')).join()
        Integer length = 6
        return RandomStringUtils.random(length, charset.toCharArray())
    }
}
