package com.ttnd.linksharing

class SessionCheckFilters {

    def filters = {

        loginCheck(controller: '*', action: 'save|delete|update|changeIsRead|upload|updatePassword|logout|edit') {
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

        /*consoleCheck(uri: '/console') {
            before = {
                redirect(controller: 'login', action: 'index')
            }
        }*/
    }
}
