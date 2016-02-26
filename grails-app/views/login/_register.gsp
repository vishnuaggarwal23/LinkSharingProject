<div class="panel panel-default panel-primary">
    <div class="panel-heading">
        Register
    </div>

    <div class="panel-body">
        <g:form name="userRegistrationForm" controller="login" action="registration" class="form-horizontal">
            <div class="form-group row">
                <label for="firstName" class="col-sm-4 form-control-label">First Name *</label>

                <div class="col-sm-8">
                    <g:textField name="firstName" type="text" class="form-control" id="firstName" required="required"/>
                </div>
            </div>

            <div class="form-group row">
                <label for="lastName" class="col-sm-4 form-control-label">Last Name *</label>

                <div class="col-sm-8">
                    <g:textField name="lastName" type="text" class="form-control" id="lastName" required="required"/>
                </div>
            </div>

            <div class="form-group row">
                <label for="email" class="col-sm-4 form-control-label">Email *</label>

                <div class="col-sm-8">
                    <input type="email" class="form-control" id="email" required="required">
                </div>
            </div>

            <div class="form-group row">
                <label for="usernameReg" class="col-sm-4 form-control-label">User Name *</label>

                <div class="col-sm-8">
                    <input type="text" class="form-control" id="usernameReg" required="required">
                </div>
            </div>

            <div class="form-group row">
                <label for="password" class="col-sm-4 form-control-label">Password *</label>

                <div class="col-sm-8">
                    <input type="password" class="form-control" id="password" required="required">
                </div>
            </div>

            <div class="form-group row">
                <label for="userNameTxt" class="col-sm-4 form-control-label">User Name *</label>

                <div class="col-sm-8">
                    <input type="text" class="form-control" id="userNameTxt" required="required">
                </div>
            </div>

            <div class="form-group row">
                <label for="passwordReg" class="col-sm-4 form-control-label">Password *</label>

                <div class="col-sm-8">
                    <input type="password" class="form-control" id="passwordReg" required="required">
                </div>
            </div>

            <div class="form-group row">
                <label for="confirmpasswordReg" class="col-sm-4 form-control-label">Confirm Password
                *</label>

                <div class="col-sm-8">
                    <input type="password" class="form-control" id="confirmpasswordReg" required="required">
                </div>
            </div>

            <div class="form-group row">
                <label for="fileSelector" class="col-sm-4 form-control-label">Photo *</label>

                <div class="col-sm-8">
                    <input type="file" id="fileSelector" required="required">
                    <span class="file-custom"></span>
                </div>
            </div>

            <div class="form-group row">
                <div class="col-sm-offset-4 col-sm-8">
                    <button type="button" class="btn btn-primary btn-block">Submit</button>
                </div>
            </div>
        </g:form>
    </div>
</div>