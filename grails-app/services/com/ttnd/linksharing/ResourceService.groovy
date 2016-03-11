package com.ttnd.linksharing

import co.ResourceSearchCO
import grails.transaction.Transactional
import vo.PostVO

@Transactional
class ResourceService {

    def serviceMethod() {

    }

    def search(ResourceSearchCO resourceSearchCO) {
        List<PostVO> resources = []
        Resource.search(resourceSearchCO).list().each {
            resources.add(Resource.getPost(it.id))
        }
        return resources
    }
}
