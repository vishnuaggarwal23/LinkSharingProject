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

    $('.seriousness').change(function () {
        $.ajax({
            url: seriousnessUrl,
            data: {id: $(this).attr('topicId'), serious: $(this).val()},
            success: success,
            error: error
        });
    });

    $('.visibility').change(function () {
        $.ajax({
            url: visibilityUrl,
            data: {topicName: $(this).attr('topicName'), visibility: $(this).val()},
            success: success,
            error: error
        });
    });
    /*
     $('#inviteModalBtn').click(function () {
     $('#sendinviteModal').modal();
     });

     $('#linkResourceModalBtn').click(function () {
     $('#sharelinkModal').modal();
     });

     $('#documentResourceModalBtn').click(function () {
     $('#sharedocModal').modal();
     });*/

    $('#createTopicBtn').click(function () {
        $.ajax({
            url: "/topic/save",
            data: {topicName: $('#topicName').val(), visibility: $('#visibility').val()},
            success: location.reload(),
            error: error
        });
    });

    jQuery.validator.addMethod("passwordCheck", function (value, element) {
        var result = false;
        var password = $('#password').val();
        if (password === value) {
            result = true;
        }
        return result;
    }, "Password Fields Do Not Match");

    $(function () {
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
