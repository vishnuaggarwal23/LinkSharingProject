<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="applicationLayout">
    <title>Post</title>
    <asset:stylesheet src="jquery.rateyo.min.css"/>
</head>

<body>
<div class="row">
    <div class="col-md-7">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-3">
                        <ls:userImage userId="${post.userID}" class="img img-responsive img-thumbnail" height="75px"
                                      width="75px"/>
                    </div>

                    <div class="col-md-9">
                        <div class="row">
                            <div class="col-md-6">
                                <span class="text-primary">${post.userFirstName} ${post.userLastName}</span>
                            </div>

                            <div class="col-md-4 col-md-offset-1 topicName">
                                <g:link controller="topic" action="show" params="[id: post.topicID]" title="${post.topicName}" data-toggle="tooltip" data-placement="right">
                                    <span class="text-primary" style="width:inherit;"><ins>${post.topicName}</ins></span>
                                </g:link>

                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <span class="text-muted">@${post.userName}</span>
                            </div>

                            <div class="col-md-4 col-md-offset-1">
                                <span class="text-muted"><g:formatDate format="dd-MMM-yyyy"
                                                                       date="${post.postDate}"/></span>
                                <span class="text-muted"><g:formatDate format="hh:mm"
                                                                       date="${post.postDate}"/></span>
                            </div>
                        </div>
                        <g:if test="${session.user}">
                            <div class="row">
                                <div class="col-md-10 col-md-offset-7">
                                    <div id="rateYo"></div>
                                    <input type="hidden" id="default-hidden-resource-rating"
                                           value="${post?.resourceRating}"/>
                                    <input type="hidden" id="hidden-user-id" value="${session.user?.id}"/>
                                    <input type="hidden" id="hidden-resource-id" value="${post?.resourceID}"/>
                                    %{--<g:form controller="resourceRating" action="save" params="[id: post.resourceID]">
                                        <g:select name="score" from="${[1, 2, 3, 4, 5]}" optionKey="${it}"
                                                  value="${post?.resourceRating}"/>
                                        <g:submitButton name="saveResourceScoreBtn" class="btn btn-default btn-primary"
                                                        value="Save" type="submit"/>
                                    </g:form>--}%
                                </div>
                            </div>
                        </g:if>
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
                        <ls:canDeleteResource resourceID="${post.resourceID}"/>
                    </div>

                    <div class="col-md-2 col-xs-6">
                        <ls:editResourceDetails resourceId="${post.resourceID}"/>
                        <g:render template="edit" model="[id: post.resourceID, description: post.description]"/>
                    </div>

                    <div class="col-md-2 col-xs-6">
                    </div>

                    <div class="col-md-3 col-xs-6">
                        <ls:resourceType resourceID="${post.resourceID}" url="${post.url}" filePath="${post.filePath}"/>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="col-md-5">
        <g:render template="/templates/trendingTopics" model="[trendingTopics: trendingTopics]"/>
    </div>
</div>
<asset:javascript src="jquery.rateyo.min.js"/>
<asset:javascript src="resourceRating.js"/>
</body>
</html>
