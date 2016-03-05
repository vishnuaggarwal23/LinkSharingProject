package com.ttnd.linksharing

class ApplicationFilters {

    def filters = {
        all(controller: '*', action: '*') {
            before = {
                log.info params.name
                log.info params.getRequest()
                log.info session.getId()
                log.info params
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
