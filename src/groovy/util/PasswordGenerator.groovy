package util
/**
 * Created by vishnu on 12/3/16.
 */
import org.apache.commons.lang.RandomStringUtils

class PasswordGenerator {
    static String getRandomPassword(){
        String charset = (('A'..'Z') + ('0'..'9')).join()
        Integer length = 6
        String randomString = RandomStringUtils.random(length, charset.toCharArray())
        return randomString
    }
}
