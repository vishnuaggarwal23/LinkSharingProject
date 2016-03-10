/**
 * Created by vishnu on 7/3/16.
 */
class ExercisePerson {
    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
        println "The name - ${this.name}"
    }
    String name

    Integer getAge() {
        return age
    }

    void setAge(Integer age) {
        this.age = age
        println "The age - ${this.age}"
    }
    Integer age

    ExercisePerson() {
        this.name = "vishnu aggarwal"
        this.age = 23
        println "Setting Constructor- ${this.name} ${this.age}"
    }
}
