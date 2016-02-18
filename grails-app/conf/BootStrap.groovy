import com.ttnd.linksharing.*
import constants.AppConstants
import enums.Visibility

class BootStrap {
    def grailsApplication
    def init = { servletContext ->
        List<User> users = createUser()
        List<Topic> topics = createTopic(users)
        List<Resource> resources = createResources(topics)
        println(grailsApplication.config.grails.sampleValue)
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
                topics += Topic.findAll("from Topic as topic where topic.createdBy=:creator", [creator: user])
            }
        }
        return topics
    }

    List<Resource> createResources(List<Topic> topics) {
        List<Resource> resources = []
        topics.each { Topic topic ->
            Integer countResources = Resource.countByTopic(topic)
            if (!countResources) {
                2.times {
                    Resource documentResource = new DocumentResource(description: "topic ${topic} doc", createdBy: topic
                            .createdBy, filePath: "file/path", topic: topic)
                    Resource linkResource = new LinkResource(description: "topic ${topic} link", createdBy: topic
                            .createdBy, url: "https://www.google.co.in", topic: topic)
                    if (documentResource.save(flush: true, failOnError: true)) {
                        log.info "document resource ${documentResource} saved"
                        resources.add(documentResource)
                        topic.addToResources(documentResource)
                    } else {
                        log.info "document resource ${documentResource} not saved"
                    }
                    if (linkResource.save(flush: true, failOnError: true)) {
                        log.info "document resource ${linkResource} saved"
                        resources.add(linkResource)
                        topic.addToResources(linkResource)
                    } else {
                        log.info "document resource ${linkResource} not saved"
                    }
                }
            } else {
                resources += Resource.findAll("from Resource")
            }
        }
        return resources
    }
    def destroy = {
    }
}
