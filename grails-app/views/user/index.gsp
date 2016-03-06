<%--
  Created by IntelliJ IDEA.
  User: vishnu
  Date: 26/2/16
  Time: 3:45 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="applicationLayout">
    <title></title>
</head>

<body>
<div class="row">
    <div class="col-md-4">
        <div class="row">
            <div class="panel panel-default panel-primary">
                <g:render template="/templates/userPanel" model="[userDetails: userDetails]"/>
            </div>
        </div>

        <div class="row">
            <div class="panel panel-default panel-primary">
                <div class="panel-heading">
                    Subscriptions
                    <div class="pull-right">
                        <a href="#" class="text-primary">View All</a>
                    </div>
                </div>

                <g:each in="${subscriptions}">
                    <g:render template="/templates/topicPanel" model="[topic:it]"/>
                </g:each>

                %{--<div class="panel-body">
                    <div class="row">
                        <div class="col-md-3">
                            <img src="male-silhouette.jpg" class="img-thumbnail img-responsive" alt="Image"
                                 id="uimg" style="width:75px;height:75px">
                        </div>

                        <div class="col-md-9">
                            <div class="row">
                                <div class="col-md-6 col-xs-6">
                                    <span class="text-primary">Vishnu Aggarwal</span>
                                </div>

                                <div class="col-md-4 col-md-offset-2  col-xs-6">
                                    <span class="text-primary"><ins>Grails</ins></span>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-4">
                                    <span class="text-muted">@vishnu</span><br/>
                                    <a href="#">
                                        <ins>Unsubscribe</ins>
                                    </a>
                                </div>

                                <div class="col-md-4 col-xs-6">
                                    <span class="text-muted">Subscriptions</span><br/>
                                    <span class="text-primary">50</span>
                                </div>

                                <div class="col-md-4 col-xs-6">
                                    <span class="text-muted">Topics</span><br/>
                                    <span class="text-primary">30</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="panel-footer">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="dropdown">
                                <g:render template="/templates/seriousnessSelect"/>
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="dropdown">
                                <g:render template="/templates/visibilitySelect"/>
                            </div>
                        </div>

                        <div class="col-md-4">
                            <a href="#"><span class="glyphicon glyphicon-envelope"
                                              style="font-size:20px"></span></a>
                            <a href="#"><span class="fa fa-file-o" style="font-size:20px"></span></a>
                            <a href="#"><span class="fa fa-trash" style="font-size:20px"></span></a>
                        </div>
                    </div>
                </div>--}%
            </div>
        </div>

        <div class="row">
            <g:render template="/templates/trendingTopics" model="[]"/>
        </div>
    </div>

    <div class="col-md-7 col-md-push-1">
        <div class="row">
            <div class="row">
                <div class="panel panel-default panel-primary">
                    <div class="panel-heading">
                        Inbox
                    </div>
                    <g:each in="${readingItems}" var="readingItem">
                        <g:render template="/templates/postPanel" model="[post: readingItem]"/>
                    </g:each>
                    %{--<div class="panel-body">
                        <div class="row">
                            <div class="col-md-2">
                                <img src="#" class="img img-thumbnail img-responsive" alt="Image" id="uimg"
                                     style="width:75px;height:75px">
                            </div>

                            <div class="col-md-10">
                                <div class="row">
                                    <div class="col-md-3">
                                        <span class="text-primary">Vishnu Aggarwal</span>
                                    </div>

                                    <div class="col-md-2">
                                        <span class="text-muted">@vishnu</span>
                                    </div>

                                    <div class="col-md-2">
                                        <span class="text-muted">5 min</span>
                                    </div>

                                    <div class="col-md-2 col-md-offset-3">
                                        <span class="text-primary pull-right">Grails</span>
                                    </div>
                                </div>

                                <div class="panel text-justify">
                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
                                    incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud
                                    exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis
                                </div>

                                <div class="panel-footer">
                                    <a href="#"><span class="fa fa-facebook-square" style="font-size:20px"></span></a>
                                    <a href="#"><span class="fa fa-tumblr" style="font-size:20px"></span></a>
                                    <a href="#"><span class="fa fa-google-plus" style="font-size:20px"></span></a>
                                    <a href="#" class="pull-right">Download</a>
                                    <a href="#" class="pull-right">View Full Site</a>
                                    <a href="#" class="pull-right">Mark as Read</a>
                                    <a href="#" class="pull-right">View Post</a>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-2">
                                <img src="#" class="img img-thumbnail img-responsive" alt="Image" id="uimg"
                                     style="width:75px;height:75px">
                            </div>

                            <div class="col-md-10">
                                <div class="row">
                                    <div class="col-md-3">
                                        <span class="text-primary">Vishnu Aggarwal</span>
                                    </div>

                                    <div class="col-md-2">
                                        <span class="text-muted">@vishnu</span>
                                    </div>

                                    <div class="col-md-2">
                                        <span class="text-muted">5 min</span>
                                    </div>

                                    <div class="col-md-2 col-md-offset-3">
                                        <span class="text-primary pull-right">Grails</span>
                                    </div>
                                </div>

                                <div class="panel text-justify">
                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
                                    incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud
                                    exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis
                                </div>

                                <div class="panel-footer">
                                    <a href="#"><span class="fa fa-facebook-square" style="font-size:20px"></span></a>
                                    <a href="#"><span class="fa fa-tumblr" style="font-size:20px"></span></a>
                                    <a href="#"><span class="fa fa-google-plus" style="font-size:20px"></span></a>
                                    <a href="#" class="pull-right">Download</a>
                                    <a href="#" class="pull-right">View Full Site</a>
                                    <a href="#" class="pull-right">Mark as Read</a>
                                    <a href="#" class="pull-right">View Post</a>
                                </div>
                            </div>
                        </div>
                    </div>--}%
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>