package enums

/**
 * Created by vishnu on 11/2/16.
 */
enum Seriousness {
    SERIOUS,
    VERY_SERIOUS,
    CASUAL

    static checkSeriousness(String seriousness){
        switch (seriousness){
            case "SERIOUS":
            case "serious":
                return Seriousness.SERIOUS
            break
            case "very serious":
            case "very_serious":
            case "VERY SERIOUS":
            case "VERY_SERIOUS":
                return Seriousness.VERY_SERIOUS
            break
            case "casual":
            case "CASUAL":
                return Seriousness.CASUAL
        }
    }
}