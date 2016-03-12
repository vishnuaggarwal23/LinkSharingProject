package co

import com.ttnd.linksharing.Topic
import com.ttnd.linksharing.User
import grails.validation.Validateable

/**
 * Created by vishnu on 12/3/16.
 */
@Validateable
class TopicCO {
    Long topicId
    String topicName
    String visibility
    User createdBy
    String newName

    static constraints = {
        topicId(nullable: true, blank: true)
        topicName(nullable: true, blank: true)
        visibility(nullable: true, blank: true)
        newName(nullable: true, blank: true)
        createdBy(nullable: true, blank: true)
    }

    public Topic getTopic() {
        return Topic.findOrCreateByNameAndCreatedBy(topicName,createdBy)
    }
}