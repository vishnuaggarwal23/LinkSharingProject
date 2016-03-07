package com.ttnd.linksharing

import constants.AppConstants
import grails.transaction.Transactional

class DocumentResource extends Resource {
    String filePath
    String fileName
    String contentType
    static transients = ['contentType', 'fileName']
    static constraints = {
        filePath(blank: false)
        fileName(bindable:true,nullable:true,blank:true)
        contentType(bindable: true, blank: false, validator: { val, obj ->
            return val.equals(AppConstants.DOCUMENT_CONTENT_TYPE)
        })
    }

    @Transactional
    Boolean deleteFile() {
        String filePath = createCriteria().get {
            projections {
                property('filePath')
            }
            eq('id', this.id)
        }
        boolean fileDeleted = new File(filePath).delete()
        if (fileDeleted) {
            this.delete(flush: true)
            return true
        } else {
            return false
        }
    }

    String toString() {
        return filePath
    }

    /*String getFileName(){
        String fileName =  this.filePath.substring(this.filePath.lastIndexOf('/')+1,this.filePath.length())
        return  fileName?:""
    }*/
}