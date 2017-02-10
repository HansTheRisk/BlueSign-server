$(document).ready(function(){
    getCall("/lecturer/modules", "json", loadModules);
});

$(document).ready(function(){
	$(document).on("click", "#modules button", function() {
        var moduleCode = $(this).attr("moduleCode");
        var title = $(this).attr("moduleTitle");
        getCall("lecturer/modules/"+moduleCode+"/classes", "json", loadClasses);
        $('#moduleDetailsJumbo').empty();
        $('#classAttendanceDetails').empty();
        $('#moduleDetailsJumbo').append('<p>'+moduleCode+': '+ title + '</p>');
        getCall("lecturer/modules/"+moduleCode+"/attendance", "json", loadModuleAttendance);
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

        $('#classAttendanceDetails').empty();
        var caption = $('<div class="caption"></div>').appendTo($('#classAttendanceDetails'));

        caption.append('<h4>'+moduleCode+'<h4>');
        caption.append('<em>'+dateTimestamp+'<em>');
        getCall("lecturer/class/"+class_uuid+"/"+dateTimestamp+"/attendance", "json", loadClassAttendance);
	})
});

$(document).ready(function(){
	$("#myModal").on("show.bs.modal", function(e) {
		$('.modal-body').empty();
		getCall($(e.relatedTarget).attr("link"), "json", loadStudentsModal);
	});
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
    $('#classAttendanceDetails').empty();
    for(var i = 0; i < json.dates.length; i++) {
        var timestamp = json.dates[i].dateTimestamp;
        var date = new Date(timestamp);
        $('<li role="presentation" module_code="'+json.moduleCode+'" class_uuid="'+json.uuid+'" dateTimestamp="'+timestamp+'">' +
            '<a href="#">' + date.toLocaleDateString() + '</a></li>').appendTo($('#classesToDate'));
    }
}

function loadClassAttendance(json) {
    var caption = $('#classAttendanceDetails .caption');
    caption.append($('<h5>Attendance Percentage: '+calculatePercentage(json.attended, json.allocated)+'%</h5>'));

    caption.append($('<p>Allocated to class: '+json.allocated+'</p>'));
    caption.append($('<p>Attended the class: '+json.attended+'</p>'));
    caption.append('<p><a link="lecturer/class/'+json.classUuid+'/'+json.dateTimestamp+'/attended" class="btn btn-primary" data-toggle="modal" data-target="#myModal" role="button">Students who attended</a>'+
                   '<a link="lecturer/class/'+json.classUuid+'/'+json.dateTimestamp+'/notAttended" class="btn btn-default" data-toggle="modal" data-target="#myModal" role="button">Students who did not attend</a></p>');
}

function loadStudentsModal(json) {
        var table = $('<table class="table">' +
                            '<thead class="thead-inverse"><tr><th>#</th><th>University ID</th><th>Name</th><th>Username</th></tr></thead>' +
                    '</table>').appendTo($('.modal-body'));
        var tableContent = $('<tbody></tody>').appendTo(table);
        for(var i = 0; i < json.length; i++) {
            tableContent.append($('<tr><th scope="row">'+(i+1)+'</th><td>'+json[i].universityId+'</td><td>'+json[i].name+'</td><td>'+json[i].surname+'</td></tr>'));
        }
}

function loadModuleAttendance(json) {
    $('#moduleDetailsJumbo').append('<h4>Completed classes to date: '+ json.totalClassesCompletedToDate + '</h4>');
    $('#moduleDetailsJumbo').append('<h4>Attendance percentage: '+ calculatePercentage(json.totalActualAttendances, json.totalExpectedAttendancesToDate) + '%</h4>');
    $('#moduleDetailsJumbo').append('<p><a class="btn btn-primary" href="#" role="button">Non-attenders</a></p>');
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
                                         + JSON.stringify(json[i].room).replace(/"/g, '')
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

function calculatePercentage(actual, total) {
    if (total == 0)
        return (0).toFixed(2);
    else
        return ((actual / total) * 100).toFixed(2);
}