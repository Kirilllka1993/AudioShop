
$select = $(".select-box");
$select.select2(/* ... */); // initialize Select2 and any events

var $option = $('<option selected>Loading...</option>');

$select.append($option).trigger('change'); // append the option and update Select2

$.ajax({ // make the request for the selected data object
    type: 'GET',
    url: '/s?/command=load_genres',
    dataType: 'json'
}).then(function (data) {
    // Here we should have the data object
    $option.text(data.text).val(data.id); // update the text that is displayed (and maybe even the value)
    $option.removeData(); // remove any caching data that might be associated
    $select.trigger('change'); // notify JavaScript components of possible changes
});