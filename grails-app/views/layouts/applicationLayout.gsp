<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="Linksharing"/></title>
    <asset:stylesheet src="bootstrap.min.css"/>
    <asset:stylesheet src="bootstrap-theme.min.css"/>
    <asset:stylesheet src="font-awesome.min.css"/>
    <g:layoutHead/>
</head>

<body>
<nav class="navbar navbar-default navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#linksharingnavbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Link Sharing</a>
        </div>

        <div id="linksharingnavbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <form class="navbar-form form-inline">
                        <div class="form-group">
                            <label class="sr-only" for="searchboxTxt">Search</label>

                            <div class="input-group">
                                <div class="input-group-addon"><span class="glyphicon glyphicon-search"
                                                                     style="font-size: large"></span></div>
                                <input type="text" class="form-control" id="searchboxTxt" placeholder="Search">

                                <div class="input-group-addon"><span class="glyphicon glyphicon-remove"
                                                                     style="font-size: large"></span></div>
                            </div>
                        </div>
                    </form>
                </li>
                <g:if test="${session.user}">
                    <li>
                        <span>
                            <a class="btn" role="button" data-toggle="modal" data-target="#createtopicModal">
                                <span class="fa fa-weixin"></span>
                            </a>
                            <a class="btn" role="button" data-toggle="modal" data-target="#sendinviteModal">
                                <span class="glyphicon glyphicon-envelope"></span>
                            </a>
                            <a class="btn" role="button" data-toggle="modal" data-target="#sharelinkModal">
                                <span class="fa fa-link"></span>
                            </a>
                            <a class="btn" role="button" data-toggle="modal" data-target="#sharedocModal">
                                <span class="fa fa-file-o"></span>
                            </a>
                        </span>
                    </li>
                    <li>
                        <div class="dropdown">
                            <a class="btn dropdown-toggle" type="button" data-toggle="dropdown">
                                <span class="glyphicon glyphicon-user"
                                      style="font-size: large"></span>${session.user.getName()}
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Profile</a></li>
                                <li><a href="#">Users</a></li>
                                <li><a href="#">Topics</a></li>
                                <li><a href="#">Posts</a></li>
                                <li><g:link controller="login" action="logout">Logout</g:link></li>
                            </ul>
                        </div>
                    </li>
                </g:if>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <g:if test="${session.user}">
        <g:render template="/topic/create"/>
        <g:render template="/topic/email"/>
        <g:render template="/resource/linkResourceCreate"/>
        <g:render template="/resource/documentResourceCreate"/>

    </g:if>

    <g:if test="${!session.user}">
        <g:render template="/login/forgotPassword"/>
    </g:if>

    <g:if test="${flash.message}">
        <div class="alert alert-success alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                    aria-hidden="true">&times;</span></button>
            ${flash.message}
        </div>
    </g:if>


    <g:if test="${flash.error}">
        <div class="alert alert-danger alert-warning alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                    aria-hidden="true">&times;</span></button>
            ${flash.error}
        </div>
    </g:if>


    <g:layoutBody/>
</div>
<asset:javascript src="jquery.js"/>
<asset:javascript src="bootstrap.min.js"/>
</body>
</html>