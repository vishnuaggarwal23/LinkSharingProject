package co

import grails.validation.Validateable
import org.apache.ivy.core.settings.Validatable

/**
 * Created by vishnu on 2/3/16.
 */
@Validateable
class ExercisePersonCO {
    String name
    Integer age

    static constants={
        name unique:true
        age null:false
    }
}
