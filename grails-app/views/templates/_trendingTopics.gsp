<div class="panel panel-default panel-primary">
    <div class="panel-heading">
        Trending Topics
    </div>
    <g:each in="${topicVOList}" var="topicVO">
        <div class="panel-body">
            <div class="row">
                <div class="col-md-3">
                    <img src="male-silhouette.jpg" class="img-thumbnail img-responsive" alt="Image"
                         id="uimg" style="width:75px;height:75px">
                </div>

                <div class="col-md-9">
                    <div class="row">
                        <div class="col-md-6 col-xs-6">
                            <span class="text-primary">${topicVO.createdBy.getName()}</span>
                        </div>

                        <div class="col-md-4 col-md-offset-2  col-xs-6">
                            <span class="text-primary"><ins>${topicVO.name}</ins></span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-4">
                            <span class="text-muted">@${topicVO.createdBy}</span><br/>
                            <a href="#">
                                <ins>Subscribe</ins>
                            </a>
                        </div>

                        <div class="col-md-4 col-xs-6">
                            <span class="text-muted">Subscriptions</span><br/>
                            <span class="text-primary">50</span>
                        </div>

                        <div class="col-md-4 col-xs-6">
                            <span class="text-muted">Posts</span><br/>
                            <span class="text-primary">${topicVO.count}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="panel-footer">
            <div class="row">
                <div class="col-md-4 col-xs-4">
                    <div class="dropdown">
                        <button class="btn btn-default btn-sm dropdown-toggle" type="button"
                                data-toggle="dropdown">Seriousness
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a href="#">....</a></li>
                            <li><a href="#">....</a></li>
                            <li><a href="#">....</a></li>
                        </ul>
                    </div>

                </div>

                <div class="col-md-4 col-xs-4">
                    <div class="dropdown">
                        <button class="btn btn-default btn-sm dropdown-toggle" type="button"
                                data-toggle="dropdown">Public
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a href="#">....</a></li>
                            <li><a href="#">....</a></li>
                            <li><a href="#">....</a></li>
                        </ul>
                    </div>
                </div>

                <div class="col-md-2 col-xs-4">
                    <a href="#">
                        <ins>Delete</ins>
                    </a>
                </div>

                <div class="col-md-2 col-xs-4">
                    <a href="#">
                        <ins>Edit</ins>
                    </a>
                </div>
            </div>
        </div>
    </g:each>
</div>