package com.ttnd.linksharing

import constants.AppConstants

class DocumentResource extends Resource {
    String filePath
    String fileName
    String contentType
    static transients = ['contentType',
                         'fileName']
    static constraints = {
        filePath(blank: false)
        fileName(bindable: true, nullable: true, blank: true)
        contentType(bindable: true, blank: false, validator: { val, obj ->
            return val.equals(AppConstants.DOCUMENT_CONTENT_TYPE)
        })
    }

    String toString() {
        return filePath
    }

    String getFileName() {
        String fileName = this.filePath.substring(this.filePath.lastIndexOf('/') + 1, this.filePath.length())
        return fileName ?: ""
    }
}