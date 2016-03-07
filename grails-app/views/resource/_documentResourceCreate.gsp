<div class="modal fade" id="sharedocModal" tabindex="-1" role="dialog" aria-labelledby="sharedocModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="sharedocModalLabel">Share Document</h4>
            </div>

            <div class="modal-body">
                <g:uploadForm name="upload" class="form-horizontal" controller="documentResource" action="save">
                    <div class="form-group row">
                        <label for="doc" class="col-sm-4 form-control-label">Document *</label>

                        <div class="col-sm-8">
                            <input type="file" id="doc" name="file" required>
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
                                <div class="dropdown">
                                    %{--<g:select class="btn dropdown-toggle" data-toggle="dropdown" name="topicName"
                                              id="docTopic" from="${topicsOfUser}"/>--}%
                                    <g:render name="topic" id="docTopic" class="btn dropdown-toggle"
                                              template="/templates/topicSelect"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-4 form-control-label">
                            <button type="button" class="btn btn-default btn-block" data-dismiss="modal">Close</button>
                        </div>

                        <div class="col-sm-8">
                            <g:submitButton type="submit" name="submit" class="btn btn-primary btn-block"
                                            value="Save Resource"/>
                        </div>
                    </div>
                </g:uploadForm>
            </div>
        </div>
    </div>
</div>