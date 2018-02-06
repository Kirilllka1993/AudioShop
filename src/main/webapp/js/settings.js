$(document).ready(function () {
    var $changeNameForm = $('#changeNameForm');
    var $changeLoginForm = $('#changeLoginForm');
    var $changeEmailForm = $('#changeEmailForm');
    var $changePasswordForm = $('#changePasswordForm');

    var respSelector = '<div class="row response"></div>';
    var $respTag = $(respSelector);
    var successStyle = {"color": "#337ab7", "font-size": "15px"};
    var failureStyle = {"color": "#83353d", "font-size": "13px"};

    $changeEmailForm.ajaxForm(function (response) {
        $('.response').remove();
        switch (response.left) {
            case "HANDLE":
                $respTag.text(response.right.message);
                if (response.right.state == "SUCCESS") {
                    $respTag.css(successStyle);
                    $changeEmailForm.prepend($respTag);
                } else if (response.right.state == "FAILURE") {
                    var inputSelector = 'input[name=' + response.right.target + ']';
                    var $input = $(inputSelector);
                    $respTag.css(failureStyle);
                    $input.parent().after($respTag);
                    $changeEmailForm.data('submitted', false);
                }
                break;
            case "ERROR":
                $respTag.text(response.right.causeMessage + " " + response.right.toDoMessage);
                $changeEmailForm.after($respTag);
                break;
        }
    });

    $changeLoginForm.ajaxForm(function (response) {
        $('.response').remove();
        switch (response.left) {
            case "HANDLE":
                $respTag.text(response.right.message);
                if (response.right.state == "SUCCESS") {
                    $respTag.css(successStyle);
                    $changeLoginForm.prepend($respTag);
                } else if (response.right.state == "FAILURE") {
                    var inputSelector = 'input[name=' + response.right.target + ']';
                    var $input = $(inputSelector);
                    $respTag.css(failureStyle);
                    $input.parent().after($respTag);
                    $changeLoginForm.data('submitted', false);
                }
                break;
            case "ERROR":
                $respTag.text(response.right.causeMessage + " " + response.right.toDoMessage);
                $changeLoginForm.after($respTag);
                break;
        }
    });

    $changePasswordForm.ajaxForm(function (response) {
        $('.response').remove();
        switch (response.left) {
            case "HANDLE":
                $respTag.text(response.right.message);
                if (response.right.state == "SUCCESS") {
                    $respTag.css(successStyle);
                    $changePasswordForm.prepend($respTag);
                } else if (response.right.state == "FAILURE") {
                    var inputSelector = 'input[name=' + response.right.target + ']';
                    var $input = $(inputSelector);
                    $respTag.css(failureStyle);
                    $input.parent().after($respTag);
                    $changePasswordForm.data('submitted', false);
                }
                break;
            case "ERROR":
                $respTag.text(response.right.causeMessage + " " + response.right.toDoMessage);
                $changePasswordForm.after($respTag);
                break;
        }
    });

    $changeNameForm.ajaxForm(function (response) {
        $('.response').remove();
        switch (response.left) {
            case "OK":
                $respTag.text(response.right);
                $respTag.css(successStyle);
                $changeNameForm.prepend($respTag);
                break;
            case "ERROR":
                $respTag.text(response.right.causeMessage + " " + response.right.toDoMessage);
                $changeNameForm.after($respTag);
                break;
        }
    });
});
