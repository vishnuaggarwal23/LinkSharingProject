package com.ttnd.linksharing

class SessionCheckFilters {

    def filters = {
        loginCheck(controller: '*', action: 'save|delete|update|changeIsRead|join|invite|logout|upload') {
            before = {
                if (!session.user)
                    redirect(controller: "login", action: "index")
            }

        }

        userIndexcheck(controller: 'user', action: 'index|updateUserActiveStatus|edit|save|updatePassword|registeredUsers') {
            before = {
                if (!session.user)
                    redirect(controller: "login", action: "index")
            }
        }
    }
}
