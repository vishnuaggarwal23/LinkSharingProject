class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: 'login', action: 'index')
        "/user"(controller: 'user', action: 'index')

        "401"(view: '/error/401')
        "403"(view: '/error/403')
        "404"(view: '/error/404')
        "440"(view: '/error/440')
        "500"(view: '/error/500')
    }
}
