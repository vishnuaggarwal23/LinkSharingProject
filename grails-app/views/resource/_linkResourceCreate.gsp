<div class="modal fade" id="sharelinkModal" tabindex="-1" role="dialog" aria-labelledby="sharelinkModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="sharelinkModalLabel">Share Link</h4>
            </div>

            <div class="modal-body">
                <g:form class="form-horizontal" controller="linkResource" action="save">
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
                        <label for="topic" class="col-sm-4 form-control-label">Topic *</label>

                        <div class="col-sm-8">
                            <div class="dropdown">
                                %{--<g:select class="btn dropdown-toggle" data-toggle="dropdown" name="topic"
                                          id="linkTopic" from="${subscribedTopics}" optionKey="id"/>--}%
                                <g:render name="topic" id="linkTopic" class="btn dropdown-toggle"
                                          template="/templates/topicSelect"/>
                            </div>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-4 form-control-label">
                            <button type="button" class="btn btn-default btn-block" data-dismiss="modal">Close</button>
                        </div>

                        <div class="col-sm-8">
                            <g:submitButton type="submit" name="saveResource" class="btn btn-primary btn-block"
                                            action="/linkresource/save"
                                            value="Save Resource"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>