package dto

import grails.validation.Validateable

/**
 * Created by vishnu on 12/3/16.
 */

/*@Validateable*/
class EmailDTO {
    List<String> to
    String subject
    String view
    String content
    Map model

    /*static constraints={
        to(null:false)
        subject(null:false)
        view(null:false)
        content(null:false)
        model(null:true)
    }*/
}