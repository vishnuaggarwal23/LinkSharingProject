class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: 'login', action: 'index')
        "/registration"(controller: 'login', action: 'registration')
        "500"(view: '/error')


    }
}
