<div class="panel-body">
    <div class="row">
        <div class="col-md-3">
            <ls:userImage userId="${topic.createdBy.id}" class="img img-responsive img-thumbnail" height="75px"
                          width="75px"/>
        </div>

        <div class="col-md-9">
            <div class="row">
                <div class="col-md-6 col-xs-6">
                    <span class="text-primary">${topic.createdBy.getName()}</span>
                </div>

                <div class="col-md-4 col-md-offset-2  col-xs-6">
                    <span class="text-primary">
                        <ins><g:link name="topicClickLnk" controller="topic" action="show"
                                     params="[id:topic.id]">${topic.name}</g:link></ins></span>
                </div>
            </div>

            <div class="row">
                <div class="col-md-4">
                    <span class="text-muted">@${topic.createdBy}</span><br/>
                    <a href="#">
                        <ins><ls:showSubscribe topicID="${topic.id}"/></ins>
                    </a>
                </div>

                <div class="col-md-4 col-xs-6">
                    <span class="text-muted">Subscriptions</span><br/>
                    <span class="text-primary"><ls:subscriptionCount topicId="${topic.id}"/> </span>
                </div>

                <div class="col-md-4 col-xs-6">
                    <span class="text-muted">Posts</span><br/>
                    <span class="text-primary"><ls:resourceCount topicId="${topic.id}"/> </span>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="panel-footer">
    <div class="row">
        <div class="col-md-4">
            <div class="dropdown">
                <ls:editTopicSeriousness topicId="${topic.id}"/>
            </div>
        </div>

        <div class="col-md-4">
            <div class="dropdown">
                <ls:editTopicVisibility topicId="${topic.id}"/>
            </div>
        </div>

        <div class="col-md-4">
            <ls:sendTopicInvite topicId="${topic.id}"/>
            <ls:editTopicDetails topicId="${topic.id}"/>
            <ls:canDeleteTopic topicId="${topic.id}"/>
        </div>
    </div>
</div>