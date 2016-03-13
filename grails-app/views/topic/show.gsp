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
                <g:render template="/templates/topicPanel" model="[topic: topicDetails]"/>
            </div>
        </div>

        <div class="row">
            <div class="panel panel-default panel-primary">
                <div class="panel-heading">
                    Users: ${topicDetails.name}
                </div>
                <g:each in="${subscribedUsers}" var="subscribedUser">
                    <g:render template="/templates/userPanel" model="[userDetails: subscribedUser]"/>
                </g:each>

            </div>
        </div>
    </div>

    <div class="col-md-7 col-md-offset-1">
        <div class="row">
            <div class="panel panel-default panel-primary">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-md-3">
                            Posts: ${topicDetails.name}
                        </div>

                        <div class="col-md-6 col-md-offset-3">
                            <g:form name="topicPostSearchForm" class="form-inline">
                                <div class="form-group">
                                    <label class="sr-only" for="topicPostSearchBox">Search</label>

                                    <div class="input-group">
                                        <div class="input-group-addon topicPostSearchBtn"><span
                                                class="glyphicon glyphicon-search topicPostSearchBtn"
                                                style="font-size: large"></span></div>
                                        <g:textField name="topicPostSearchBox" type="text" class="form-control"
                                                     id="topicPostSearchBox" placeholder="Search"/>

                                        <div class="input-group-addon topicPostSearchCancelBtn"><span
                                                class="glyphicon glyphicon-remove topicPostSearchCancelBtn"
                                                style="font-size: large"></span></div>
                                    </div>
                                </div>
                            </g:form>
                        </div>
                    </div>
                    <g:hiddenField name="topicId" id="hiddenTopicID" class="topicPostSearchHiddenTopicID" value="${topicDetails.id}"/>
                </div>
                <div class="postPanelBody">
                    <g:each in="${topicPosts}" var="topicPost">
                        <g:render template="/templates/postPanel" model="[post: topicPost]"/>
                    </g:each>

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>