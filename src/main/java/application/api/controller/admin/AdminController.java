package application.api.controller.admin;

import application.api.resource.message.MessageResource;
import application.api.resource.scheduledClass.ScheduledClassResource;
import application.api.resource.student.CreatedStudentResource;
import application.api.resource.student.StudentResource;
import application.api.resource.user.PasswordResource;
import application.api.resource.user.UserResource;
import application.domain.student.Student;
import application.domain.user.User;
import application.service.module.ModuleService;
import application.service.scheduledClass.ScheduledClassService;
import application.service.student.StudentService;
import application.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ScheduledClassService scheduledClassService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModuleService moduleService;

    @RequestMapping("/admin")
    public String admin() {
        return "adminPage";
    }

    @RequestMapping(value = "admin/class/{universityId}/{moduleCode}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ScheduledClassResource>> getModules(@PathVariable String universityId,
                                                                   @PathVariable String moduleCode) {
        List<ScheduledClassResource> classes = new ArrayList<>();
        scheduledClassService.findClassesByStudentUniveristyIdAndModuleCode(universityId, moduleCode).forEach(scheduledClass ->
                classes.add(new ScheduledClassResource(scheduledClass)));
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @RequestMapping(value = "admin/student", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<StudentResource>> getStudents() {
        List<StudentResource> students = new ArrayList<>();
        studentService.findAll().forEach(student -> students.add(new StudentResource(student)));
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @RequestMapping(value = "admin/user", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<UserResource>> getUsers() {
        return new ResponseEntity<List<UserResource>>(userService.getUsers()
                                                                 .stream()
                                                                 .map(user -> new UserResource(user))
                                                                 .collect(Collectors.toList()), HttpStatus.OK);
    }

    @RequestMapping(value = "admin/user", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createUser(@RequestBody UserResource resource) {
        ResponseEntity validation = validateUserCreateResource(resource);
        if(validation != null)
            return validation;
        if(userService.getUserByUsername(resource.getUsername()) != null)
            return new ResponseEntity<>(new MessageResource("User with username: "+resource.getUsername()+" already exists"), HttpStatus.FORBIDDEN);

        User user = userService.save(resource.getObject());
        if(user != null)
            return new ResponseEntity<>(new UserResource(user), HttpStatus.CREATED);
        else
            return new ResponseEntity<>(new MessageResource("Something went wrong, please try again."), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "admin/user/{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity updateUserDetails(@RequestBody UserResource resource,
                                            @PathVariable String uuid) {
        ResponseEntity validation = validateUserEditResource(resource);
        User user = userService.findByUUID(uuid);
        if(validation != null)
            return validation;
        if(user == null)
            return new ResponseEntity<>(new MessageResource("User with uuid: "+uuid+" does not exist"), HttpStatus.NOT_FOUND);
        if(!resource.getUsername().equals(user.getUsername())) {
            if(userService.getUserByUsername(resource.getUsername()) != null)
                return new ResponseEntity<>(new MessageResource("User with username: "+resource.getUsername()+" already exists"), HttpStatus.FORBIDDEN);
        }
        resource.setUuid(uuid);
        user = userService.updateUserDetails(resource.getObject());
        if(user != null)
            return new ResponseEntity<>(new UserResource(user), HttpStatus.OK);
        else
            return new ResponseEntity<>(new MessageResource("Something went wrong, please try again."), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "admin/user/{uuid}/password", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity resetUserPassword(@RequestBody PasswordResource resource,
                                          @PathVariable String uuid) {
        User user = userService.findByUUID(uuid);
        if(resource.getPassword() == null)
            return new ResponseEntity<>(new MessageResource("Password cannot be null."), HttpStatus.FORBIDDEN);
        if(user == null)
            return new ResponseEntity<>(new MessageResource("User does not exist"), HttpStatus.FORBIDDEN);
        if(userService.resetUserPassword(uuid, resource.getPassword()) == false)
            return new ResponseEntity<>(new MessageResource("Something went wrong, please try again."), HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(new UserResource(user), HttpStatus.OK);
    }

    @RequestMapping(value = "admin/user/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteUser(@PathVariable String uuid) {
        if(userService.findByUUID(uuid) == null)
            return new ResponseEntity<>(new MessageResource("User does not exist"), HttpStatus.FORBIDDEN);
        return null;
    }

    @RequestMapping(value = "admin/student", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createStudent(@RequestBody StudentResource resource) {
        ResponseEntity validation = validateStudentResource(resource);
        if(validation != null)
            return validation;
        if (studentService.getStudentByUniversityId(resource.getUniversityId()) != null)
            return new ResponseEntity<>(new MessageResource("Student with id: "+resource.getUniversityId()+" already exists"), HttpStatus.FORBIDDEN);
        Student student = studentService.save(resource.getObject());
        if(student != null)
            return new ResponseEntity<>(new CreatedStudentResource(student), HttpStatus.CREATED);
        else
            return new ResponseEntity<>(new MessageResource("Something went wrong, please try again."), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "admin/student/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity updateStudentDetails(@RequestBody StudentResource resource,
                                               @PathVariable String id) {
        ResponseEntity validation = validateStudentResource(resource);
        if(validation != null)
            return validation;
        if (studentService.getStudentByUniversityId(id) == null)
            return new ResponseEntity<>(new MessageResource("Student with id: "+id+" does not exist."), HttpStatus.NOT_FOUND);
        resource.setUniversityId(id);
        Student student = studentService.updateStudentDetails(resource.getObject());
        if(student != null)
            return new ResponseEntity<>(new CreatedStudentResource(student), HttpStatus.OK);
        else
            return new ResponseEntity<>(new MessageResource("Something went wrong, please try again."), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "admin/student/{id}/pin/reset", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity resetStudentPin(@PathVariable String id) {
        Student student = studentService.getStudentByUniversityId(id);
        if(student == null)
            return new ResponseEntity<>(new MessageResource("Student does not exist"), HttpStatus.FORBIDDEN);
        String pin = studentService.resetUserPin(id);
        if(pin == null)
            return new ResponseEntity<>(new MessageResource("Something went wrong, please try again."), HttpStatus.ACCEPTED);
        else {
            student.setPin(pin);
            return new ResponseEntity<>(new CreatedStudentResource(student), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "admin/student/{universityId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteStudent(@PathVariable String universityId) {
        return null;
    }

//    @RequestMapping(value = "admin/module", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseEntity createModule(@RequestBody ModuleToCreateResource resource) {
//
//        if(moduleService.getByModuleCode(resource.getModuleCode()) != null)
//            return new ResponseEntity(new MessageResource("Module with id: " +resource.getModuleCode()+ " exists already."), HttpStatus.FORBIDDEN);
//
//        if(userService.findByUUID(resource.getLecturerUuid()) == null)
//            return new ResponseEntity(new MessageResource("Provided lecturer does not exist"), HttpStatus.FORBIDDEN);
//
//        List<String> studentIds = studentService.findAll().stream().map(student -> student.getUniversityId()).collect(Collectors.toList());
//        List<String> students = resource.getStudentIds();
//
//        if(students.size() == 0)
//            return new ResponseEntity<>(new MessageResource("A module cannot be created without assigned students"), HttpStatus.FORBIDDEN);
//
//        for(int i = 0; i < students.size();  i++) {
//            if(!studentIds.contains(students.get(i)))
//                    return new ResponseEntity<>(new MessageResource("A student with id: " +students.get(i)+ " does not exist."), HttpStatus.FORBIDDEN);
//        }
//        return new ResponseEntity<>(new ModuleResource(moduleService.saveWithStudentsAllocated(resource.getObject(), students)), HttpStatus.CREATED);
//    }

    public ResponseEntity createClass() {
        return null;
    }

    private ResponseEntity validateUserCreateResource(UserResource resource) {
        if(resource.getUsername() == null || resource.getUsername().isEmpty())
            return new ResponseEntity(new MessageResource("Missing username!"), HttpStatus.FORBIDDEN);
        if(resource.getName() == null || resource.getName().isEmpty())
            return new ResponseEntity(new MessageResource("Missing name!"), HttpStatus.FORBIDDEN);
        if(resource.getSurname() == null || resource.getSurname().isEmpty())
            return new ResponseEntity(new MessageResource("Missing surname!"), HttpStatus.FORBIDDEN);
        if(resource.getObject().getPassword() == null || resource.getObject().getPassword().isEmpty())
            return new ResponseEntity(new MessageResource("Missing password!"), HttpStatus.FORBIDDEN);

        if(resource.getRole() == null || resource.getRole().isEmpty())
            return new ResponseEntity(new MessageResource("Missing username!"), HttpStatus.FORBIDDEN);
        else if(!resource.getRole().equals("ROLE_ADMIN") && !resource.getRole().equals("ROLE_LECTURER")){
            return new ResponseEntity(new MessageResource("Unrecognised role!"), HttpStatus.FORBIDDEN);
        }
        return null;
    }

    private ResponseEntity validateUserEditResource(UserResource resource) {
        if(resource.getUsername() == null || resource.getUsername().isEmpty())
            return new ResponseEntity(new MessageResource("Missing username!"), HttpStatus.FORBIDDEN);
        if(resource.getName() == null || resource.getName().isEmpty())
            return new ResponseEntity(new MessageResource("Missing name!"), HttpStatus.FORBIDDEN);
        if(resource.getSurname() == null || resource.getSurname().isEmpty())
            return new ResponseEntity(new MessageResource("Missing surname!"), HttpStatus.FORBIDDEN);
        return null;
    }

    private ResponseEntity validateStudentResource(StudentResource resource) {
        if(resource.getUniversityId() == null || resource.getUniversityId().isEmpty())
            return new ResponseEntity(new MessageResource("Missing university id!"), HttpStatus.FORBIDDEN);
        if(resource.getName() == null || resource.getName().isEmpty())
            return new ResponseEntity(new MessageResource("Missing name!"), HttpStatus.FORBIDDEN);
        if(resource.getSurname() == null || resource.getSurname().isEmpty())
            return new ResponseEntity(new MessageResource("Missing surname!"), HttpStatus.FORBIDDEN);
        if(resource.getEmail() == null || resource.getEmail().isEmpty())
            return new ResponseEntity(new MessageResource("Missing email!"), HttpStatus.FORBIDDEN);
        return null;
    }
}
