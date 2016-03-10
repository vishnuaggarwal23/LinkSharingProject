%{--
<div class="panel-body">
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
    </div>
</div>--}%

<div class="panel-body">
    <div class="col-md-2">
        <img src="${post.userPhoto}" class="img img-thumbnail img-responsive" alt="Image"
             id="uimg"
             style="width:75px;height:75px">
    </div>

    <div class="col-md-10">
        <div class="row">
            <div class="col-md-3">
                <span class="text-primary">${post.userFirstName} ${post.userLastName}</span>
            </div>

            <div class="col-md-2">
                <span class="text-muted">@${post.userName}</span>
            </div>

            <div class="col-md-2">
                <span class="text-muted">${post.postDate}</span>
            </div>

            <div class="col-md-2 col-md-offset-3">
                <span class="text-primary pull-right"><g:link name="topicClickLnk" controller="topic" action="show"
                                                              params="[id:post.topicID]">${post.topicName}</g:link></span>
            </div>
        </div>

        <div class="panel text-justify">
            ${post.description}

        </div>
    </div>
</div>
<div class="panel-footer">
    <a href="#"><span class="fa fa-facebook-square" style="font-size:20px"></span></a>
    <a href="#"><span class="fa fa-tumblr" style="font-size:20px"></span></a>
    <a href="#"><span class="fa fa-google-plus" style="font-size:20px"></span></a>
    %{--<a href="#" class="pull-right">Download</a>--}%
    %{--<a href="#" class="pull-right">View Full Site</a>--}%
    %{--<ls:showingMark>
        --}%%{--<a href="#" class="pull-right">Mark as Read</a>--}%%{--
    </ls:showingMark>--}%
    <ls:resourceType resourceID="${post.resourceID}" url="${post.url}" filePath="${post.filePath}"/>
    <ls:markRead isRead="${post.isRead}" id="${post.resourceID}"/>
    %{--<a href="#" class="pull-right">View Post</a>--}%
    <g:link controller="resource" action="show" class="pull-right" params="[id:post.resourceID]">View Post</g:link>
</div>