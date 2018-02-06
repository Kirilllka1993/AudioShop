$(document).ready(function () {
    var $commentForm = $('#commentForm');

    var respSelector = '<div class="row response"></div>';
    var $respTag = $(respSelector);
    var successStyle = {"color": "#337ab7", "font-size": "15px"};
    var failureStyle = {"color": "#83353d", "font-size": "13px"};

    $commentForm.ajaxForm(function (response) {
        $('.response').remove();
        switch (response.left) {
            case "HANDLE":
                break;
            case "ERROR":
                $respTag.css(failureStyle);
                $respTag.text(response.right.causeMessage + " " + response.right.toDoMessage);
                $commentForm.after($respTag);
                break;
        }
    });
});
