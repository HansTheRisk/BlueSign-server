$(document).ready(function(){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    getCall("/lecturer/modules", "json", loadModules);
//    $.ajax({
//        type: "GET",
//        url: "/lecturer/student",
//        dataType: "json",
//        cache: false,
//        success: function(data) {
//            $('#1').append(JSON.stringify(data[0].name));
//        }
//    });
//
//    var requestData = JSON.stringify({ "message":"testMessage" });
//
//    $.ajax({
//        type: "POST",
//        url: "/lecturer/student",
//        beforeSend: function(xhr){
//                xhr.setRequestHeader(header, token);
//            },
//        data: requestData,
//        dataType: "json",
//        contentType: "application/json",
//        cache: false,
//        success: function() {
//            $('#1').append("OK");
//        },
//        error: function (xhr, ajaxOptions, thrownError) {
//            $('#1').append(xhr.status + ": " + thrownError);
//        }
//    });

});

$(document).ready(function(){
	$(document).on("click", "#modules button", function() {
        var moduleCode = $(this).attr("moduleCode");
        getCall("lecturer/modules/"+moduleCode+"/classes", "json", loadClasses);
	})
});

function getCall(url, type, method) {
    $.ajax({
      type: "GET",
      url: url,
      dataType: type,
      cache: false,
      success: method
    });
}

function loadClasses(json) {
    for(var i = 0; i < json.length; i++) {
        var startDate = new Date(json[i].startDateTimestamp);
        var endDate = new Date(json[i].endDateTimestamp);
        $('<li class="list-group-item">' + startDate + ' - ' + endDate +'</li>').appendTo($('#classes'));
    }
}

function loadModules(json) {
    for(var i = 0; i < json.length; i++) {
        var moduleCode = JSON.stringify(json[i].moduleCode).replace(/"/g, '');
        var moduleTitle = JSON.stringify(json[i].title).replace(/"/g, '');
        $('<button type="button" moduleCode="'+moduleCode+'" class="btn btn-default">'+ moduleCode + ': ' + moduleTitle + '</button>').appendTo($('#modules'));
    }
}