$(document).ready(function(){
    $(document).on("click", "#navTabs li a", function(e) {
        e.preventDefault();
        var attribute = $(this).attr("href");

        $("#users").hide();
        $("#students").hide();
        $("#modules").hide();
        $("#classes").hide();
        $("#ip").hide();

        if(attribute == "#users") {
            $('#removeUserButton').attr('disabled', true);
            $('#removeUserButton').removeAttr('data-target');
            getCall("/admin/user", "json", loadUsers);
        }
        else if(attribute == "#students") {
            $('#removeStudentButton').attr('disabled', true);
            $('#removeStudentButton').removeAttr('data-target');
            getCall("/admin/student", "json", loadStudents);
        }
        else if(attribute == "#modules") {
            $('#removeModuleButton').attr('disabled', true);
            $('#removeModuleButton').removeAttr('data-target');
            $('#studentsAssignedToModulePills').empty();
            getCall("/admin/module", "json", loadModules);
        }
        else if(attribute == "#classes") {
            $('#classesPills').empty();
            $('#studentsAssignedToClassPills').empty();
            $('#createClassButton').removeAttr('data-target');
            $('#createClassButton').attr('disabled', true);
            $('#removeClassButton').removeAttr('data-target');
            $('#removeClassButton').attr('disabled', true);
            getCall("/admin/module", "json", loadModulesDropdown);
        }
        else if(attribute == "#ip") {
            getCall("/admin/ip", "json", loadIps);
            $('#removeIpButton').removeAttr('data-target');
            $('#removeIpButton').attr('disabled', true);
        }
        $(attribute).show();
    })
});

$(document).ready(function(){
	$(document).on("click", "#usersPills li", function(e) {
        e.preventDefault();
        $("#usersPills").children('li').removeClass('active');
        $(this).addClass('active');
        $('#removeUserButton').attr('disabled', false);
        $('#removeUserButton').attr('data-target', '#myModal');

        var uuid = $(this).attr("user_uuid");
        var username = $(this).attr("username");
        var name = $(this).attr("name");
        var surname = $(this).attr("surname");
        var role = $(this).attr("userType");
        var email = $(this).attr("email");

        var info = $('#userInfo');
        info.empty();

        info.append('<h4>'+name+" "+surname+'<h4>');
        info.append('<em>'+uuid+'<em></br>');
        info.append('<p><table cellspacing="2"><tr><th></th><th></th></tr><tr><td>Name: </td><td>'+name+'</td></tr><tr><td>Surname: </td><td>'+surname+'</td></tr><tr><td>Username: </td><td>'+username+'</td></tr><tr><td>Email: </td><td>'+email+'</td></tr><tr><td>Role: </td><td>'+role+'</td></tr></table><p>');
        info.append('<p><a class="btn btn-primary" callType="editUser" data-toggle="modal" data-target="#myModal" role="button">Edit</a></p>');
        info.append('<p><a class="btn btn-primary" callType="resetPassword" data-toggle="modal" data-target="#myModal" role="button">Reset password</a></p>');
	})
});

$(document).ready(function(){
	$(document).on("click", "#studentsPills li", function(e) {
        e.preventDefault();
        $('#removeStudentButton').attr('disabled', false);
        $('#removeStudentButton').attr('data-target', '#myModal');
        $("#studentsPills").children('li').removeClass('active');
        $(this).addClass('active');

        var id = $(this).attr("universityId");
        var name = $(this).attr("name");
        var surname = $(this).attr("surname");
        var email = $(this).attr("email");

        var info = $('#studentsInfo');
        info.empty();

        info.append('<h4>'+name+" "+surname+'<h4>');
        info.append('<em>'+id+'<em></br>');
        info.append('<p><table cellspacing="2"><tr><th></th><th></th></tr><tr><td>Name: </td><td>'+name+'</td></tr><tr><td>Surname: </td><td>'+surname+'</td></tr><tr><td>Email: </td><td>'+email+'</td></tr></table><p>');
        info.append('<p><a class="btn btn-primary" callType="editStudent" data-toggle="modal" data-target="#myModal" role="button">Edit</a></p>');
        info.append('<p><a class="btn btn-primary" callType="resetPin" data-toggle="modal" data-target="#myModal" role="button">Reset pin</a></p>');
	})
});

$(document).ready(function(){
	$(document).on("click", "#modulesPills li", function(e) {
        e.preventDefault();
        $('#removeModuleButton').attr('disabled', false);
        $('#removeModuleButton').attr('data-target', '#myModal');
        $('#addStudentToModuleButton').attr('disabled', false);
        $('#addStudentToModuleButton').attr('data-target', '#myModal');

        $("#modulesPills").children('li').removeClass('active');
        $(this).addClass('active');

        var moduleCode = $(this).attr("moduleCode");
        var title = $(this).attr("title");
        var lecturerUuid = $(this).attr("lecturerUuid");

        var info = $('#moduleInfo');
        info.empty();

        info.append('<h4>'+moduleCode+'<h4>');
        info.append('<em>'+title+'<em></br>');
        info.append('<p><table cellspacing="2"><tr><th></th><th></th></tr><tr><td>Lecturer: </td><td><div id="lecturerInfo"></td></tr></table><p>');
        info.append('<p><a class="btn btn-primary" callType="editModule" data-toggle="modal" data-target="#myModal" role="button">Edit</a></p>');
        getCall("admin/user/"+lecturerUuid, "json", loadLecturer);
        getCall("admin/module/"+moduleCode+"/student", "json", loadModuleStudents);
	})
});

$(document).ready(function(){
	$(document).on("click", "#studentsAssignedToModulePills li", function(e) {
        e.preventDefault();
        $("#studentsAssignedToModulePills").children('li').removeClass('active');
        $(this).addClass('active');
        $('#removeStudentFromModuleButton').attr('disabled', false);
        $('#removeStudentFromModuleButton').attr('data-target', '#myModal');
	})
});

$(document).ready(function(){
	$(document).on("click", "#classesPills li", function(e) {
        e.preventDefault();
        $("#classesPills").children('li').removeClass('active');
        $(this).addClass('active');
        $('#removeClassButton').attr('disabled', false);
        $('#removeClassButton').attr('data-target', '#myModal');
        var classUuid = $(this).attr("class_uuid");
        getCall("admin/class/"+classUuid+"/student", "json", loadClassStudents);
	})
});

$(document).ready(function(){
	$(document).on("click", "#ipPills li", function(e) {
        e.preventDefault();
        $("#ipPills").children('li').removeClass('active');
        $(this).addClass('active');
        $('#removeIpButton').attr('disabled', false);
        $('#removeIpButton').attr('data-target', '#myModal');
	})
});

$(document).ready(function(){
    $(document).on("change", "#modulesDropDownList", function(e) {
        var valueSelected = this.value;
        if(valueSelected === 'null') {
            $('#classesPills').empty();
            $('#createClassButton').attr('disabled', true);
            $('#createClassButton').removeAttr('data-target');
        }
        else {
            getCall("/admin/module/"+valueSelected+"/class", "json", loadClasses);
            $('#createClassButton').attr('disabled', false);
            $('#createClassButton').attr('data-target', '#myModal');
        }
    })
});

$(document).ready(function(){
    $(document).on("change", "#group", function(e) {
        var valueSelected = this.value;
        var moduleCode = $('#modulesDropDownList option:selected').val();
        getCall("/admin/module/"+moduleCode+"/"+valueSelected+"/student", "json", loadStudentsForClassCreate);
    })
});

$(document).ready(function(){
    $(document).on("submit", "#userForm", function(e) {
        $(this).find("button").attr('disabled', true);
        var id = $(this).find("button").attr("id");
        var username = $('#username').val();
        var name = $('#name').val();
        var surname = $('#surname').val();
        var email = $('#email').val();

        if(id=="submit") {
            var role = $('#role option:selected').val();
            var passwordOne = $('#passwordOne').val();
            var passwordTwo = $('#passwordTwo').val();

            if (passwordOne != passwordTwo) {
                        e.preventDefault();
                        $('#alertText').empty();
                        $('#alertText').append("Given passwords don't match!");
                        $(this).find("button").attr('disabled', false);
            }
            else {
                e.preventDefault();
                var requestData = JSON.stringify({ "username":username, "name":name, "surname":surname, "role":role, "password":passwordOne, "email":email});
                postCall("admin/user", "json", requestData, userCreateSuccess, userCreateFail);
            }
        }
        else if(id=="update") {
            e.preventDefault();
            var selectedUser = $('#usersPills .active');
            var uuid = selectedUser.attr("user_uuid");
            var requestData = JSON.stringify({ "username":username, "name":name, "surname":surname, "email":email});
            putCall("admin/user/"+uuid, "json", requestData, userUpdateSuccess, userCreateFail);
        }
        else if(id=="reset") {
            e.preventDefault();
            var selectedUser = $('#usersPills .active');
            var uuid = selectedUser.attr("user_uuid");

            var passwordOne = $('#passwordOne').val();
            var passwordTwo = $('#passwordTwo').val();

            if (passwordOne != passwordTwo) {
                        e.preventDefault();
                        $('#alertText').empty();
                        $('#alertText').append("Given passwords don't match!");
                        $(this).find("button").attr('disabled', false);
            }
            else {
                e.preventDefault();
                var requestData = JSON.stringify({ "password":passwordOne});
                postCall("admin/user/"+uuid+"/password", "json", requestData, passwordResetSuccess, userCreateFail);
            }
        }
        else if(id=="delete") {
            e.preventDefault();
            var selectedUser = $('#usersPills .active');
            var uuid = selectedUser.attr("user_uuid");
            deleteCall('admin/user/'+uuid, "json", userDeleteSuccess, userCreateFail);
        }
    })
});

$(document).ready(function(){
    $(document).on("submit", "#studentForm", function(e) {
        $(this).find("button").attr('disabled', true);
        var selectedUser = $('#studentsPills .active');
        var id = $(this).find("button").attr("id");
        var universityId = $('#universityId').val();
        var name = $('#name').val();
        var surname = $('#surname').val();
        var email = $('#email').val();

        if(id=="submit") {
            e.preventDefault();
            var requestData = JSON.stringify({ "universityId":universityId, "name":name, "surname":surname, "email":email});
            postCall("admin/student", "json", requestData, studentCreateSuccess, userCreateFail);
        }
        else if(id=="update") {
            e.preventDefault();
            var requestData = JSON.stringify({ "universityId":selectedUser.attr("universityId"), "name":name, "surname":surname, "email":email});
            putCall("admin/student/"+selectedUser.attr("universityId"), "json", requestData, studentUpdateSuccess, userCreateFail);
        }
        else if(id=="pinreset") {
            e.preventDefault();
            getCall("admin/student/"+selectedUser.attr("universityId")+"/pin/reset", "json", pinResetSuccess, userCreateFail);
        }
        else if(id=="delete") {
            e.preventDefault();
            deleteCall("admin/student/"+selectedUser.attr("universityId"), "json", studentDeleteSuccess, userCreateFail);
        }
    })
});

$(document).ready(function(){
    $(document).on("submit", "#moduleForm", function(e) {
        $(this).find("button").attr('disabled', true);
        var selectedModule = $('#modulesPills .active');
        var id = $(this).find("button").attr("id");
        var lecturerUuid = $('#lecturer option:selected').val();
        var moduleCode = $('#moduleCode').val();
        var title = $('#title').val();
        var studentIds = [];

        $(this).find('input[type="checkbox"]').each(function(index){
            if($(this).is(':checked')) {
                studentIds.push($(this).attr("value"));
            }
        })

        if(id=="submit") {
            e.preventDefault();
            if (studentIds.length == 0) {
                    e.preventDefault();
                    $('#alertText').empty();
                    $('#alertText').append("A module must have students allocated!");
                    $(this).find("button").attr('disabled', false);
            }
            else {
                    e.preventDefault();
                    var requestData = JSON.stringify({ "moduleCode":moduleCode, "title":title, "lecturerUuid":lecturerUuid, "studentIds":studentIds});
                    postCall("admin/module", "json", requestData, moduleCreateSuccess, userCreateFail);
            }
        }
        else if(id=="update") {
            e.preventDefault();
            var requestData = JSON.stringify({ "title":title, "lecturerUuid":lecturerUuid});
            putCall("admin/module/"+selectedModule.attr("moduleCode"), "json", requestData, moduleUpdateSuccess, userCreateFail);
        }
        else if(id=="delete") {
            e.preventDefault();
            deleteCall("admin/module/"+selectedModule.attr("moduleCode"), "json", moduleDeleteSuccess, userCreateFail);
        }
        else if(id=="addStudent") {
            e.preventDefault();
            var group = $('#groupSelect option:selected').val();
            var student = $('#studentSelect option:selected').val();
            var requestData = JSON.stringify({ "universityId":student});
            putCall("admin/module/"+selectedModule.attr('moduleCode')+"/"+group+"/student", "json", requestData, addStudentToModuleSuccess, userCreateFail);
        }
        else if(id=="deleteStudent") {
            e.preventDefault();
            var moduleCode = $('#modulesPills .active').attr("moduleCode");
            var selectedStudentId = $('#studentsAssignedToModulePills .active').attr("universityId");
            deleteCall("admin/module/"+moduleCode+"/student/"+selectedStudentId, "json", removeStudentFromModuleSuccess, userCreateFail);

        }
    })
});

$(document).ready(function(){
    $(document).on("submit", "#classForm", function(e) {
        $(this).find("button").attr('disabled', true);
        var id = $(this).find("button").attr("id");
        var weeks = parseInt($('#weeks').val());
        var days = weeks * 7;
        var startDate = new Date($('#startDate').val());
        var endDate = new Date($('#startDate').val());
        var startTime = $('#startTime').val();
        var endTime = $('#endTime').val();
        var group = $('#group option:selected').val();
        var locked = $('#studentsList').attr("locked")
        var selectedModule = $('#modulesDropDownList option:selected').val();
        var room = $('#room').val();
        var studentIds = [];

        endDate.setDate(endDate.getDate() + days);


        if(id=="submit") {
            if(parseInt(endTime) < parseInt(startTime)) {
                e.preventDefault();
                $('#alertText').empty();
                $('#alertText').append("End time cannot be smaller than start time!");
                $(this).find("button").attr('disabled', false);
            }
            else {

                startDate.setHours(startTime);
                startDate.setMinutes(15);
                endDate.setHours(endTime);
                endDate.setMinutes(15);

                if (locked == "false") {
                    $(this).find('input[type="checkbox"]').each(function(index){
                        if($(this).is(':checked')) {
                            studentIds.push($(this).attr("value"));
                        }
                    })
                    if(studentIds.length == 0) {
                        e.preventDefault();
                        $('#alertText').empty();
                        $('#alertText').append("A class for a new group cannot be created without allocated students!");
                        $(this).find("button").attr('disabled', false);
                    }
                    else {
                        e.preventDefault();
                        var requestData = JSON.stringify({ "moduleCode":selectedModule, "room":room, "group":group, "startDateTimestamp":startDate.getTime(), "studentIds":studentIds, "endDateTimestamp":endDate.getTime()});
                        postCall("admin/class", "json", requestData, classCreateSuccess, userCreateFail);
                    }
                }
                else {
                        e.preventDefault();
                        var requestData = JSON.stringify({ "moduleCode":selectedModule, "room":room, "group":group, "startDateTimestamp":startDate.getTime(), "endDateTimestamp":endDate.getTime()});
                        postCall("admin/class", "json", requestData, classCreateSuccess, userCreateFail);
                }
            }

        }
        else if(id=="delete") {
            e.preventDefault();
            var classUuid = $('#classesPills .active').attr("class_uuid");
            deleteCall("admin/class/"+classUuid, "json", deleteClassSuccess, userCreateFail);
        }
    })
});

$(document).ready(function(){
    $(document).on("submit", "#ipForm", function(e) {
        $(this).find("button").attr('disabled', true);
        var id = $(this).find("button").attr("id");
        var selectedIp = $('#ipPills .active');
        var start = $('#rangeStart').val();
        var end = $('#rangeEnd').val();

        if(id=="submit") {
            e.preventDefault();
            var requestData = JSON.stringify({ "ipRangeStart":start, "ipRangeEnd":end});
            postCall("admin/ip", "json", requestData, ipCreateSuccess, userCreateFail);
        }
        else if(id=="delete") {
            e.preventDefault();
            deleteCall("admin/ip/"+selectedIp.attr("uuid"), "json", userCreateFail, ipRemoveSuccess);
        }
    })
});

$(document).ready(function(){
	$("#myModal").on("show.bs.modal", function(e) {
            var type = $(e.relatedTarget).attr("callType")
            $('.modal-body').empty();
            if(type == "addUser") {
                loadAddUserModal();
            }
            else if(type == "editUser") {
                loadEditUserModal();
            }
            else if(type == "removeUser") {
                loadRemoveUserModal();
            }
            else if(type == "resetPassword") {
                loadResetPasswordModal();
            }
            else if(type == "addStudent") {
                loadAddStudentModal();
            }
            else if(type == "editStudent") {
                loadEditStudentModal();
            }
            else if(type == "removeStudent") {
                loadRemoveStudentModal();
            }
            else if(type == "resetPin") {
                loadPinResetModal();
            }
            else if(type == "addModule") {
                loadAddModuleModal();
            }
            else if(type == "editModule") {
                loadEditModuleModal();
            }
            else if(type == "removeModule") {
                loadRemoveModuleModal();
            }
            else if(type == "addStudentToModule") {
                loadAddStudentToModuleModal();
            }
            else if(type == "removeStudentFromModule") {
                loadRemoveStudentFromModuleModal();
            }
            else if(type == "addClass") {
                loadAddClassModal();
            }
            else if(type == "removeClass") {
                loadRemoveClassModal();
            }
            else if(type == "addIp") {
                loadAddIpModal();
            }
            else if(type == "removeIp") {
                loadRemoveIpModal();
            }
	});
});

$(document).ready(function(){
    $("#students").hide();
    $("#modules").hide();
    $("#classes").hide();
    $("#ip").hide();
    $("#users").show();
    getCall("/admin/user", "json", loadUsers);
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

function getCall(url, type, method, failMethod) {
    $.ajax({
      type: "GET",
      url: url,
      dataType: type,
      cache: false,
      success: method,
      error: failMethod
    });
}

function deleteCall(url, type, method, failMethod) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
      type: "DELETE",
      url: url,
      beforeSend: function(xhr){
                          xhr.setRequestHeader(header, token);
                  },
      dataType: type,
      cache: false,
      success: method,
      error: failMethod
    });
}

function postCall(url, type, data, okMethod, failMethod) {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
            type: "POST",
            url: url,
            beforeSend: function(xhr){
                    xhr.setRequestHeader(header, token);
                },
            data: data,
            dataType: type,
            contentType: "application/json",
            cache: false,
            success: okMethod,
            error: failMethod
        });
}

function putCall(url, type, data, okMethod, failMethod) {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
            type: "PUT",
            url: url,
            beforeSend: function(xhr){
                    xhr.setRequestHeader(header, token);
                },
            data: data,
            dataType: type,
            contentType: "application/json",
            cache: false,
            success: okMethod,
            error: failMethod
        });
}

function loadAddUserModal() {
    var modalBody = $('.modal-body');
    modalBody.append($(
    '<div id="userForm">' +
         '<div id="alert" class="alert alert-warning">' +
            '<a href="#" class="close" data-dismiss="alert"></a>' +
            '<div id ="alertText"></div>' +
         '</div>' +
         '<form>' +
             '<div class="form-group">' +
                 '<label for="username">Username</label>' +
                 '<input type="text" class="form-control" id="username" placeholder="Username" required></input>' +
             '</div>' +
             '<div class="form-group">' +
                 '<label for="name">Name</label>' +
                 '<input type="text" class="form-control" id="name" placeholder="Name" required></input>' +
             '</div>' +
             '<div class="form-group">' +
                 '<label for="surname">Surname</label>' +
                 '<input type="text" class="form-control" id="surname" placeholder="Surname" required></input>' +
             '</div>' +
             '<div class="form-group">' +
                 '<label for="email">Email address</label>' +
                 '<input type="email" class="form-control" id="email" placeholder="Email" required placeholder="Enter a valid email address"></input>' +
             '</div>' +
             '<div class="form-group">' +
                 '<label for="passwordOne">Password</label>' +
                 '<input type="password" class="form-control" id="passwordOne" placeholder="Password" required></input>' +
                 '<input type="password" class="form-control" id="passwordTwo" placeholder="Password" required></input>' +
             '</div>' +
             '<div class="form-group">' +
                 '<label for="role">Role : </label>' +
                 '<select class="custom-select mb-2 mr-sm-2 mb-sm-0" id="role">' +
                     '<option value="ROLE_LECTURER">LECTURER</option>' +
                     '<option value="ROLE_ADMIN">ADMIN</option>' +
                 '</select>' +
             '</div>' +
             '<button id="submit" type="submit" class="btn btn-primary">Submit</button>' +
         '</form>' +
     '</div>'));
}

function loadEditUserModal() {

    var selectedUser = $('#usersPills .active');
    var username = selectedUser.attr("username");
    var name = selectedUser.attr("name");
    var surname = selectedUser.attr("surname");
    var email = selectedUser.attr("email");

    var modalBody = $('.modal-body');
        modalBody.append($(
        '<div id="userForm">' +
             '<div id="alert" class="alert alert-warning">' +
                '<a href="#" class="close" data-dismiss="alert"></a>' +
                '<div id ="alertText"></div>' +
             '</div>' +
             '<form>' +
                 '<div class="form-group">' +
                     '<label for="username">Username</label>' +
                     '<input type="text" class="form-control" id="username" value="'+username+'" required></input>' +
                 '</div>' +
                 '<div class="form-group">' +
                     '<label for="name">Name</label>' +
                     '<input type="text" class="form-control" id="name" value="'+name+'" required></input>' +
                 '</div>' +
                 '<div class="form-group">' +
                     '<label for="surname">Surname</label>' +
                     '<input type="text" class="form-control" id="surname" value="'+surname+'"required></input>' +
                 '</div>' +
                 '<div class="form-group">' +
                     '<label for="email">Email address</label>' +
                     '<input type="email" class="form-control" id="email" placeholder="Email" value="'+email+'" required></input>' +
                 '</div>' +
                 '<button id="update" type="submit" class="btn btn-primary">Update</button>' +
             '</form>' +
         '</div>'));
}

function loadResetPasswordModal() {
    var modalBody = $('.modal-body');
            modalBody.append($(
            '<div id="userForm">' +
                 '<div id="alert" class="alert alert-warning">' +
                    '<a href="#" class="close" data-dismiss="alert"></a>' +
                    '<div id ="alertText"></div>' +
                 '</div>' +
                 '<form>' +
                     '<div class="form-group">' +
                         '<label for="passwordOne">Password</label>' +
                         '<input type="password" class="form-control" id="passwordOne" placeholder="Password" required></input>' +
                         '<input type="password" class="form-control" id="passwordTwo" placeholder="Password" required></input>' +
                     '</div>' +
                     '<button id="reset" type="submit" class="btn btn-primary">Reset</button>' +
                 '</form>' +
             '</div>'));
}

function loadAddStudentModal() {
    var modalBody = $('.modal-body');
    modalBody.append($(
    '<div id="studentForm">' +
         '<div id="alert" class="alert alert-warning">' +
            '<a href="#" class="close" data-dismiss="alert"></a>' +
            '<div id ="alertText"></div>' +
         '</div>' +
         '<form>' +
             '<div class="form-group">' +
                 '<label for="universityId">University ID</label>' +
                 '<input type="text" class="form-control" id="universityId" placeholder="University ID" required></input>' +
             '</div>' +
             '<div class="form-group">' +
                 '<label for="name">Name</label>' +
                 '<input type="text" class="form-control" id="name" placeholder="Name" required></input>' +
             '</div>' +
             '<div class="form-group">' +
                 '<label for="surname">Surname</label>' +
                 '<input type="text" class="form-control" id="surname" placeholder="Surname" required></input>' +
             '</div>' +
             '<div class="form-group">' +
                 '<label for="email">Email address</label>' +
                 '<input type="email" class="form-control" id="email" placeholder="Email" required placeholder="Enter a valid email address"></input>' +
             '</div>' +
             '<button id="submit" type="submit" class="btn btn-primary">Submit</button>' +
         '</form>' +
     '</div>'));
}

function loadEditStudentModal() {

    var selectedUser = $('#studentsPills .active');
    var name = selectedUser.attr("name");
    var surname = selectedUser.attr("surname");
    var email = selectedUser.attr("email");

    var modalBody = $('.modal-body');
    modalBody.append($(
    '<div id="studentForm">' +
         '<div id="alert" class="alert alert-warning">' +
            '<a href="#" class="close" data-dismiss="alert"></a>' +
            '<div id ="alertText"></div>' +
         '</div>' +
         '<form>' +
             '<div class="form-group">' +
                 '<label for="name">Name</label>' +
                 '<input type="text" class="form-control" id="name" placeholder="Name" value="'+name+'" required></input>' +
             '</div>' +
             '<div class="form-group">' +
                 '<label for="surname">Surname</label>' +
                 '<input type="text" class="form-control" id="surname" placeholder="Surname" value="'+surname+'" required></input>' +
             '</div>' +
             '<div class="form-group">' +
                 '<label for="email">Email address</label>' +
                 '<input type="email" class="form-control" id="email" placeholder="Email" value="'+email+'" required placeholder="Enter a valid email address"></input>' +
             '</div>' +
             '<button id="update" type="submit" class="btn btn-primary">Submit</button>' +
         '</form>' +
     '</div>'));
}

function loadRemoveStudentModal() {
    var modalBody = $('.modal-body');
    modalBody.append($(
    '<div id="studentForm">' +
         '<div id="alert" class="alert alert-warning">' +
            '<a href="#" class="close" data-dismiss="alert"></a>' +
            '<div id ="alertText"></div>' +
         '</div>' +
         '<form><button id="delete" type="submit" class="btn btn-primary">Confirm</button></form>' +
     '</div>'));
}

function loadPinResetModal() {
    var modalBody = $('.modal-body');
    modalBody.append($(
    '<div id="studentForm">' +
         '<div id="alert" class="alert alert-warning">' +
            '<a href="#" class="close" data-dismiss="alert"></a>' +
            '<div id ="alertText"></div>' +
         '</div>' +
         '<form><button id="pinreset" type="submit" class="btn btn-primary">Confirm</button></form>' +
     '</div>'));
}

function loadAddModuleModal() {
    var modalBody = $('.modal-body');
    modalBody.append($(
    '<div>' +
         '<div id="alert" class="alert alert-warning">' +
            '<a href="#" class="close" data-dismiss="alert"></a>' +
            '<div id ="alertText"></div>' +
         '</div>' +
         '<form id="moduleForm">' +
             '<div class="form-group">' +
                 '<label for="moduleCode">Module code</label>' +
                 '<input type="text" class="form-control" id="moduleCode" placeholder="Module code" required></input>' +
             '</div>' +
             '<div class="form-group">' +
                 '<label for="name">Title</label>' +
                 '<input type="text" class="form-control" id="title" placeholder="Title" required></input>' +
             '</div>' +
         '</form>' +
     '</div>'));
     getCall("/admin/lecturer", "json", loadLecturersForModuleCreate);
     getCall("/admin/student", "json", loadStudentsForModuleCreate);
     setTimeout(function(){
       $('#moduleForm').append('<button id="submit" type="submit" class="btn btn-primary">Submit</button>');
     }, 500);
}

function loadAddStudentToModuleModal() {
    var selectedModule = $('#modulesPills .active');
    var moduleCode = selectedModule.attr("moduleCode");
    var modalBody = $('.modal-body');
    modalBody.append($(
    '<div>' +
         '<div id="alert" class="alert alert-warning">' +
            '<a href="#" class="close" data-dismiss="alert"></a>' +
            '<div id ="alertText"></div>' +
         '</div>' +
         '<form id="moduleForm">' +

         '</form>' +
     '</div>'));
     getCall("/admin/module/"+moduleCode+"/studentAvailable", "json", loadStudentsForModuleAdd);
     getCall("/admin/module/"+moduleCode+"/group", "json", loadModuleGroupsForModule);
     setTimeout(function(){
       $('#moduleForm').append('<button id="addStudent" type="submit" class="btn btn-primary">Submit</button>');
     }, 750);
}

function loadRemoveStudentFromModuleModal() {
    var modalBody = $('.modal-body');
    modalBody.append($(
    '<div id="moduleForm">' +
         '<div id="alert" class="alert alert-warning">' +
            '<a href="#" class="close" data-dismiss="alert"></a>' +
            '<div id ="alertText"></div>' +
         '</div>' +
         '<p>Are you sure you want to remove this student from the module?</p>'+
         '<form><button id="deleteStudent" type="submit" class="btn btn-primary">Confirm</button></form>' +
     '</div>'));
}

function loadEditModuleModal() {
    var selectedModule = $('#modulesPills .active');
    var title = selectedModule.attr("title");
    var lecturer = selectedModule.attr("lecturerUuid");
    var modalBody = $('.modal-body');
    modalBody.append($(
    '<div>' +
         '<div id="alert" class="alert alert-warning">' +
            '<a href="#" class="close" data-dismiss="alert"></a>' +
            '<div id ="alertText"></div>' +
         '</div>' +
         '<form id="moduleForm">' +
             '<div class="form-group">' +
                 '<label for="name">Title</label>' +
                 '<input type="text" class="form-control" id="title" placeholder="Title" value="'+title+'" required></input>' +
             '</div>' +
         '</form>' +
     '</div>'));
     getCall("/admin/lecturer", "json", loadLecturersForModuleCreate);
     setTimeout(function(){
       $("#lecturer").val(lecturer);
       $('#moduleForm').append('<button id="edit" type="submit" class="btn btn-primary">Submit</button>');
     }, 500);
}

function loadRemoveModuleModal() {
    var modalBody = $('.modal-body');
    modalBody.append($(
    '<div id="moduleForm">' +
         '<div id="alert" class="alert alert-warning">' +
            '<a href="#" class="close" data-dismiss="alert"></a>' +
            '<div id ="alertText"></div>' +
         '</div>' +
         '<p>Are you sure you want to remove this module?</p>'+
         '<form><button id="delete" type="submit" class="btn btn-primary">Confirm</button></form>' +
     '</div>'));
}

function loadAddClassModal() {
    var today = new Date();
    var modalBody = $('.modal-body');
    var moduleCode = $('#modulesDropDownList option:selected').val();
    modalBody.append($(
    '<div>' +
         '<div id="alert" class="alert alert-warning">' +
            '<a href="#" class="close" data-dismiss="alert"></a>' +
            '<div id ="alertText"></div>' +
         '</div>' +
         '<form id="classForm">' +
             '<div class="form-group">' +
                 '<label for="startDate">Start date</label>' +
                 '</br><input id="startDate" type="date" value="'+today.toISOString().substr(0, 10)+'" required/></input>' +
             '</div>' +
             '<div class="form-group">' +
                 '<label>Time</label>' +
                 '</br>From: <input id="startTime" type="number" min="09" max="17" step="1" required>:15</input>' +
             '</div>' +
              '<div class="form-group">' +
                  'To: <input id="endTime" name="end_time" type="number" min="10" max="18" step="1" required>:15</input>' +
              '</div>' +
             '<div class="form-group">' +
                 '<label for="role">Num of weeks : </label>' +
                 '</br><input type="number" min="0" max="10" step="1" value="0" name="weeks" id="weeks" required>' +
             '</div>' +
              '<div class="form-group">' +
                  '<label for="group">Group : </label>' +
                  '<select class="custom-select mb-2 mr-sm-2 mb-sm-0" id="group">' +
                      '<option value="NONE">None</option>' +
                      '<option value="A">A</option>' +
                      '<option value="B">B</option>' +
                      '<option value="C">C</option>' +
                      '<option value="D">D</option>' +
                  '</select>' +
              '</div>' +
              '<div class="form-group">' +
                 '<label for="room">Room</label>' +
                 '<input type="text" class="form-control" id="room" placeholder="Room" required></input>' +
              '</div>' +
              '<div id="studentFormGroup" class="form-group"><label for="students">Students</label></div>' +
         '</form>' +
     '</div>'));
     getCall("/admin/module/"+moduleCode+"/none/student", "json", loadStudentsForClassCreate);
     setTimeout(function(){
       $('#classForm').append('<button id="submit" type="submit" class="btn btn-primary">Submit</button>');
     }, 500);
}

function loadRemoveClassModal() {
    var modalBody = $('.modal-body');
    modalBody.append($(
    '<div id="classForm">' +
         '<div id="alert" class="alert alert-warning">' +
            '<a href="#" class="close" data-dismiss="alert"></a>' +
            '<div id ="alertText"></div>' +
         '</div>' +
         '<p>Are you sure you want to remove this class?</p>'+
         '<form><button id="delete" type="submit" class="btn btn-primary">Confirm</button></form>' +
     '</div>'));
}

function loadAddIpModal() {
    var today = new Date();
    var modalBody = $('.modal-body');
    modalBody.append($(
    '<div>' +
         '<div id="alert" class="alert alert-warning">' +
            '<a href="#" class="close" data-dismiss="alert"></a>' +
            '<div id ="alertText"></div>' +
         '</div>' +
         '<form id="ipForm">' +
             '<div class="form-group">' +
                 '<label for="rangeStart">Range start</label>' +
                 '</br><input id="rangeStart" type="text" value="127.0.0.1" required/></input>' +
             '</div>' +
              '<div class="form-group">' +
                  '<label for="rangeEnd">Range end</label>' +
                  '</br><input id="rangeEnd" type="text" value="127.0.0.1" required/></input>' +
              '</div>' +
         '</form>' +
     '</div>'));
     $('#ipForm').append('<button id="submit" type="submit" class="btn btn-primary">Submit</button>');
}

function loadRemoveIpModal() {
    var modalBody = $('.modal-body');
    modalBody.append($(
    '<div id="ipForm">' +
         '<div id="alert" class="alert alert-warning">' +
            '<a href="#" class="close" data-dismiss="alert"></a>' +
            '<div id ="alertText"></div>' +
         '</div>' +
         '<form><button id="delete" type="submit" class="btn btn-primary">Confirm</button></form>' +
     '</div>'));
}

function loadRemoveUserModal() {
    var modalBody = $('.modal-body');
    modalBody.append($(
    '<div id="userForm">' +
        '<div id="alert" class="alert alert-warning">' +
           '<a href="#" class="close" data-dismiss="alert"></a>' +
           '<div id ="alertText"></div>' +
        '</div>' +
        '<form><button id="delete" type="submit" class="btn btn-primary">Confirm</button></form>' +
    '</div>'));
}

function userCreateSuccess(json) {
    var date = new Date();
    $('#myModal .close').click();
    $('#consoleText').append('</br>');
    $('#consoleText').append(date.toLocaleString());
    $('#consoleText').append(': User with username: ' +json.username+ ' created.');
    getCall("/admin/user", "json", loadUsers);
}

function userCreateFail(json) {
    $('#alertText').empty();
    $('#alertText').append(json.responseJSON.message);
    $(".modal-body").find("button").attr('disabled', false);
}

function userUpdateSuccess(json) {
    prepareConsole();
    $('#consoleText').append(': User with username: ' +json.username+ ' edited.');
    getCall("/admin/user", "json", loadUsers);
}

function userDeleteSuccess(json) {
    prepareConsole();
    $('#consoleText').append(": " + json.message);
    $('#removeUserButton').attr('disabled', true);
    $('#removeUserButton').removeAttr('data-target');
    getCall("/admin/user", "json", loadUsers);
}

function passwordResetSuccess(json) {
    prepareConsole();
    $('#consoleText').append(': Password reset for user with username: ' +json.username+ ' successful.');
}

function studentCreateSuccess(json) {
    prepareConsole();
    $('#consoleText').append(': Student with university id: ' +json.universityId+ ' created. PIN: ' + json.pin);
    getCall("/admin/student", "json", loadStudents);
}

function studentUpdateSuccess(json) {
    prepareConsole();
    $('#consoleText').append(': Student with university id: ' +json.universityId+ ' updated.');
    getCall("/admin/student", "json", loadStudents);
}

function studentDeleteSuccess(json) {
    prepareConsole();
    $('#removeStudentButton').attr('disabled', true);
    $('#removeStudentButton').removeAttr('data-target');
    $('#consoleText').append(": " + json.message);
    getCall("/admin/student", "json", loadStudents);
}

function pinResetSuccess(json) {
    prepareConsole();
    $('#consoleText').append(': Pin reset for student with id: ' +json.universityId+ ' successful. PIN: '+ json.pin);
    getCall("/admin/student", "json", loadStudents);
}

function moduleCreateSuccess(json) {
    prepareConsole();
    $('#consoleText').append(': Module with code: ' +json.moduleCode+ ' created.');
    getCall("/admin/module", "json", loadModules);
}

function moduleUpdateSuccess(json) {
    prepareConsole();
    $('#consoleText').append(': Module with code: ' +json.moduleCode+ ' updated.');
    getCall("/admin/module", "json", loadModules);
}

function moduleRemoveSuccess(json) {
    prepareConsole();
    $('#consoleText').append(": " + json.message);
    $('#studentsAssignedToModulePills').empty();
    getCall("/admin/module", "json", loadModules);
    $('#removeModuleButton').attr('disabled', true);
    $('#removeModuleButton').removeAttr('data-target');
    $('#addStudentToModuleButton').attr('disabled', true);
    $('#addStudentToModuleButton').removeAttr('data-target');
    $('#removeStudentFromModuleButton').attr('disabled', true);
    $('#removeStudentFromModuleButton').removeAttr('data-target');
}

function addStudentToModuleSuccess(json) {
    var moduleCode = $('#modulesPills .active').attr("moduleCode");
    prepareConsole();
    $('#consoleText').append(": " + json.message);
    getCall("admin/module/"+moduleCode+"/student", "json", loadModuleStudents);
}

function removeStudentFromModuleSuccess(json) {
    var moduleCode = $('#modulesPills .active').attr("moduleCode");
    prepareConsole();
    $('#consoleText').append(": " + json.message);
    getCall("admin/module/"+moduleCode+"/student", "json", loadModuleStudents);
    $('#removeStudentFromModuleButton').attr('disabled', true);
    $('#removeStudentFromModuleButton').removeAttr('data-target');
}

function moduleDeleteSuccess(json) {
    prepareConsole();
    $('#consoleText').append(": " + json.message);
    $('#studentsAssignedToModulePills').empty();
    $('#moduleInfo').empty();
    $('#removeModuleButton').attr('disabled', true);
    $('#removeModuleButton').removeAttr('data-target');
    $('#addStudentToModuleButton').attr('disabled', true);
    $('#removeStudentFromModuleButton').attr('disabled', true);
    $('#removeStudentFromModuleButton').removeAttr('data-target');
    getCall("/admin/module", "json", loadModules);
}

function classCreateSuccess(json) {
    prepareConsole();
    $('#consoleText').append(': Class with uuid: ' +json.uuid+ ' created.');
    getCall("/admin/module/"+json.moduleCode+"/class", "json", loadClasses);
}

function deleteClassSuccess(json) {
    prepareConsole();
    $('#consoleText').append(": " + json.message);
    $('#removeClassButton').attr('disabled', true);
    $('#removeClassButton').removeAttr('data-target');
    $('#studentsAssignedToClassPills').empty();
    var moduleCode = $('#modulesDropDownList option:selected').val();
    getCall("/admin/module/"+moduleCode+"/class", "json", loadClasses);
}

function ipCreateSuccess(json) {
    prepareConsole();
    $('#consoleText').append(': Ip range with uuid: ' +json.uuid+ ' created.');
    getCall("/admin/ip", "json", loadIps);
}

function ipRemoveSuccess(json) {
    prepareConsole();
    $('#consoleText').append(': Ip range removed.');
    getCall("/admin/ip", "json", loadIps);
    $('#removeIpButton').attr('disabled', true);
    $('#removeIpButton').removeAttr('data-target');
}

function prepareConsole() {
    var date = new Date();
    $('#myModal .close').click();
    $('#consoleText').append('</br>');
    $('#consoleText').append(date.toLocaleString());
}

function loadUsers(json) {
    $('#usersPills').empty();
    $('#userInfo').empty();

    for(var i = 0; i < json.length; i++) {
        var uuid = JSON.stringify(json[i].uuid).replace(/"/g, '');
        var username = JSON.stringify(json[i].username).replace(/"/g, '');
        var name = JSON.stringify(json[i].name).replace(/"/g, '');
        var surname = JSON.stringify(json[i].surname).replace(/"/g, '');
        var role = JSON.stringify(json[i].role).replace(/"/g, '');
        var email = JSON.stringify(json[i].email).replace(/"/g, '');

        var href = $('<a href="#"></a>')
        var item = $('<li role="presentation" user_uuid="'+uuid+'" username="'+username+'" name="'+name+'" surname="'+surname+'" userType="'+role+'" email="'+email+'"></li>').appendTo($('#usersPills'));

        href.append(username);
        item.append(href);
    }
}

function loadStudents(json) {
        $('#studentsPills').empty();
        $('#studentsInfo').empty();

        for(var i = 0; i < json.length; i++) {
            var studentId = JSON.stringify(json[i].universityId).replace(/"/g, '');
            var name = JSON.stringify(json[i].name).replace(/"/g, '');
            var surname = JSON.stringify(json[i].surname).replace(/"/g, '');
            var email = JSON.stringify(json[i].email).replace(/"/g, '');

            var href = $('<a href="#"></a>');
            var item = $('<li role="presentation" universityId="'+studentId+'" name="'+name+'" surname="'+surname+'" email="'+email+'"></li>').appendTo($('#studentsPills'));

            href.append(name + " " + surname);
            item.append(href);
        }
}

function loadLecturersForModuleCreate(json) {
            var form = $('<div class="form-group"><label for="lecturer">Lecturer : </label></div>').appendTo($('#moduleForm'));
            var lecturers = $('<select class="custom-select mb-2 mr-sm-2 mb-sm-0" id="lecturer"></select>').appendTo(form);

            for(var i = 0; i < json.length; i++) {
                var uuid = JSON.stringify(json[i].uuid).replace(/"/g, '');
                var name = JSON.stringify(json[i].name).replace(/"/g, '');
                var surname = JSON.stringify(json[i].surname).replace(/"/g, '');

                lecturers.append('<option value="'+uuid+'">'+name+' '+surname+'</option>');
            }
}

function loadModuleGroupsForModule(json) {
            var form = $('<div class="form-group"><label for="group">Group : </label></div>').appendTo($('#moduleForm'));
            var group = $('<select class="custom-select mb-2 mr-sm-2 mb-sm-0" id="groupSelect"></select>').appendTo(form);

            for(var i = 0; i < json.length; i++) {
                var moduleGroup = JSON.stringify(json[i].moduleGroup).replace(/"/g, '');
                group.append('<option value="'+moduleGroup+'">'+moduleGroup+'</option>');
            }
}

function loadStudentsForModuleAdd(json) {
            var form = $('<div class="form-group"><label for="student">Student : </label></div>').appendTo($('#moduleForm'));
            var student = $('<select class="custom-select mb-2 mr-sm-2 mb-sm-0" id="studentSelect"></select>').appendTo(form);

            for(var i = 0; i < json.length; i++) {
                var studentId = JSON.stringify(json[i].universityId).replace(/"/g, '');
                var name = JSON.stringify(json[i].name).replace(/"/g, '');
                var surname = JSON.stringify(json[i].surname).replace(/"/g, '');
                student.append('<option value="'+studentId+'">'+studentId+' '+name+' '+surname+'</option>');
            }
}

function loadStudentsForModuleCreate(json) {
            var form = $('<div class="form-group"><label for="students">Students</label></div>').appendTo($('#moduleForm'));
            var students = $('<div id="studentsList"></div>').appendTo(form);
            for(var i = 0; i < json.length; i++) {
                var studentId = JSON.stringify(json[i].universityId).replace(/"/g, '');
                var name = JSON.stringify(json[i].name).replace(/"/g, '');
                var surname = JSON.stringify(json[i].surname).replace(/"/g, '');

                students.append('<label class="checkbox" style="margin-left:20px;"><input type="checkbox" value="'+studentId+'">'+studentId+' '+name+' '+surname+'</label>');
            }
}

function loadStudentsForClassCreate(json) {
            var form = $('#studentFormGroup');
            form.empty();
            var students = $('<div id="studentsList"></div>').appendTo(form);
            var locked = json.locked;
            for(var i = 0; i < json.students.length; i++) {
                var studentId = JSON.stringify(json.students[i].universityId).replace(/"/g, '');
                var name = JSON.stringify(json.students[i].name).replace(/"/g, '');
                var surname = JSON.stringify(json.students[i].surname).replace(/"/g, '');

                students.append('<label class="checkbox" style="margin-left:20px;"><input class="check" type="checkbox" value="'+studentId+'">'+studentId+' '+name+' '+surname+'</label>');
            }
            if(locked == true)
                $('#studentsList .check').attr('disabled', 'disabled');
            else
                $('#studentsList .check').removeAttr('disabled');
            students.attr("locked", locked);
}

function loadModules(json) {
            $('#modulesPills').empty();
            $('#moduleInfo').empty();

            for(var i = 0; i < json.length; i++) {
                var moduleCode = JSON.stringify(json[i].moduleCode).replace(/"/g, '');
                var title = JSON.stringify(json[i].title).replace(/"/g, '');
                var lecturerUuid = JSON.stringify(json[i].lecturerUuid).replace(/"/g, '');

                var href = $('<a href="#"></a>');
                var item = $('<li role="presentation" moduleCode="'+moduleCode+'" title="'+title+'" lecturerUuid="'+lecturerUuid+'"></li>').appendTo($('#modulesPills'));

                href.append(moduleCode);
                item.append(href);
            }
}

function loadModuleStudents(json) {
            $('#studentsAssignedToModulePills').empty();

            for(var i = 0; i < json.length; i++) {
                var studentId = JSON.stringify(json[i].universityId).replace(/"/g, '');
                var name = JSON.stringify(json[i].name).replace(/"/g, '');
                var surname = JSON.stringify(json[i].surname).replace(/"/g, '');
                var email = JSON.stringify(json[i].email).replace(/"/g, '');

                var href = $('<a href="#"></a>');
                var item = $('<li role="presentation" universityId="'+studentId+'" name="'+name+'" surname="'+surname+'" email="'+email+'"></li>').appendTo($('#studentsAssignedToModulePills'));

                href.append(name + " " + surname);
                item.append(href);
            }
}

function loadClassStudents(json) {
    $('#studentsAssignedToClassPills').empty();
    for(var i = 0; i < json.length; i++) {
        var studentId = JSON.stringify(json[i].universityId).replace(/"/g, '');
        var name = JSON.stringify(json[i].name).replace(/"/g, '');
        var surname = JSON.stringify(json[i].surname).replace(/"/g, '');
        var email = JSON.stringify(json[i].email).replace(/"/g, '');

        var href = $('<a href="#"></a>');
        var item = $('<li role="presentation" universityId="'+studentId+'" name="'+name+'" surname="'+surname+'" email="'+email+'"></li>').appendTo($('#studentsAssignedToClassPills'));

        href.append(name + " " + surname);
        item.append(href);
    }
}

function loadModulesDropdown(json) {
            $('#modulesDropDown').empty();
            var dropdown = $('<select class="custom-select mb-2 mr-sm-2 mb-sm-0" id="modulesDropDownList"></select>').appendTo($('#modulesDropDown'));
            dropdown.append('<option value="null">Select module</option>');
            for(var i = 0; i < json.length; i++) {
                var moduleCode = JSON.stringify(json[i].moduleCode).replace(/"/g, '');
                var li = $('<option value="'+moduleCode+'">'+moduleCode+'</option>').appendTo(dropdown);
            }
}

function loadClasses(json) {
    $('#classesPills').empty();
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
                                         + 'Group: ' + JSON.stringify(json[i].group).replace(/"/g, '')
                                         + '</br>'
                                         + JSON.stringify(json[i].room).replace(/"/g, '')
                                         + '</a></li>').appendTo($('#classesPills'));
    }
}

function loadLecturer(json) {
    var name = JSON.stringify(json.name).replace(/"/g, '');
    var surname = JSON.stringify(json.surname).replace(/"/g, '');
    $('#lecturerInfo').empty();
    $('#lecturerInfo').append(name+" "+surname);
}

function loadIps(json) {
    $('#ipPills').empty();
    for(var i = 0; i < json.length; i++) {
        var uuid = JSON.stringify(json[i].uuid).replace(/"/g, '');
        var start = JSON.stringify(json[i].ipRangeStart).replace(/"/g, '');
        var end = JSON.stringify(json[i].ipRangeEnd).replace(/"/g, '');

        var href = $('<a href="#"></a>');
        var item = $('<li role="presentation" uuid="'+uuid+'" start="'+start+'" end="'+end+'"></li>').appendTo($('#ipPills'));

        href.append(start + " - " + end);
        item.append(href);
    }
}