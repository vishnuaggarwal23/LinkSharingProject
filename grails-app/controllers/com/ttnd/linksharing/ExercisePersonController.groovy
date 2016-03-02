package com.ttnd.linksharing

import co.ExercisePersonCO

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

class ExercisePersonController {
    def getActionParams(String name,Integer age){
        render "${name}, ${age}"
        ExercisePerson person=new ExercisePerson(name: name,age:age)
        render "${person.name}, ${person.age}"
    }

    def getParamsConversion(){
        ExercisePerson person=new ExercisePerson(name: 'vishnu',age: 23)
        render "${person.properties}\n"
        render "${params.getProperties()}\n"
        person.properties=params
        render "${person.properties}\n"
        render "${params.getProperties()}"
    }

    def fetchList(){
        render "${params.list("items")}<br/>${params.list("items").getProperties()}"
        render "<br/> ${params.properties}"
    }

    def paramsConversion(){
        int age=params.int("age")
        Date dob=params.date("dob","dd-MMM-yy")
        render "${age}<br/> ${dob}<br/> ${params.getProperties()}"
    }

    def checkErrors(){
        render "${params}"
        ExercisePerson person=new ExercisePerson(params)
        println person?.hasErrors()
        if(person?.hasErrors()){
            println "${person.errors.getFieldError('age')}"
            if(person.errors.hasFieldErrors('age')){
                println person.errors.getFieldError('age').rejectedValue
            }
        }
        render "<br/> ${person.properties}"
    }

    def commandObject(ExercisePersonCO personCO){
        render "${personCO.properties} <br/>"
        render "${personCO.errors} <br/>"
        render "${personCO.validate()} <br/>"
        if(personCO.hasErrors()){
            render "${personCO.errors.getFieldError('age').rejectedValue}"
        }
    }
}