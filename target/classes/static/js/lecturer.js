$(document).ready(function(){
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        type: "GET",
        url: "/lecturer/student",
        dataType: "json",
        cache: false,
        success: function(data) {
            $('#1').append(JSON.stringify(data[0].name));
        }
    });

    var requestData = JSON.stringify({ "message":"testMessage" });

    $.ajax({
        type: "POST",
        url: "/lecturer/student",
        beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
        data: requestData,
        dataType: "json",
        contentType: "application/json",
        cache: false,
        success: function() {
            $('#1').append("OK");
        },
        error: function (xhr, ajaxOptions, thrownError) {
            $('#1').append(xhr.status + ": " + thrownError);
        }
    });

});