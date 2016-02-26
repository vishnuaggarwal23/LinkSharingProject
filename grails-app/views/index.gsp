<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="applicationLayout">
    <title></title>
</head>

<body>
<div class="row">
    <div class="col-md-7">
        <div class="row">
            <div class="panel panel-default panel-primary">
                <div class="panel-heading">
                    Recent Shares
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
                                <a href="#" class="pull-right">View Post<span
                                        class="glyphicon glyphicon-arrow-up"></span></a>
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
                                <a href="#" class="pull-right">View Post<span
                                        class="glyphicon glyphicon-arrow-up"></span></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="panel panel-default panel-primary">
                <div class="panel-heading">
                    Top Posts
                    <div class="pull-right">
                        <div class="dropdown pull right">
                            <button class="dropdown-toggle btn-responsive btn-primary" data-toggle="dropdown">Today
                                <span class="caret"></span></button>
                            <ul class="dropdown-menu">
                                <li><a href="#">Today</a></li>
                                <li><a href="#">1 Week</a></li>
                                <li><a href="#">1 Month</a></li>
                                <li><a href="#">1 Year</a></li>
                            </ul>
                        </div>
                    </div>
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
                                <a href="#" class="pull-right">View Post<span
                                        class="glyphicon glyphicon-arrow-up"></span></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="col-md-4 col-md-offset-1">
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