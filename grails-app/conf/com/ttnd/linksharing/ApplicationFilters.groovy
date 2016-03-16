package com.ttnd.linksharing

class ApplicationFilters {

    def filters = {
        all(controller: '*', action: '*') {
            before = {
                log.info params
            }
        }

        console(controller: 'console', action: '*') {
            before = {
                if (!(session.user?.isAdmin)) {
                    redirect(controller: 'login', action: 'index')
                }
            }
        }
    }
}
