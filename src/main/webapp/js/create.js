$(document).ready(function () {
    var $addTrackForm = $('#addTrackForm');
    var $addAlbumForm = $('#addAlbumForm');
    var $addArtistForm = $('#addArtistForm');
    var $addGenreForm = $('#addGenreForm');

    var respSelector = '<div class="row response"></div>';
    var $respTag = $(respSelector);
    var successStyle = {"color": "#337ab7", "font-size": "15px"};
    var failureStyle = {"color": "#83353d", "font-size": "13px"};

    $addTrackForm.ajaxForm(function (response) {
        var $inputs = $('#addTrackForm input[type=text], #addTrackForm input[type=file]');
        var $select = $('#addTrackForm select option:selected');
        $('.response').remove();
        switch (response.left) {
            case "HANDLE":
                $respTag.text(response.right.message);
                if (response.right.state == "SUCCESS") {
                    $respTag.css(successStyle);
                    $addTrackForm.prepend($respTag);
                    $inputs.val('');
                    $select.prop('selected', false);
                } else {
                    $respTag.css(failureStyle);
                    $addTrackForm.after($respTag);
                }
                break;
            case "ERROR":
                $respTag.css(failureStyle);
                $respTag.text(response.right.causeMessage + " " + response.right.toDoMessage);
                $addTrackForm.after($respTag);
                break;
        }
    });

    $addArtistForm.ajaxForm(function (response) {
        var $inputs = $('#addArtistForm input[type=text]');
        var $select = $('#addArtistForm select option:selected');
        $('.response').remove();
        switch (response.left) {
            case "HANDLE":
                $respTag.text(response.right.message);
                if (response.right.state == "SUCCESS") {
                    $respTag.css(successStyle);
                    $addArtistForm.prepend($respTag);
                    $inputs.val('');
                    $select.prop('selected', false);
                } else if (response.right.state == "FAILURE") {
                    var inputSelector = 'input[name=' + response.right.target + ']';
                    var $input = $(inputSelector);
                    $respTag.css(failureStyle);
                    $input.parent().after($respTag);
                }
                break;
            case "LOCATION_REDIRECT":
                window.location.replace(response.right);
                break;
            case "ERROR":
                $respTag.css(failureStyle);
                $respTag.text(response.right.causeMessage + " " + response.right.toDoMessage);
                $addArtistForm.after($respTag);
                break;
        }
    });

    $addAlbumForm.ajaxForm(function (response) {
        var $inputs = $('#addAlbumForm input[type=text]');
        var $select = $('#addAlbumForm select option:selected');
        $('.response').remove();
        switch (response.left) {
            case "HANDLE":
                $respTag.text(response.right.message);
                if (response.right.state == "SUCCESS") {
                    $respTag.css(successStyle);
                    $addAlbumForm.prepend($respTag);
                    $inputs.val('');
                    $select.removeAttr('selected');
                } else if (response.right.state == "FAILURE") {
                    var inputSelector = 'input[name=' + response.right.target + ']';
                    var $input = $(inputSelector);
                    $respTag.css(failureStyle);
                    $input.parent().after($respTag);
                }
                break;
            case "LOCATION_REDIRECT":
                window.location.replace(response.right);
                break;
            case "ERROR":
                $respTag.css(failureStyle);
                $respTag.text(response.right.causeMessage + " " + response.right.toDoMessage);
                $addAlbumForm.after($respTag);
                break;
        }
    });

    $addGenreForm.ajaxForm(function (response) {
        var $inputs = $('#addGenreForm input[type=text]');
        $('.response').remove();
        switch (response.left) {
            case "HANDLE":
                $respTag.text(response.right.message);
                if (response.right.state == "SUCCESS") {
                    $respTag.css(successStyle);
                    $addGenreForm.prepend($respTag);
                    $inputs.val('');
                } else if (response.right.state == "FAILURE") {
                    var inputSelector = 'input[name=' + response.right.target + ']';
                    var $input = $(inputSelector);
                    $respTag.css(failureStyle);
                    $input.parent().after($respTag);
                }
                break;
            case "ERROR":
                $respTag.css(failureStyle);
                $respTag.text(response.right.causeMessage + " " + response.right.toDoMessage);
                $addGenreForm.after($respTag);
                break;
        }
    });
});
