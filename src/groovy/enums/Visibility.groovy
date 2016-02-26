package enums

/**
 * Created by vishnu on 11/2/16.
 */
enum Visibility {
    PUBLIC,
    PRIVATE

    final String value

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

////    Visibility(String value){
////        this.value=value
////    }
//
//    String toString(){
//        return value
//    }
//
//    String getKey(){
//        return name()
//    }
}