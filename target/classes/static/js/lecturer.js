$(document).ready(function(){
    getCall("/lecturer/modules", "json", loadModules);
});

$(document).ready(function(){
	$(document).on("click", "#modules button", function() {
        var moduleCode = $(this).attr("moduleCode");
        var title = $(this).attr("moduleTitle");
        getCall("lecturer/modules/"+moduleCode+"/classes", "json", loadClasses);
        $('#moduleDetailsJumbo').empty();
        $('#moduleDetailsJumbo').append('<h4>'+moduleCode+': '+ title + '</h4>');
        $('#moduleDetailsJumbo').append('<p><a class="btn btn-primary" href="#" role="button">Learn more</a></p>');
	})
});

$(document).ready(function(){
	$(document).on("click", "#classes li", function() {
        var class_uuid = $(this).attr("class_uuid");
        getCall("lecturer/class/"+class_uuid+"/toDate", "json", loadClassesToDate);
	})
});

$(document).ready(function(){
	$(document).on("click", "#classesToDate li", function() {
        var class_uuid = $(this).attr("class_uuid");
        var dateTimestamp = $(this).attr("dateTimestamp");
        var moduleCode = $(this).attr("module_code");

        $('#classAttendance').empty();
        var thumbnail = $('<div id="classAttendanceDetails" class="thumbnail"></div>').appendTo('#classAttendance');
        var caption = $('<div class="caption"></div>').appendTo(thumbnail);

        caption.append('<h4>'+moduleCode+'<h4>');
        caption.append('<em>'+dateTimestamp+'<em>');
        getCall("lecturer/class/"+class_uuid+"/"+dateTimestamp+"/attendance", "json", loadClassAttendance);
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

function loadClassesToDate(json) {
    $('#classesToDate').empty();
    for(var i = 0; i < json.dates.length; i++) {
        var timestamp = json.dates[i].dateTimestamp;
        var date = new Date(timestamp);
        $('<li role="presentation" module_code="'+json.moduleCode+'" class_uuid="'+json.uuid+'" dateTimestamp="'+timestamp+'">' +
            '<a href="#">' + date.toLocaleDateString() + '</a></li>').appendTo($('#classesToDate'));
    }
}

function loadClassAttendance(json) {
    var caption = $('#classAttendanceDetails .caption');
    caption.append($('<h5>Attendance Percentage: ' + ((json.attended / json.allocated) * 100).toFixed(2) +'%</h5>'));
    caption.append($('<p>Allocated to class: '+json.allocated+'</p>'));
    caption.append($('<p>Attended the class:'+json.attended+'</p>'));
    caption.append('<p><a href="#" class="btn btn-primary" role="button">Button</a>'+
                   '<a href="#" class="btn btn-default" role="button">Button</a></p>');
}

function loadModuleAttendance(json) {

}

function loadClasses(json) {
    $('#classes').empty();
    $('#classesToDate').empty();
    for(var i = 0; i < json.length; i++) {
        var startDate = new Date(json[i].startDateTimestamp);
        var endDate = new Date(json[i].endDateTimestamp);
        $('<li role="presentation" class_uuid="'+json[i].uuid+'"><a href="#">' + startDate.toLocaleDateString()
                                         + ' - '
                                         + endDate.toLocaleDateString()
                                         + '</br>'
                                         + startDate.toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'}) + ' - '
                                         + endDate.toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})
                                         + ' ' + JSON.stringify(json[i].day).replace(/"/g, '')
                                         + '</br>'
                                         + + JSON.stringify(json[i].room).replace(/"/g, '')
                                         + '</a></li>').appendTo($('#classes'));
    }
}

function loadModules(json) {
    for(var i = 0; i < json.length; i++) {
        var moduleCode = JSON.stringify(json[i].moduleCode).replace(/"/g, '');
        var moduleTitle = JSON.stringify(json[i].title).replace(/"/g, '');
        $('<button type="button" moduleCode="'+moduleCode+'" moduleTitle="'+moduleTitle+'" class="btn btn-default">'+ moduleCode + '</button>').appendTo($('#modules'));
    }
}