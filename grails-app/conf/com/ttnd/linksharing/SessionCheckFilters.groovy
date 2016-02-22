package com.ttnd.linksharing

class SessionCheckFilters {

    def filters = {
        notLogin(controller: 'login', action: '*', invert: true) {
            before = {
                if (!session.user) {
                    redirect(controller: 'login', action: 'index')
                }
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
