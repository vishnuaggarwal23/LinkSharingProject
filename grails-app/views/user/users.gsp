<%--
  Created by IntelliJ IDEA.
  User: vishnu
  Date: 11/3/16
  Time: 12:26 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="applicationLayout">
    <title>Users</title>
</head>

<body>
<div class="panel panel-default panel-primary">
    <div class="panel-heading">
        <div class="row">
            <div class="col-md-3">
                Users
            </div>

            <div class="col-md-3">
                <g:select name="usersType" from=""/>
            </div>

            <div class="col-md-3">
                <g:form name="adminUsersSearchForm" class="form-inline">
                    <div class="form-group">
                        <label class="sr-only" for="adminUsersSearchBox">Search</label>

                        <div class="input-group">
                            <div class="input-group-addon adminUsersSearchBtn"><span
                                    class="glyphicon glyphicon-search"
                                    style="font-size: large"></span></div>
                            <g:textField name="adminUsersSearchBox" type="text" class="form-control"
                                         id="adminUsersSearchBox" placeholder="Search"/>

                            <div class="input-group-addon adminUsersSearchCancelBtn"><span
                                    class="glyphicon glyphicon-remove"
                                    style="font-size: large"></span></div>
                        </div>
                    </div>
                </g:form>
            </div>

            <div class="col-md-3">
                <g:submitButton name="search" id="search" value="search" type="submit"/>
            </div>
        </div>
    </div>

    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-condensed table-hover">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Firstname</th>
                    <th>Lastname</th>
                    <th>Active</th>
                    <th>Manage</th>
                </tr>
                </thead>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </table>
        </div>
    </div>
</div>
</body>
</html>