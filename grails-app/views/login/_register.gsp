<div class="panel panel-default panel-primary">
    <div class="panel-heading">
        Register
    </div>

    <div class="panel-body">
        <g:uploadForm name="registration" controller="login" action="registration" class="form-horizontal">
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
                    <g:field name="email" type="email" class="form-control" id="email" required="required"/>
                </div>
            </div>

            <div class="form-group row">
                <label for="userName" class="col-sm-4 form-control-label">User Name *</label>

                <div class="col-sm-8">
                    <g:textField name="userName" type="text" class="form-control" id="userName" required="required"/>
                </div>
            </div>

            <div class="form-group row">
                <label for="password" class="col-sm-4 form-control-label">Password *</label>

                <div class="col-sm-8">
                    <g:field name="password" type="password" class="form-control" id="password" required="required"/>
                </div>
            </div>

            <div class="form-group row">
                <label for="confirmPassword" class="col-sm-4 form-control-label">Confirm Password *</label>

                <div class="col-sm-8">
                    <g:field name="confirmPassword" type="password" class="form-control" id="confirmPassword"
                             required="required"/>
                </div>
            </div>

            <div class="form-group row">
                <label for="photo" class="col-sm-4 form-control-label">Photo</label>

                <div class="col-sm-8">
                    <input type="file" name="file" id="photo">
                    <span class="file-custom"></span>
                </div>
            </div>

            <div class="form-group row">
                <div class="col-sm-offset-4 col-sm-8">
                    <g:submitButton type="submit" name="submit" value="Register"
                                    class="btn btn-primary btn-block"/>
                </div>
            </div>
        </g:uploadForm>
    </div>
</div>