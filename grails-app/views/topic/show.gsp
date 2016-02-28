<!DOCTYPE html>
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
                <div class="panel-heading">
                    Topics: ${topicDetails.name}
                </div>

                <div class="panel-body">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-3">
                                <img src="male-silhouette.jpg" class="img-thumbnail img-responsive" alt="Image"
                                     id="uimg" style="width:75px;height:75px">
                            </div>

                            <div class="col-md-9">
                                <div class="row">

                                    <div class="col-md-6 col-xs-6">
                                        <span class="text-primary"><ins>${topicDetails.name}</ins></span>
                                        <span class="text-muted"><ins>(${topicDetails.visibility})</ins></span>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-4">
                                        <span class="text-muted">@${topicDetails.createdBy}</span><br/>
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
                            <div class="col-md-4 col-md-push-4">
                                <div class="dropdown">
                                    <g:select name="seriousness" from="${enums.Seriousness.values()}"
                                              class="btn btn-xs btn-default dropdown-toggle"/>
                                </div>
                            </div>

                            <div class="col-md-8 col-md-push-5">
                                <a href="#"><span class="glyphicon glyphicon-envelope"
                                                  style="font-size:20px"></span></a>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="panel panel-default panel-primary">
                <div class="panel-heading">
                    Users: ${topicDetails.name}
                </div>
                <g:each in="${subscribedUsers}" var="subscribedUser">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-3">
                                <img src="male-silhouette.jpg" class="img img-thumbnail img-responsive" alt="Image"
                                     id="uimg" style="width:75px;height:75px">
                            </div>

                            <div class="col-md-9">
                                <span class="text-primary">${subscribedUser.firstName} ${subscribedUser.lastName}</span><br/>
                                <span class="text-muted">@${subscribedUser.name}</span>

                                <div class="row">
                                    <div class="col-md-4 col-xs-6">
                                        <span class="text-muted">Subscriptions</span><br/>
                                        <span class="text-primary">50</span>
                                    </div>

                                    <div class="col-md-4 col-md-offset-2 col-xs-6">
                                        <span class="text-muted">Posts</span><br/>
                                        <span class="text-primary">30</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </g:each>

            </div>
        </div>
    </div>

    <div class="col-md-7 col-md-offset-1">
        <div class="row">
            <div class="panel panel-default panel-primary">
                <div class="panel-heading">
                    Posts: ${topicDetails.name}
                </div>

                <div class="panel-body">
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
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>