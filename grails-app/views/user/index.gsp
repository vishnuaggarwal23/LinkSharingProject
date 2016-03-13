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
                <div style="overflow-y:scroll; height: 400px;">
                    <g:each in="${subscriptions}">
                        <g:render template="/templates/topicPanel" model="[topic: it]"/>
                    </g:each>
                </div>

            </div>
        </div>

        <div class="row" style="overflow-y:scroll; height:auto;max-height: 400px">
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
                    <g:paginate class="pagination" total="${totalReadingItems}" controller="user" action="index"
                                max="${searchCO.max}" offset="${searchCO.offset}"/>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>