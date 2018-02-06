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
                $respTag.text(response.right.message);
                $respTag.css(failureStyle);
                $commentForm.after($respTag);
                break;
            case "OK":
                var comment = '<div class="row">' +
                    '<div class="col-sm-1">' +
                    '<img class="img-responsive avatar-nav" src="/s?command=load_image&elementId='+response.right.right.accountId+'&target=account">' +
                    '</div>' +
                    '<div class="col-sm-5">' +
                    '<div class="panel panel-default" style="background-color:transparent">' +
                    '<div class="panel-heading" style="background-color:transparent">' +
                    '<strong>'+response.right.right.login+'</strong> <span class="text-muted">'+response.right.left.dateTime+'</span>' +
                    '</div>' +
                    '<div class="panel-body">' +
                    response.right.left.text +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</div>';
                var $comment = $(comment);
                $('#comments').after($comment);
                break;
            case "ERROR":
                $respTag.css(failureStyle);
                $respTag.text(response.right.causeMessage + " " + response.right.toDoMessage);
                $commentForm.after($respTag);
                break;
        }
    });
});
