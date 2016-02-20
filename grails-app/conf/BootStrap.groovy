import com.ttnd.linksharing.*
import constants.AppConstants
import enums.Visibility

class BootStrap {
    def grailsApplication
    def init = { servletContext ->
        List<User> users = createUser()
        List<Topic> topics = createTopic(users)
        List<Resource> resources = createResources(topics)
        List<Subscription> subscriptions = createSubscription(users, topics)
        List<ReadingItem> readingItems = createReadingItems(users, topics, resources)
        List<ResourceRating> resourceRatings = createResourceRatings(users, readingItems)
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
            log.info "Creating new users "
            if (User.save(normalUser)) {
                log.info "${normalUser} saved"
                users.add(normalUser)
            } else {
                log.error "${normalUser} cannot be saved--- ${normalUser.errors.allErrors}"
            }
            if (User.save(adminUser)) {
                log.info "${adminUser} saved"
                users.add(adminUser)
            } else {
                log.error "${adminUser} cannot be saved--- ${adminUser.errors.allErrors}"
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
                log.info "Creating 5 Topics for ${user}"
                (1..5).each {
                    Topic topic = new Topic(name: "${user}->Topic${it}", visibility: AppConstants.VISIBILITY,
                            createdBy: user)
                    if (Topic.save(topic)) {
                        log.info "${topic} saved for ${user}"
                        topics.add(topic)
                        user.addToTopics(topic)
                    } else {
                        log.error "${topic} is not saved for ${user}"
                    }
                }
            } else {
                log.info "${user} already have some topics created "
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
                    if (Resource.save(documentResource)) {
                        log.info "${documentResource} saved by ${topic.createdBy} for ${topic}"
                        resources.add(documentResource)
                        topic.addToResources(documentResource)
                        topic.createdBy.addToResources(documentResource)
                    } else {
                        log.error "${documentResource} not saved--- ${documentResource.errors.allErrors}"
                    }
                    if (Resource.save(linkResource)) {
                        log.info "${linkResource} saved by ${topic.createdBy} for ${topic}"
                        resources.add(linkResource)
                        topic.addToResources(linkResource)
                        topic.createdBy.addToResources(linkResource)
                    } else {
                        log.error "${linkResource} not saved--- ${linkResource.errors.allErrors}"
                    }
                }
            } else {
                resources += Resource.findAll("from Resource")
            }
        }
        return resources
    }

    List<Subscription> createSubscription(List<User> users, List<Topic> topics) {
        List<Subscription> subscriptions = []
        subscriptions += Subscription.findAll("from Subscription")
        users.each { User user ->
            topics.each { Topic topic ->
                if (Subscription.findByUserAndTopic(user, topic) == null) {
                    Subscription subscription = new Subscription(user: user, topic: topic, seriousness: AppConstants.SERIOUSNESS)
                    if (Subscription.save(subscription)) {
                        if (Subscription.save(subscription)) {
                            log.info "${subscription}-> ${user} subscribed for ${topic}"
                            subscriptions.add(subscription)
                            topic.addToSubscriptions(subscription)
                            user.addToSubscriptions(subscription)
                        } else {
                            log.error "Subscription does not occurs--- ${subscription.errors.allErrors}"
                        }
                    }
                } else {
                    log.info "User ${user} already subscribed to Topic ${topic}"
                }
            }
        }
        return subscriptions
    }

    List<ReadingItem> createReadingItems(List<User> users, List<Topic> topics, List<Resource> resources) {
        List<ReadingItem> readingItems = []
        users.each { User user ->
            topics.each { Topic topic ->
                if (Subscription.findByUserAndTopic(user, topic)) {
                    topic.resources.each { resource ->
                        if (resource.createdBy != user && !user.readingItems?.contains(resource)) {
                            ReadingItem readingItem = new ReadingItem(user: user, resource: resource, isRead: false)
                            if (ReadingItem.save(readingItem)) {
                                log.info "${readingItem} saved in ${user}'s list"
                                readingItems.add(readingItem)
                                resource.addToReadingItems(readingItem)
                                user.addToReadingItems(readingItem)
                            } else {
                                log.error "${readingItem} is not saved in ${user}'s list--- ${readingItem.errors.allErrors}"
                            }
                        }
                    }
                }
            }
        }
        return readingItems
    }

    List<ResourceRating> createResourceRatings(List<User> users, List<ReadingItem> readingItems) {
        List<ResourceRating> resourceRatings = []
        users.each { User user ->
            user.readingItems?.each { ReadingItem readingItem ->
                if (!readingItem.isRead) {
                    ResourceRating resourceRating = new ResourceRating(score: AppConstants.RATING, user: readingItem.user,
                            resource: readingItem.resource)
                    if (ResourceRating.save(resourceRating)) {
                        log.info "${resourceRating} rating for ${readingItem.resource} by ${readingItem.user}"
                        resourceRatings.add(resourceRating)
                        readingItem.resource.addToResourceRating(resourceRating)
                        readingItem.user.addToResourceRatings(resourceRating)
                    } else {
                        log.error "${resourceRating} rating not set for ${readingItem.resource} by ${readingItem.user}---" +
                                " ${resourceRating.errors.allErrors}"
                    }
                } else {
                    log.info "${readingItem.user} cannot rate"
                }
            }
        }
        return resourceRatings
    }

    def destroy = {
    }
}