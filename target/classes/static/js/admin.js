$(document).ready(function(){
    $(document).on("click", "#navTabs li a", function(e) {
        e.preventDefault();
        var attribute = $(this).attr("href");

        $("#users").hide();
        $("#students").hide();
        $("#modules").hide();
        $("#classes").hide();
        $("#ip").hide();
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

        var info = $('#info');
        info.empty();

        info.append('<h4>'+name+" "+surname+'<h4>');
        info.append('<em>'+uuid+'<em></br>');
        info.append('<p><table cellspacing="2"><tr><th></th><th></th></tr><tr><td>Name: </td><td>'+name+'</td></tr><tr><td>Surname: </td><td>'+surname+'</td></tr><tr><td>Username: </td><td>'+username+'</td></tr><tr><td>Email: </td><td>'+email+'</td></tr><tr><td>Role: </td><td>'+role+'</td></tr></table><p>');
        info.append('<p><a class="btn btn-primary" callType="editUser" data-toggle="modal" data-target="#myModal" role="button">Edit</a></p>');
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
    })
});

//$(document).ready(function(){
//    $(document).on("click", "#update", function(e) {
//        var selectedUser = $('#usersPills .active');
//        var uuid = selectedUser.attr("user_uuid");
//        var username = $('#username').val();
//        var name = $('#name').val();
//        var surname = $('#surname').val();
//        var email = $('#email').val();
//
//        e.preventDefault();
//        var requestData = JSON.stringify({ "username":username, "name":name, "surname":surname, "email":email});
//        putCall("admin/user/"+uuid, "json", requestData, userUpdateSuccess, userCreateFail);
//    })
//});

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

function removeUser(uuid) {

}

function loadUsers(json) {
    $('#usersPills').empty();
    $('#info').empty();

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