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
                <div style="height: 400px;overflow-y: auto">
                    <g:each in="${recentPosts}" var="recentPost">
                        <g:render template="/templates/postPanel" model="[post: recentPost]"/>
                    </g:each>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="panel panel-default panel-primary">
                <div class="panel-heading">
                    Top Posts
                </div>
                <div style="height: 400px;overflow-y: auto">
                    <g:each in="${topPosts}" var="topPost">
                        <g:render template="/templates/postPanel" model="[post: topPost]"/>
                    </g:each>
                </div>

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