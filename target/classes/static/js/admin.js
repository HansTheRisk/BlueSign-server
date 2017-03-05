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
            getCall("/admin/user", "json", loadUsers);
        }
        else if(attribute == "#students") {
            getCall("/admin/student", "json", loadStudents);
        }
        else if(attribute == "#modules") {
            getCall("/admin/module", "json", loadModules);
        }
        else if(attribute == "#classes") {
            getCall("/admin/module", "json", loadModulesDropdown);
        }
        else if(attribute == "#ip") {
            getCall("/admin/ip", "json", loadIps);
        }
        $(attribute).show();
    })
});

$(document).ready(function(){
	$(document).on("click", "#usersPills li", function(e) {
        e.preventDefault();
        $("#usersPills").children('li').removeClass('active');
        $(this).addClass('active');

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
        getCall("admin/module/"+moduleCode+"/students", "json", loadModuleStudents)
	})
});

$(document).ready(function(){
	$(document).on("click", "#modulesDropDown li a", function(e) {
        $(".dropdown-toggle:first-child").text($(this).text());
        $(".dropdown-toggle:first-child").val($(this).text());
        var code = $(this).attr("code");
        getCall("/admin/module/"+code+"/classes", "json", loadClasses);
	})
});

$(document).ready(function(){
    $(document).on("submit", "#userForm", function(e) {
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
            }
            else {
                e.preventDefault();
                var requestData = JSON.stringify({ "password":passwordOne});
                postCall("admin/user/"+uuid+"/password", "json", requestData, passwordResetSuccess, userCreateFail);
            }
        }
    })
});

$(document).ready(function(){
    $(document).on("submit", "#studentForm", function(e) {
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
    })
});

$(document).ready(function(){
    $(document).on("submit", "#moduleForm", function(e) {
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
            }
            else {
                    e.preventDefault();
                    var requestData = JSON.stringify({ "moduleCode":moduleCode, "title":title, "lecturerUuid":lecturerUuid, "studentIds":studentIds});
                    postCall("admin/module", "json", requestData, moduleCreateSuccess, userCreateFail);
            }
        }
        else if(id=="update") {
            e.preventDefault();
            var requestData = JSON.stringify({ "universityId":selectedUser.attr("universityId"), "name":name, "surname":surname, "email":email});
            putCall("admin/student/"+selectedUser.attr("universityId"), "json", requestData, studentUpdateSuccess, userCreateFail);
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
        else if(type == "resetPassword") {
            loadResetPasswordModal();
        }
        else if(type == "addStudent") {
            loadAddStudentModal();
        }
        else if(type == "editStudent") {
            loadEditStudentModal();
        }
        else if(type == "resetPin") {
            loadPinResetModal();
        }
        else if(type == "addModule") {
            loadAddModuleModal();
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

function loadPinResetModal() {
    var modalBody = $('.modal-body');
    modalBody.append($(
    '<div id="studentForm">' +
         '<div id="alert" class="alert alert-warning">' +
            '<a href="#" class="close" data-dismiss="alert"></a>' +
            '<div id ="alertText"></div>' +
         '</div>' +
         '<form><button id="pinreset" type="submit" class="btn btn-primary">Confirm</button></form>' + +
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
}

function userUpdateSuccess(json) {
    var date = new Date();
    $('#myModal .close').click();
    $('#consoleText').append('</br>');
    $('#consoleText').append(date.toLocaleString());
    $('#consoleText').append(': User with username: ' +json.username+ ' edited.');
    getCall("/admin/user", "json", loadUsers);
}

function passwordResetSuccess(json) {
    var date = new Date();
    $('#myModal .close').click();
    $('#consoleText').append('</br>');
    $('#consoleText').append(date.toLocaleString());
    $('#consoleText').append(': Password reset for user with username: ' +json.username+ ' successful.');
    getCall("/admin/user", "json", loadUsers);
}

function studentCreateSuccess(json) {
    var date = new Date();
    $('#myModal .close').click();
    $('#consoleText').append('</br>');
    $('#consoleText').append(date.toLocaleString());
    $('#consoleText').append(': Student with university id: ' +json.universityId+ ' created. PIN: ' + json.pin);
    getCall("/admin/student", "json", loadStudents);
}

function studentUpdateSuccess(json) {
    var date = new Date();
    $('#myModal .close').click();
    $('#consoleText').append('</br>');
    $('#consoleText').append(date.toLocaleString());
    $('#consoleText').append(': Student with university id: ' +json.universityId+ ' updated.');
    getCall("/admin/student", "json", loadStudents);
}

function pinResetSuccess(json) {
    var date = new Date();
    $('#myModal .close').click();
    $('#consoleText').append('</br>');
    $('#consoleText').append(date.toLocaleString());
    $('#consoleText').append(': Pin reset for student with id: ' +json.universityId+ ' successful. PIN: '+ json.pin);
    getCall("/admin/student", "json", loadStudents);
}

function moduleCreateSuccess(json) {
    var date = new Date();
    $('#myModal .close').click();
    $('#consoleText').append('</br>');
    $('#consoleText').append(date.toLocaleString());
    $('#consoleText').append(': Module with code: ' +json.moduleCode+ ' created.');
    getCall("/admin/module", "json", loadModules);
}

function removeUser(uuid) {

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

function loadModulesDropdown(json) {
            $('#modulesDropDown').empty();
            var div = $('<div class="dropdown"><button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">Modules<span class="caret"></span></button></div>').appendTo($('#modulesDropDown'));
            var dropdown = $('<ul class="dropdown-menu" aria-labelledby="dropdownMenu1"></il>').appendTo(div);
            for(var i = 0; i < json.length; i++) {
                var moduleCode = JSON.stringify(json[i].moduleCode).replace(/"/g, '');
                var li = $('<li><a code="'+moduleCode+'">'+moduleCode+'</a></li>').appendTo(dropdown);
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