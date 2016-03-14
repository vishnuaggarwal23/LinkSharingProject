<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="applicationLayout">
    <title>Edit Profile</title>
</head>

<body>
<div class="row">
    <div class="col-md-4">
        <div class="row">
            <g:render template="/templates/userPanel" model="[userDetails: userDetails]"/>
            <g:hiddenField name="id" value="${userDetails.id}"/>
        </div>

        <div class="row">
            <div class="panel panel-default panel-primary">
                <div class="panel-heading">
                    Topics
                </div>

                <div id="createdTopics">
                    <g:render template="/topic/list"/>
                </div>
            </div>
        </div>
    </div>

    <div class="col-md-7 col-md-push-1">
        <div class="row">
            <div class="panel panel-default panel-primary">
                <div class="panel-heading">
                    Profile
                </div>

                <div class="panel-body">
                    <g:uploadForm name="registration" controller="user" action="save" class="form-horizontal">
                        <div class="form-group row">
                            <g:hiddenField name="id" value="${userCo?.id}"/>
                            <label for="firstName" class="col-sm-4 form-control-label">First Name *</label>

                            <div class="col-sm-8">
                                <g:textField name="firstName" type="text" class="form-control" id="firstName"
                                             value="${userCo?.firstName}"/>
                            </div>

                            <div class="alert-danger">
                                <g:fieldError field="firstName" bean="${userCo}"/>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="lastName" class="col-sm-4 form-control-label">Last Name *</label>

                            <div class="col-sm-8">
                                <g:textField name="lastName" type="text" class="form-control" id="lastName"
                                             value="${userCo?.lastName}"/>
                            </div>

                            <div class="alert-danger"><g:fieldError field="lastName" bean="${userCo}"/></div>
                        </div>

                        <div class="form-group row">
                            <label for="userName" class="col-sm-4 form-control-label">User Name *</label>

                            <div class="col-sm-8">
                                <input type="text" value="${userCo?.userName}" readonly class="form-control">
                                %{--<g:textField name="userName" type="text" class="form-control" id="userName"
                                             value="${userCo?.userName}" readonly="true"/>--}%
                            </div>

                            <div class="alert-danger"><g:fieldError field="userName" bean="${userCo}"/></div>
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
                                <g:submitButton type="submit" name="submit" value="Update"
                                                class="btn btn-primary btn-block"/>
                            </div>
                        </div>
                    </g:uploadForm>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="panel panel-default panel-primary">
                <div class="panel-heading">
                    Change Password
                </div>

                <div class="panel-body">
                    <g:form name="updatePassword"controller="user" action="updatePassword">
                        <div class="form-group row">
                            <g:hiddenField name="id" value="${userCo?.id}"/>
                            <label for="oldPassword" class="col-sm-4 form-control-label">Old Password *</label>

                            <div class="col-sm-8">
                                <g:field name="oldPassword" type="password" class="form-control"/>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="password" class="col-sm-4 form-control-label">Password *</label>

                            <div class="col-sm-8">
                                <g:field name="password" type="password" class="form-control"/>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="confirmPassword" class="col-sm-4 form-control-label">Confirm Password *</label>

                            <div class="col-sm-8">
                                <g:field name="confirmPassword" type="password" class="form-control"/>
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col-sm-offset-4 col-sm-8">
                                <g:submitButton type="submit" name="submit" value="Update"
                                                class="btn btn-primary btn-block"/>
                            </div>
                        </div>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
</div>
<g:javascript>
    $(document).ready(function () {

        $.ajax({
            url: "/user/topics",
            data: {id: $("#id").val()},
            success: function (result) {
                $("#createdTopics").html(result)
            }

        });
    })
</g:javascript>

</body>
</html>