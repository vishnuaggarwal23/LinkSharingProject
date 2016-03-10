<div class="panel panel-default panel-primary">
    <div class="panel-heading">
        Login
    </div>

    <div class="panel-body">
        <g:form name="loginForm" class="form-horizontal" controller="login" action="login">
            <div class="form-group row">
                <label for="loginUserName" class="col-sm-4 form-control-label">User Name *</label>

                <div class="col-sm-8">
                    <g:textField name="loginUserName" class="form-control" id="loginUserName" required="required"/>
                </div>
            </div>

            <div class="form-group row">
                <label for="loginPassword" class="col-sm-4 form-control-label">Password *</label>

                <div class="col-sm-8">
                    <g:field name="loginPassword" type="password" class="form-control" id="loginPassword" required="required"/>
                </div>
            </div>

            <div class="form-group row">
                <div class="col-sm-4 form-control-label">
                    <a name="forgotPasswordLink" class="btn btn-default" data-toggle="modal"
                            data-target="#forgotPasswordModal">Forgot
                                Password</a>
                </div>

                <div class="col-sm-8">
                    <g:submitButton name="loginBtn" value="Login" type="submit" formaction="/login/login"
                                    class="btn btn-primary btn-block"/>
                </div>
            </div>
        </g:form>
    </div>
</div>
