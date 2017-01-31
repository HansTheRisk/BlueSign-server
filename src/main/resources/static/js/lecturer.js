$(document).ready(function(){

    $.ajax({
        type: "GET",
        url: "/lecturer/student",
        dataType: "json",
        cache: false,
        success: function(data) {
            $('#1').append(JSON.stringify(data[0].name));
        }
    });

});