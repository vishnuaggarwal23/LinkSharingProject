package enums

/**
 * Created by vishnu on 11/3/16.
 */
enum UserActiveStatus {
    ALL_USERS("All Users"),
    ACTIVE("Active"),
    INACTIVE("Inactive")

    final String value
    UserActiveStatus(String value){ this.value = value }

    @Override
    String toString(){ value }
    String getKey() { name() }
}