<%@ page import="enums.Visibility" %>
<div class="modal fade" id="createtopicModal" tabindex="-1" role="dialog" aria-labelledby="createtopicModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="createtopicModalLabel">Create Topic</h4>
            </div>

            <div class="modal-body">
                <g:form class="form-horizontal" name="topicCreate" id="topicCreate" controller="topic" action="save">
                    <div class="form-group row">
                        <label for="topicName" class="col-sm-4 form-control-label">Name *</label>

                        <div class="col-sm-8">
                            <g:textField name="topicName" id="topicName" placeholder="Topic Name" class="form-control"/>
                            %{--<input type="text" class="form-control" id="topicName">--}%
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="visibility" class="col-sm-4 form-control-label">Visibility *</label>

                        <div class="col-sm-8">
                            <div class="dropdown">
                                <g:select class="btn dropdown-toggle" data-toggle="dropdown" name="visibility"
                                          id="visibility" from="${enums.Visibility.values()}"/>
                            </div>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-4 form-control-label">
                            <button type="button" class="btn btn-default btn-block" data-dismiss="modal">Close</button>
                        </div>

                        <div class="col-sm-8">
                            <g:submitButton type="submit" name="createTopic" value="Create Topic" action="save"
                                            class="btn btn-block btn-primary"/>
                            %{--<button type="button" class="btn btn-primary btn-block">Save</button>--}%
                        </div>
                    </div>
                </g:form>
            %{--<form class="form-horizontal">
                <div class="form-group row">
                    <label for="topicName" class="col-sm-4 form-control-label">Name *</label>

                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="topicName">
                    </div>
                </div>

                <div class="form-group row">
                    <label for="visibility" class="col-sm-4 form-control-label">Visibility *</label>

                    <div class="col-sm-8">
                        <div class="dropdown">
                            <button class="btn dropdown-toggle" id="visibility" type="button"
                                    data-toggle="dropdown">
                                <span class="glyphicon glyphicon-user"></span>Public
                                <span class="caret"></span></button>
                            <ul class="dropdown-menu">
                                <li><a href="#">.....</a></li>
                                <li><a href="#">.....</a></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="form-group row">
                    <div class="col-sm-4 form-control-label">
                        <button type="button" class="btn btn-default btn-block" data-dismiss="modal">Close</button>
                    </div>

                    <div class="col-sm-8">
                        <button type="button" class="btn btn-primary btn-block">Save</button>
                    </div>
                </div>
            </form>--}%
            </div>
        </div>
    </div>
</div>