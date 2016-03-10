<div class="modal fade" id="forgotPasswordModal" tabindex="-1" role="dialog" aria-labelledby="forgotPasswordModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="forgotPasswordModalLabel">Forgot Password</h4>
            </div>

            <div class="modal-body">
                <g:form class="form-horizontal" name="forgotPassword" id="forgotPassword" controller="login"
                        action="forgotPassword">

                    <div class="form-group row">
                        <div class="col-sm-4 form-control-label">
                            <button type="button" class="btn btn-default btn-block" data-dismiss="modal">Close</button>
                        </div>

                        <div class="col-sm-8">
                            <g:submitButton type="submit" name="forgotPasswordBtn" value="Forgot Password"
                                            formaction=""
                                            class="btn btn-block btn-primary"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>