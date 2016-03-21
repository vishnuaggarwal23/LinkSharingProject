<div class="panel-body">
    <div class="row">
        <div class="col-md-3">
            <g:link controller="user" action="profile" params="[id: topic.createdBy.id]">
                <ls:userImage userId="${topic.createdBy.id}" class="img img-responsive img-thumbnail" height="75px"
                              width="75px"/>
            </g:link>
        </div>

        <div class="col-md-9">
            <div class="row">
                <div class="col-md-6 col-xs-6 topicShowPanel_${topic.id}">
                    <span class="text-primary">${topic.createdBy.getName()}</span>
                </div>

                <div class="col-md-4 col-md-offset-2 col-xs-6 topicShowPanel_${topic.id} topicName">
                    <span class="text-primary" style="width:inherit">
                        <ins><g:link name="topicClickLnk" controller="topic" action="show"
                                     params="[id: topic.id]" data-toggle="tooltip" data-placement="right"
                                     title="${topic.name}">${topic.name}</g:link></ins></span>
                </div>

                <div class="col-md-12" id="topicEditPanel_${topic.id}" style="visibility: hidden">
                    <g:form class="form-inline" name="editTopic">
                        <div class="form-group row">
                            <g:hiddenField name="topicId" id="topicId_${topic.id}" value="${topic.id}"/>
                            <g:hiddenField name="topicName" id="topicName_${topic.id}" value="${topic.name}"/>
                            <g:hiddenField name="createdBy" id="createdBy_${topic.id}" value="${topic.createdBy}"/>
                            <g:textField name="newName" class="form-control" size="13" type="text"
                                         id="newName_${topic.id}"
                                         value="${topic.name}" required="required"/>
                            <g:submitButton name="editTopicNameBtn_${topic.id}" type="submit" value="Save"
                                            class="btn btn-default btn-primary form-control editTopicNameBtn"
                                            onclick="editTopicName(${topic.id})"/>
                            <button type="button" class="btn btn-default" name="cancel_${topic.id}"
                                    id="cancel_${topic.id}"
                                    value="Cancel" onclick="closeEditTopic(${topic.id})">Cancel</button>
                        </div>
                    </g:form>
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
                    <span class="text-primary"><ls:subscriptionCount topicId="${topic.id}"/></span>
                </div>

                <div class="col-md-4 col-xs-6">
                    <span class="text-muted">Posts</span><br/>
                    <span class="text-primary"><ls:resourceCount topicId="${topic.id}"/></span>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="panel-footer">
    <div class="row">
        <div class="col-md-4">
            <div class="dropdown">
                <ls:canUpdateTopic>
                %{--<ls:editTopicSeriousness topicId="${topic.id}"/>--}%
                    <ls:showSeriousness topicId="${topic.id}"/>
                </ls:canUpdateTopic>
            </div>
        </div>

        <div class="col-md-4">
            <div class="dropdown">
                <ls:showVisibility topicId="${topic.id}"/>
            </div>
        </div>

        <div class="col-md-4">

            <ls:sendTopicInvite topicId="${topic.id}"/>
            <ls:canUpdateTopic>
                <ls:editTopicDetails topicId="${topic.id}"/>
                <ls:canDeleteTopic topicId="${topic.id}"/>
            </ls:canUpdateTopic>
        </div>
    </div>
</div>