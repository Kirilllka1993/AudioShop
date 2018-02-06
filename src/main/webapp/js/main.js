/**
 *
 */

/*
 $(document).ready(function() {
 // bind 'myForm' and provide a simple callback function
 $(".add-to-cart").ajaxForm(function(response) {
 $.each(response, function (key, value) {
 $(".add-to-cart-button").val("Added to cart");/!*
 $("<p>").text(key+" "+value).appendTo($("#register-section"));*!/
 });
 });
 });
 */

$(document).ready(function () {

    $(".add-to-cart-button").attr("data-clicked", "false");

    $(".add-to-cart-button").on("click", function () {
        $(this).attr("data-clicked", "true");
    });

    $(".add-to-cart").ajaxForm(function (response) {
        $button = $(".add-to-cart-button[data-clicked='true']");
        $button.text("dasda");
        $button.attr("data-clicked", "false");
    });

});

// attach handler to form's submit event
/*$('.add-to-cart').submit(function() {
 // submit the form
 $(this).ajaxSubmit();
 $('.add-to-cart-button').text("Added to cart");
 // return false to prevent normal browser submit and page navigation
 return false;
 });*/
