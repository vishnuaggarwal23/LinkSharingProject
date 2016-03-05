package enums

/**
 * Created by vishnu on 11/2/16.
 */
enum Visibility {
    PUBLIC,
    PRIVATE

    static checkVisibility(String visibility) {
        switch (visibility) {
            case "public":
            case "PUBLIC":
                return Visibility.PUBLIC
                break
            case "private":
            case "PRIVATE":
                return Visibility.PRIVATE
                break
        }
    }
}