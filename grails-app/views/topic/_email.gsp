<div class="modal fade openModal" id="sendinviteModal" tabindex="-1" role="dialog" aria-labelledby="sendinviteModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="sendinviteModalLabel">Send Invitation</h4>
            </div>

            <div class="modal-body">
                <g:form name="inviteForm" controller="topic" action="invite" class="form-horizontal">
                    <div class="form-group row">
                        <label for="email" class="col-sm-4 form-control-label">Email *</label>

                        <div class="col-sm-8">
                            <g:field name="email" type="email" class="form-control col-sm-8"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="topic" class="col-sm-4 form-control-label">Topic *</label>

                        <div class="col-sm-8">
                            <div class="dropdown">
                                <ls:showSubscribedTopics/>
                            </div>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-4 form-control-label">
                            <button type="button" class="btn btn-default btn-block" data-dismiss="modal">Close</button>
                        </div>

                        <div class="col-sm-8">
                            <g:submitButton name="emailBtn" type="submit"
                                            class="btn btn-primary btn-block" value="Invite"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>