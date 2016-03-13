class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: 'login', action: 'index')
        "/user"(controller: 'user', action: 'index')
        "404"(view: '/404')
        "500"(view: '/error')
    }
}
