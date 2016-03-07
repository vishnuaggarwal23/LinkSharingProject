package com.ttnd.linksharing

import grails.transaction.Transactional

@Transactional
class ExercisePersonService {

    def serviceMethod() {

    }

    def save(){
        ExercisePerson person=new ExercisePerson(name: 'vishnu',age:23)
        person.save(flush: true)
    }
}
