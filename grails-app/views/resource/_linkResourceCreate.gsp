<div class="modal fade" id="sharelinkModal" tabindex="-1" role="dialog" aria-labelledby="sharelinkModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="sharelinkModalLabel">Share Link</h4>
            </div>

            <div class="modal-body">
                <g:form class="form-horizontal" controller="resource" action="saveLinkResource">
                    <div class="form-group row">
                        <label for="link" class="col-sm-4 form-control-label">Link *</label>

                        <div class="col-sm-8">
                            <g:field type="url" id="link" name="url"/>
                        </div>
                    </div>

                    <div class="form-horizontal row">
                        <label for="description" class="col-sm-4 form-control-label">Description *</label>

                        <div class="col-sm-8">
                            <g:textArea name="description" id="description"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="linkTopic" class="col-sm-4 form-control-label">Topic *</label>

                        <div class="col-sm-8">
                            <div class="dropdown">
                                <g:select class="btn dropdown-toggle" data-toggle="dropdown" name="topicName"
                                          id="linkTopic" from="${topicsOfUser}"/>
                            </div>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-4 form-control-label">
                            <button type="button" class="btn btn-default btn-block" data-dismiss="modal">Close</button>
                        </div>

                        <div class="col-sm-8">
                            <g:submitButton type="submit" name="saveResource" class="btn btn-primary btn-block"
                                            action="/resource/saveLinkResource"
                                            value="Save Resource"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>