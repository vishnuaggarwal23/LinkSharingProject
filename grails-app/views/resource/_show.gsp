%{--<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="applicationLayout">
    <title></title>
</head>

<body>--}%
<div class="row">
    <div class="col-md-7">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-3">
                        <img src="male-silhouette.jpg" class="img-thumbnail img-responsive" alt="Image"
                             id="uimg" style="width:75px;height:75px">
                    </div>

                    <div class="col-md-9">
                        <div class="row">
                            <div class="col-md-6">
                                <span class="text-primary">Vishnu Aggarwal</span>
                            </div>

                            <div class="col-md-4 col-md-offset-1">
                                <span class="text-primary"><ins>Grails</ins></span>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <span class="text-muted">@vishnu</span>
                            </div>

                            <div class="col-md-4 col-md-offset-1">
                                <span class="text-muted">9:21 PM 9 Feb 2016</span>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-10 col-md-offset-7">
                                <g:select name="score" from="${[1, 2, 3, 4, 5]}" optionKey="${it}"
                                          optionValue="${it}"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="panel-body">
                <span class="text-justify">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis</span>
            </div>

            <div class="panel-footer">
                <div class="row">
                    <div class="col-md-3 col-xs-12">
                        <div class="row">
                            <div class="col-md-4 col-xs-4">
                                <a href="#"><span class="fa fa-facebook-official"
                                                  style="font-size: large"></span></a>
                            </div>

                            <div class="col-md-4 col-xs-4">
                                <a href="#"><span class="fa fa-twitter-square" style="font-size: large"></span></a>
                            </div>

                            <div class="col-md-4 col-xs-4">
                                <a href="#"><span class="fa fa-google-plus" style="font-size: large"></span></a>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-2 col-xs-6">
                        <a href="#">
                            <ins>Delete</ins>
                        </a>
                    </div>

                    <div class="col-md-2 col-xs-6">
                        <a href="#">
                            <ins>Edit</ins>
                        </a>
                    </div>

                    <div class="col-md-2 col-xs-6">
                        <a href="#">
                            <ins>Download</ins>
                        </a>
                    </div>

                    <div class="col-md-3 col-xs-6">
                        <a href="#">
                            <ins>View Full Site</ins>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="col-md-5">
        <g:render template="/templates/trendingTopics"/>
    </div>
</div>
%{--
</body>
</html>--}%
