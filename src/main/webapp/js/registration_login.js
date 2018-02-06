$(document).ready(function () {
    jQuery.fn.preventDoubleSubmission = function () {
        $(this).on('submit', function (e) {
            var $form = $(this);
            if ($form.data('submitted')) {
                e.preventDefault();
            } else {
                $form.data('submitted', true);
            }
        });
        return this;
    };

    var registerForm = '#registration';
    var logInForm = '#log-in';
    var $logInForm = $(logInForm);
    var $registerForm = $(registerForm);

    $registerForm.preventDoubleSubmission();

    var respSelector = '<div class="row response"></div>';
    var $respTag = $(respSelector);
    var successStyle = {"color": "#337ab7", "font-size": "15px"};
    var failureStyle = {"color": "#83353d", "font-size": "13px"};

    $registerForm.ajaxForm(function (response) {
        var $inputs = $('#registration input[type=text], #registration input[type=password]');
        var $lastInput = $logInForm.find('input[type=password]');
        $('.response').remove();
        switch (response.left) {
            case "HANDLE":
                $respTag.text(response.right.message);
                if (response.right.state == "SUCCESS") {
                    $respTag.css(successStyle);
                    $registerForm.prepend($respTag);
                    $inputs.val('');
                } else if (response.right.state == "FAILURE") {
                    var inputSelector = 'input[name=' + response.right.target + ']';
                    var $input = $(inputSelector);
                    $respTag.css(failureStyle);
                    $input.parent().after($respTag);
                    $registerForm.data('submitted', false);
                }
                break;
            case "LOCATION_GO":
                window.location = response.right;
                break;
            case "LOCATION_REDIRECT":
                window.location.replace(response.right);
                break;
            case "ERROR":
                $respTag.text(response.right.causeMessage + " " + response.right.toDoMessage);
                $lastInput = $registerForm.find("input[type=password]");
                $lastInput.parent().after($respTag);
                $registerForm.data('submitted', false);
                $inputs.val('');
                break;
        }
    });

    $logInForm.ajaxForm(function (response) {
        var $inputs = $("#log-in input[type=text], #log-in input[type=password]");
        var $lastInput = $logInForm.find("input[type=password]");
        $(".response").remove();
        switch (response.left) {
            case "HANDLE":
                $respTag.css(failureStyle);
                $respTag.text(response.right);
                $lastInput.parent().after($respTag);
                $inputs.val('');
                break;
            case "LOCATION_GO":
                window.location = response.right;
                break;
            case "LOCATION_REDIRECT":
                window.location.replace(response.right);
                break;
            case "ERROR":
                $respTag.css(failureStyle);
                $respTag.text(response.right.causeMessage + " " + response.right.toDoMessage);
                $lastInput = $logInForm.find("input[type=password]");
                $lastInput.parent().after($respTag);
                $inputs.val('');
                break;
        }
    });
});
