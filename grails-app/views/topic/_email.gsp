<div class="modal fade" id="sendinviteModal" tabindex="-1" role="dialog" aria-labelledby="sendinviteModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="sendinviteModalLabel">Send Invitation</h4>
            </div>

            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group row">
                        <label for="email" class="col-sm-4 form-control-label">Email *</label>

                        <div class="col-sm-8">
                            <input type="email" class="form-control" id="email">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="topic" class="col-sm-4 form-control-label">Topic *</label>

                        <div class="col-sm-8">
                            <div class="dropdown">
                                <button class="btn dropdown-toggle" id="topic" type="button" data-toggle="dropdown">
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
                            <button type="button" class="btn btn-primary btn-block">Invite</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>