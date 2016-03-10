// Place your Spring DSL code here
beans = {
    myBean(ExercisePerson) {
        name = "vishnu"
        age = 23
    }

    constructorBean(ExercisePerson) { bean ->
        bean.scope='prototype'
    }
}
