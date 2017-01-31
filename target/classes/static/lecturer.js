$(document).ready(function(){

    $.ajax({
        type: "GET",
        url: "/student/b00642446/1234",
        dataType: "jsonp",
        cache: false,
        success: function(data) {
            $('#1').append(JSON.stringify(data.value));
        }
    });

});