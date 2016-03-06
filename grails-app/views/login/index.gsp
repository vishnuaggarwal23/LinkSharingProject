<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="applicationLayout">
    <title></title>
</head>

<body>
<div class="row">
    <div class="col-md-6">
        <div class="row">
            <div class="panel panel-default panel-primary">
                <div class="panel-heading">
                    Recent Shares
                </div>
                <g:each in="${recentPosts}" var="recentPost">
                    <g:render template="/templates/postPanel" model="[post: recentPost]"/>
                %{-- <div class="panel-footer">
                     <a href="#"><span class="fa fa-facebook-square" style="font-size:20px"></span></a>
                     <a href="#"><span class="fa fa-tumblr" style="font-size:20px"></span></a>
                     <a href="#"><span class="fa fa-google-plus" style="font-size:20px"></span></a>
                     <a href="#" class="pull-right">View Post<span
                             class="glyphicon glyphicon-arrow-up"></span></a>
                 </div>--}%
                %{--<div class="panel-body">
                    <div class="row">
                        <div class="col-md-2">
                            <img src="${topPost.userPhoto}" class="img img-thumbnail img-responsive" alt="Image"
                                 id="uimg"
                                 style="width:75px;height:75px">
                        </div>

                        <div class="col-md-10">
                            <div class="row">
                                <div class="col-md-3">
                                    <span class="text-primary">${topPost.userFirstName} ${topPost.userLastName}</span>
                                </div>

                                <div class="col-md-2">
                                    <span class="text-muted">@${topPost.userName}</span>
                                </div>

                                <div class="col-md-2">
                                    <span class="text-muted">5 min</span>
                                </div>

                                <div class="col-md-2 col-md-offset-3">
                                    <span class="text-primary pull-right">${topPost.topicName}</span>
                                </div>
                            </div>

                            <div class="panel text-justify">
                                ${topPost.description}
                            </div>

                            <div class="panel-footer">
                                <a href="#"><span class="fa fa-facebook-square" style="font-size:20px"></span></a>
                                <a href="#"><span class="fa fa-tumblr" style="font-size:20px"></span></a>
                                <a href="#"><span class="fa fa-google-plus" style="font-size:20px"></span></a>
                                <a href="#" class="pull-right">View Post<span
                                        class="glyphicon glyphicon-arrow-up"></span></a>
                            </div>
                        </div>
                    </div>
                </div>--}%
                </g:each>
            </div>
        </div>

        <div class="row">
            <div class="panel panel-default panel-primary">
                <div class="panel-heading">
                    Top Posts
                </div>

                <g:each in="${topPosts}" var="topPost">
                    <g:render template="/templates/postPanel" model="[post: topPost]"/>
                %{--<div class="panel-footer">
                    <a href="#"><span class="fa fa-facebook-square" style="font-size:20px"></span></a>
                    <a href="#"><span class="fa fa-tumblr" style="font-size:20px"></span></a>
                    <a href="#"><span class="fa fa-google-plus" style="font-size:20px"></span></a>
                    <a href="#" class="pull-right">View Post<span
                            class="glyphicon glyphicon-arrow-up"></span></a>
                </div>--}%
                %{--<div class="panel-body">
                    <div class="row">
                        <div class="col-md-2">
                            <img src="${topPost.userPhoto}" class="img img-thumbnail img-responsive" alt="Image"
                                 id="uimg"
                                 style="width:75px;height:75px">
                        </div>

                        <div class="col-md-10">
                            <div class="row">
                                <div class="col-md-3">
                                    <span class="text-primary">${topPost.userFirstName} ${topPost.userLastName}</span>
                                </div>

                                <div class="col-md-2">
                                    <span class="text-muted">@${topPost.userName}</span>
                                </div>

                                <div class="col-md-2">
                                    <span class="text-muted">5 min</span>
                                </div>

                                <div class="col-md-2 col-md-offset-3">
                                    <span class="text-primary pull-right">${topPost.topicName}</span>
                                </div>
                            </div>

                            <div class="panel text-justify">
                                ${topPost.description}
                            </div>

                            <div class="panel-footer">
                                <a href="#"><span class="fa fa-facebook-square" style="font-size:20px"></span></a>
                                <a href="#"><span class="fa fa-tumblr" style="font-size:20px"></span></a>
                                <a href="#"><span class="fa fa-google-plus" style="font-size:20px"></span></a>
                                <a href="#" class="pull-right">View Post<span
                                        class="glyphicon glyphicon-arrow-up"></span></a>
                            </div>
                        </div>
                    </div>
                </div>--}%
                </g:each>
            </div>
        </div>
    </div>

    <div class="col-md-5 col-md-offset-1">
        <div class="row">
            <g:render template="/login/login"/>
        </div>

        <div class="row">
            <g:render template="/login/register"/>
        </div>
    </div>
</div>
</body>
</html>