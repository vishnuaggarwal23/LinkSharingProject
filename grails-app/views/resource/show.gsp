<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="applicationLayout">
    <title></title>
</head>

<body>
<div class="row">
    <div class="col-md-7">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-3">
                        <img src="${post.userPhoto}" class="img-thumbnail img-responsive" alt="Image"
                             id="uimg" style="width:75px;height:75px">
                    </div>

                    <div class="col-md-9">
                        <div class="row">
                            <div class="col-md-6">
                                <span class="text-primary">${post.userFirstName} ${post.userLastName}</span>
                            </div>

                            <div class="col-md-4 col-md-offset-1">
                                <span class="text-primary"><ins>${post.topicName}</ins></span>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <span class="text-muted">@${post.userName}</span>
                            </div>

                            <div class="col-md-4 col-md-offset-1">
                                <span class="text-muted">9:21 PM 9 Feb 2016</span>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-10 col-md-offset-7">
                                <g:form controller="resourceRating" action="save" params="[id: post.resourceID]">
                                    <g:select name="score" from="${[1, 2, 3, 4, 5]}" optionKey="${it}"
                                              optionValue="${it}" value="${post.resourceRating}"/>
                                    <g:submitButton name="saveResourceScoreBtn" formaction="/resourceRating/save"
                                                    value="Save" type="submit"/>
                                </g:form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="panel-body">
                <span class="text-justify">${post.description}</span>
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
                        %{--<a href="#">
                            <ins>Delete</ins>
                        </a>--}%
                        <ls:canDeleteResource resourceID="${post.resourceID}"/>
                    </div>

                    <div class="col-md-2 col-xs-6">
                        <a href="#">
                            <ins>Edit</ins>
                        </a>
                    </div>

                    <div class="col-md-2 col-xs-6">
                        %{-- <a href="#">
                             <ins>Download</ins>
                         </a>--}%
                    </div>

                    <div class="col-md-3 col-xs-6">
                        %{--<a href="#">
                            <ins>View Full Site</ins>
                        </a>--}%
                        <ls:resourceType resourceID="${post.resourceID}" url="${post.url}" filePath="${post.filePath}"/>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="col-md-5">
        %{--<g:each in="${trendingTopics}" var="trendingTopic">--}%
        <g:render template="/templates/trendingTopics" model="[]"/>
        %{--</g:each>--}%
    </div>
</div>
</body>
</html>
