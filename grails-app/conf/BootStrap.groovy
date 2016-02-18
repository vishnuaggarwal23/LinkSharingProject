import com.ttnd.linksharing.*
import constants.AppConstants
import enums.Visibility

class BootStrap {
    def grailsApplication
    def init = { servletContext ->
        List<User> users = createUser()
        List<Topic> topics = createTopic(users)
        //List<Resource> resources=createResources()
        /*List<Topic> topics = []
        users = createUser()
        println(grailsApplication.config.grails.sampleValue)
        users.each { user ->
            validate(user)
        }
        users.each { user ->
            topics = createTopics(user)
            topics.each{topic->
                validate(topic)
            }
        }
        new Topic(name: "Topic1",createdBy: users.first(),visibility: Visibility.PRIVATE).save()*/
    }

    List<User> createUser() {
        List<User> users = []
        User normalUser = new User(userName: "user", firstName: "fname", lastName: "lname", email: "user@ttnd.com",
                password: AppConstants.PASSWORD, isAdmin: false)
        User adminUser = new User(userName: "admin", firstName: "fname", lastName: "lname", email: "admin@ttnd.com",
                password: AppConstants.PASSWORD, isAdmin: true)
        Integer countUsers = User.count()
        if (!countUsers) {
            log.info "Users doesnot exists in the system "
            log.info "Creating new users "
            if (normalUser.save(flush: true, failOnError: true)) {
                log.info "User ${normalUser} saved"
                users.add(normalUser)
            } else {
                log.info "User ${normalUser} cannot be saved"
            }
            if (adminUser.save(flush: true, failOnError: true)) {
                log.info "User ${adminUser} saved"
                users.add(adminUser)
            } else {
                log.info "User ${adminUser} cannot be saved"
            }
        } else {
            log.info "Users exists in the system "
            users = User.findAll("from User")
        }
        return users
    }

    List<Topic> createTopic(List<User> users) {
        List<Topic> topics = []
        users.each { User user ->
            Integer countTopics = Topic.countByCreatedBy(user)
            if (!countTopics) {
                log.info "Creating 5 Topics for user ${user}"
                (1..5).each {
                    Topic topic = new Topic(name: "Topic${it}", visibility: AppConstants.VISIBILITY, createdBy: user)
                    if (topic.save(flush: true, failOnError: true)) {
                        log.info "Topic ${topic} saved for user ${user}"
                        user.addToTopics(topic)
                        topics.add(topic)
                    } else {
                        log.info "Topic ${topic} cannot be saved"
                    }
                }
            } else {
                log.info "User ${user} already have some topics created "
                topics+=Topic.findAll("from Topic as topic where topic.createdBy=:creator",[creator:user])
            }
        }
        return topics
    }

    /*List<User> checkUserExists() {
        Integer normalUserCount = User.countByIsAdmin(false)
        Integer adminUserCount = User.countByIsAdmin(true)
        List<User> users=[]
        User user;

        if (normalUserCount == 0) {
            user=new User();
            user.setUserName("normaluser")
            user.setFirstName("fname")
            user.setLastName("lname")
            user.setEmail("normaluser@xyz.com")
            user.setIsAdmin(false)
            user.setPassword(AppConstants.PASSWORD)
            createUser(user, users)
            validate(user)
        }
        if (adminUserCount == 0) {
            user=new User();
            user.setUserName("adminuser")
            user.setFirstName("fname")
            user.setLastName("lname")
            user.setEmail("adminuser@xyz.com")
            user.setIsAdmin(true)
            user.setPassword(AppConstants.PASSWORD)
            createUser(user, users)
            validate(user)
        } else {
            users = User.findAll()
        }
        return users
    }

    void createUser(User user, List<User> users) {
        if (user.save()) {
            users.add(user)
            log.info "User Saved ${user}"
        } else {
            log.info "User not Saved ${user} ---- ${user.errors.allErrors}"
        }
    }

    void validate(def obj) {
            log.info "---Before validate HasErrors--- ${obj.hasErrors()}"
            log.info "---User is valid--- ${obj.validate()}"
            log.info "---After validate HasErrors--- ${obj.hasErrors()}"
            obj.save(flush: true)
    }*/

    /*List<User> createUser() {
        List<User> users = []
        User user = new User(userName: "user", firstName: "Vishnu", lastName: "Aggarwal", password: AppConstants.PASSWORD, email:
                "user@ttndlinksharing.com")
        findOrCheckUser(user, users)
        user = new User(userName: "admin", firstName: "Admin", lastName: "linksharing", password: AppConstants.PASSWORD, email:
                "admin@ttndlinksharing.com", isAdmin: true)
        findOrCheckUser(user, users)
        return users
    }

    void validate(def user) {
        log.info "---Before validate HasErrors--- ${user.hasErrors()}"
        log.info "---User is valid--- ${user.validate()}"
        log.info "---After validate HasErrors--- ${user.hasErrors()}"
        user.save(flush: true)
    }

    void findOrCheckUser(User user, List<User> users) {
        User checkUser = User.findByEmail(user.email)
        if (!checkUser) {
            if (user.save()) {
                users.add(user)
                log.info "User ${user} saved"
            } else {
                log.info "User ${user} not saved -----  ${user.errors.allErrors}"
            }
        } else {
            log.info "User already exists ----- ${user}"
            //user.save(failOnError: true)
        }
    }

    List<Topic> createTopics(User user){
        int countOfTopicsPerUser=topicCountPerUser(user)
        List<Topic> topics=[]
        if(countOfTopicsPerUser){
            (1..5).each{
                Topic topic=new Topic(name:"Topic${it}",createdBy: user,visibility:AppConstants.VISIBILITY)
                findOrCheckTopic(user,topic,topics)
            }
        }
        else{
            log.info "user ${user} has some topics ${topics}"
        }
        return topics
    }

    void findOrCheckTopic(User user,Topic topic,List<Topic> topics){
        Topic checkTopic=Topic.findByNameAndCreatedBy(topic.name,user)
        if(!checkTopic){
            if(topic.save()){
                topics.add(topic)
                log.info "Topic ${topic} saved"
            }
            else{
                log.info "Topic ${topic} not saved ----- ${topic.errors.allErrors}"
            }
        }
        else{
            log.info "Topic ${topic} already exists for the user ${user}"
            //topic.save(failOnError: true)
        }
    }

    Integer topicCountPerUser(User creator){
        return Topic.countByCreatedBy(creator)
    }*/

    def destroy = {
    }
}
