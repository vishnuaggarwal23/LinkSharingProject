package com.ttnd.linksharing

class ApplicationFilters {

    def filters = {
        all(controller: '*', action: '*') {
            before = {
                log.info params
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
