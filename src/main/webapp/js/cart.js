$(document).ready(function () {
    var $addToCartBtn = $('.add-to-cart-button');
    var $removeFromCartLink = $('.remove-from-cart-link');
    $addToCartBtn.click(function () {
        $(this).text('In cart');
    });
    $removeFromCartLink.click(function () {
        $(this).parent().submit();
    });

    var $addToCartForm = $('.add-to-cart-form');
    var $makePurchaseForm = $('#makePurchaseForm');

    var respSelector = '<div class="row response"></div>';
    var $respTag = $(respSelector);
    var successStyle = {"color": "#337ab7", "font-size": "15px"};
    var failureStyle = {"color": "#83353d", "font-size": "13px"};

    $addToCartForm.ajaxForm(function (response) {
        $('.response').remove();
        switch (response.left) {
            case "OK":
                break;
            case "ERROR":
                $respTag.css(failureStyle);
                $respTag.text(response.right.causeMessage + " " + response.right.toDoMessage);
                $addToCartForm.after($respTag);
                break;
        }
    });

    $makePurchaseForm.ajaxForm(function (response) {
        $('.response').remove();
        switch (response.left) {
            case "HANDLE":
                $respTag.text(response.right.message);
                if (response.right.state == "SUCCESS") {
                    var $items = $('#itemsTable').find('td');
                    $items.remove();
                    $respTag.css(successStyle);
                    $makePurchaseForm.prepend($respTag);
                } else if (response.right.state == "FAILURE") {
                    $respTag.css(failureStyle);
                    $makePurchaseForm.after($respTag);
                }
                break;
            case "ERROR":
                $respTag.css(failureStyle);
                $respTag.text(response.right.causeMessage + " " + response.right.toDoMessage);
                $makePurchaseForm.after($respTag);
                break;
        }
    });

});
