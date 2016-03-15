/*
 if (typeof jQuery !== 'undefined') {
 (function($) {
 $(document).ajaxStart(function(){
 $('#spinner').fadeIn();
 }).ajaxStop(function(){
 $('#spinner').fadeOut();
 });
 })(jQuery);
 }
 */

$(document).ready(function () {

    /*JSON Response*/

    function success(jsonObject) {
        if (jsonObject && jsonObject.message) {
            $('.jsonObjectResponse').css({"display": "block"});
            $('.jsonObjectResponse').text(jsonObject.message);
            $('.jsonObjectResponse').addClass('alert alert-success alert-dismissable');
        }
    }

    function error(jsonObject) {
        if (jsonObject && jsonObject.error) {
            $('.jsonObjectResponse').css({"display": "block"});
            $('.jsonObjectResponse').text(jsonObject.error);
            $('.jsonObjectResponse').addClass('alert alert-danger alert-dismissable');
        }
    }

    function ajaxResponse(jsonObject) {

        success(jsonObject);
        error(jsonObject);
    }


    /*AJAX- Toggle ReadingItem isRead*/

    $('.toggleIsRead').on('click', function (event) {
        event.preventDefault();
        $.ajax({
            type: 'POST',
            context: this,
            url: "/readingItem/changeIsRead",
            data: {id: $(this).attr('resourceId'), isRead: $(this).attr('isRead')},
            success: ajaxResponse,
            complete: location.reload()
        });
    });

    /*AJAX- Toggle Subscription Seriousness*/

    $('.seriousness').change(function () {
        $.ajax({
            url: "/subscription/update",
            data: {id: $(this).attr('topicId'), serious: $(this).val()},
            success: ajaxResponse
        });
    });

    /*AJAX- Toggle Topic Visibility*/

    $('.visibility').change(function () {
        $.ajax({
            url: "/topic/save",
            data: {
                topicId: $(this).attr('topicId'),
                topicName: $(this).attr('topicName'),
                visibility: $(this).val()
            },
            success: ajaxResponse
        });
    });

    /*AJAX- Create Topic*/

    $('#createTopicBtn').click(function () {
        $.ajax({
            url: "/topic/save",
            data: {
                topicName: $('#topicName').val(),
                visibility: $('#visibility').val()
            },
            success: ajaxResponse,
            complete: location.reload()
        });
    });


    /*AJAX- Edit Topic Name*/

    editTopicName = function (topicId) {
        $.ajax({
            url: "/topic/save",
            data: {
                topicId: $('#topicId_' + topicId).val(),
                topicName: $('#topicName_' + topicId).val(),
                newName: $('#newName_' + topicId).val()
            },
            success: ajaxResponse
        });
    };

    openTopicEdit = function (topicId) {
        $('.topicShowPanel_' + topicId).css({"visibility": "hidden"});
        $('#topicEditPanel_' + topicId).css({"visibility": "visible"});
    };

    closeEditTopic = function (topicId) {
        $('.topicShowPanel_' + topicId).css({"visibility": "visible"});
        $('#topicEditPanel_' + topicId).css({"visibility": "hidden"});
    };

    /*AJAX- Topic Post Search*/

    $('.topicPostSearchCancelBtn').on('click', function () {
        $('#topicPostSearchBox').val("");
        location.reload()
    });

    $('.topicPostSearchBtn').on('click', function () {
        $.ajax({
            url: "/resource/search",
            data: {
                q: $('#topicPostSearchBox').val(),
                topicID: $('.topicPostSearchHiddenTopicID').val()
            },
            success: function (searchPosts) {
                $('.postPanelBody').html(searchPosts)
            }
        })
    });


    /*Global Resource Search*/

    $('.globalSearchCancelBtn').on('click', function () {
        $('#globalSearchBox').val("");
        location.href = "/";
    });

    $('.globalSearchBtn').on('click', function () {
        $.ajax({
            url: "/resource/save",
            data: {
                q: $('.globalSearchBox').val(),
                global: $('#global').val(),
                visibility: $('#visibility').val()
            }
        })
    });


    /*jQuery- Field Validators*/

    jQuery.validator.addMethod("passwordCheck", function (value, element) {
        var result = false;
        var password = $('#password').val();
        if (password === value) {
            result = true;
        }
        return result;
    }, "Password Fields Do Not Match");

    $(function () {
        $('#upload').validate({
            rules: {
                'file': {
                    required: true
                },
                'description': {
                    required: true
                },
                'topic': {
                    required: true
                }
            },
            messages: {
                'file': {
                    required: "File Selection is Required"
                },
                'description': {
                    required: "Resource Description is Required"
                },
                'topic': {
                    required: "Topic is Required"
                }
            }
        });

        $('#resourceDescriptionEditForm').validate({
            rules: {
                'description': {
                    rules: {
                        required: true
                    },
                    messages: {
                        required: "Resource Description is Required"
                    }
                }
            }
        });

        $('#linkResourceSave').validate({
            rules: {
                'url': {
                    required: true
                },
                'description': {
                    required: true
                },
                'topic': {
                    required: true
                }
            },
            messages: {
                'file': {
                    required: "URL is Required"
                },
                'description': {
                    required: "Resource Description is Required"
                },
                'topic': {
                    required: "Topic is Required"
                }
            }
        });

        $('#topicCreate').validate({
            rules: {
                'topicName': {
                    required: true,
                    remote: {
                        url: "/topic/validateUniqueTopicPerUser",
                        type: 'post',
                        data: {
                            topicFName: function () {
                                return $('#topicName').val()
                            }
                        }
                    }
                },
                'visibility': {
                    required: true
                }
            },
            messages: {
                'topicName': {
                    required: "Topic Name Field is Required",
                    remote: "Topic already Exists"
                },
                'visibility': {
                    required: "Visibility is Required"
                }
            }
        });

        $('#inviteForm').validate({
            rules: {
                'email': {
                    required: true,
                    remote: {
                        url: "/login/validateEmail",
                        type: "post",
                        data: {
                            email: function () {
                                return !$('#email').val()
                            }
                        }
                    }
                }
            },
            messages: {
                'email': {
                    required: 'Email is Required',
                    remote: 'Entered E-mail is not registered'
                }
            }
        });

        $('#forgotPassword').validate({
            rules: {
                'email': {
                    required: true,
                    remote: {
                        url: "/login/validateEmail",
                        type: "post",
                        data: {
                            email: function () {
                                return !$('#email').val()
                            }
                        }
                    }
                }
            },
            messages: {
                'email': {
                    required: 'Email is Required',
                    remote: 'Entered E-mail is not registered'
                }
            }
        });

        $('#updatePassword').validate({
            rules: {
                'oldPassword': {
                    required: true,
                    minlength: 5,
                    remote: {
                        url: '/user/validatePassword',
                        type: 'post',
                        data: {
                            oldPassword: function () {
                                return $('#oldPassword').val()
                            }
                        }
                    }
                },
                'password': {
                    required: true,
                    minlength: 5
                },
                'confirmPassword': {
                    required: true,
                    minlength: 5,
                    passwordCheck: true
                }
            },
            messages: {
                'oldPassword': {
                    required: "Old Password is Required",
                    remote: "Old Password is Incorrect",
                    minlength: "Password should be of Minimum Length 5"
                },
                'password': {
                    required: "Password is Required",
                    minlength: "Password should be of Minimum Length 5"
                },
                'confirmPassword': {
                    required: "Confirm Password is Required",
                    minlength: "Confirm Password should be of Minimum Length 5"
                }
            }
        });

        $('#loginForm').validate({
            rules: {
                'loginUserName': {
                    required: true
                },
                'loginPassword': {
                    required: true,
                    minlength: 5
                }
            },
            messages: {
                'loginUserName': {
                    required: "Username is Required"
                },
                'loginPassword': {
                    required: "Password is Required",
                    minlength: "Password should be minimum 5 characters"
                }
            }
        });

        $('#registration').validate({
            rules: {
                'firstName': {
                    required: true
                },
                'lastName': {
                    required: true
                },
                'password': {
                    required: true,
                    minlength: 5
                },
                'confirmPassword': {
                    required: true,
                    minlength: 5,
                    passwordCheck: true
                },
                'email': {
                    required: true,
                    remote: {
                        url: "/login/validateEmail",
                        type: "post",
                        data: {
                            email: function () {
                                return $('#email').val()
                            }
                        }
                    }
                },
                'userName': {
                    required: true,
                    remote: {
                        url: "/login/validateUserName",
                        type: "post",
                        data: {
                            userName: function () {
                                return $('#userName').val()
                            }
                        }
                    }
                }
            },
            messages: {
                'firstName': {
                    required: "First Name Field Cannot Be Blank"
                },
                'lastName': {
                    required: "First Name Field Cannot Be Blank"
                },
                'password': {
                    required: "Password Field Cannot Be Blank",
                    minlength: "Minimum Password Size Is Of 5 Characters"
                },
                'confirmPassword': {
                    required: "Confirm Password Field Cannot Be Blank",
                    minlength: "Minimum Password Size Is Of 5 Characters"
                },
                'email': {
                    required: "E-Mail Address Field Cannot Be Blank",
                    remote: "Email address entered is already used"
                },
                'userName': {
                    required: "User Name Field Cannot Be Blank",
                    remote: "Username entered is already used"
                }
            }
        });

    });
});
