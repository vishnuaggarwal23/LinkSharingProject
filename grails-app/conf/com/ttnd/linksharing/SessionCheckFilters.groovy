package com.ttnd.linksharing

class SessionCheckFilters {

    def filters = {
        /*notLogin(controller: 'login', invert: true) {
            before = {
                if (!session.user) {
                    redirect(controller: 'login', action: 'index')
                }
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }*/

        loginCheck(controller: '*', action: 'save|delete|update|changeIsRead') {
            before = {
                if (!session.user)
                    redirect(controller: "login", action: "index")
            }

        }

        userIndexcheck(controller: 'user', action: 'index') {
            before = {

                if (!session.user)
                    redirect(controller: "login", action: "index")
            }
        }
    }
}
