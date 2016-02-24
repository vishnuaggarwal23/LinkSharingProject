class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: 'login', action: 'index')
        "/registration"(controller: 'login', action: 'registration')
        "/login"(controller: 'login', action: 'login')
        "/logout"(controller: 'login', action: 'logout')
        "500"(view: '/error')


    }
}
