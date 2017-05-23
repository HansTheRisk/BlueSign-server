package application.api.controller.admin;

import application.api.controller.lecturer.LecturerController;
import application.api.resource.ip.IpRangeResource;
import application.api.resource.message.MessageResource;
import application.api.resource.module.ModuleGroupResource;
import application.api.resource.module.ModuleGroupStudentsResource;
import application.api.resource.module.ModuleResource;
import application.api.resource.module.ModuleToCreateResource;
import application.api.resource.scheduledClass.ScheduledClassResource;
import application.api.resource.scheduledClass.ScheduledClassToCreateResource;
import application.api.resource.student.CreatedStudentResource;
import application.api.resource.student.StudentResource;
import application.api.resource.user.PasswordResource;
import application.api.resource.user.UserResource;
import application.domain.ipRange.IpRange;
import application.domain.scheduledClass.ScheduledClass;
import application.domain.student.Student;
import application.domain.user.User;
import application.service.ipRange.IpRangeService;
import application.service.module.ModuleService;
import application.service.scheduledClass.ScheduledClassService;
import application.service.student.StudentService;
import application.service.user.UserService;
import application.util.ip.IpUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private LecturerController lecturerController;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ScheduledClassService scheduledClassService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private IpRangeService ipRangeService;

    @RequestMapping("/admin")
    public String admin() {
        return "adminPage";
    }

    @RequestMapping(value = "admin/student", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<StudentResource>> getStudents() {
        List<StudentResource> students = new ArrayList<>();
        studentService.findAll().forEach(student -> students.add(new StudentResource(student)));
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @RequestMapping(value = "admin/student", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createStudent(@RequestBody StudentResource resource) {
        ResponseEntity validation = validateStudentResource(resource);
        if (validation != null)
            return validation;
        if (studentService.getStudentByUniversityId(resource.getUniversityId()) != null)
            return new ResponseEntity<>(new MessageResource("Student with id: " + resource.getUniversityId() + " already exists"), HttpStatus.FORBIDDEN);
        Student student = studentService.save(resource.getObject());
        if (student != null)
            return new ResponseEntity<>(new CreatedStudentResource(student), HttpStatus.CREATED);
        else
            return new ResponseEntity<>(new MessageResource("Something went wrong, please try again."), HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "admin/student/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity updateStudentDetails(@RequestBody StudentResource resource,
                                               @PathVariable String id) {
        ResponseEntity validation = validateStudentResource(resource);
        if (validation != null)
            return validation;
        if (studentService.getStudentByUniversityId(id) == null)
            return new ResponseEntity<>(new MessageResource("Student with id: " + id + " does not exist."), HttpStatus.NOT_FOUND);
        resource.setUniversityId(id);
        Student student = studentService.updateStudentDetails(resource.getObject());
        if (student != null)
            return new ResponseEntity<>(new CreatedStudentResource(student), HttpStatus.OK);
        else
            return new ResponseEntity<>(new MessageResource("Something went wrong, please try again."), HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "admin/student/{universityId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteStudent(@PathVariable String universityId) {
        if(universityId.length() > 9)
            return new ResponseEntity<>(new MessageResource("Invalid student id!"), HttpStatus.FORBIDDEN);
        if (studentService.getStudentByUniversityId(universityId) == null)
            return new ResponseEntity<>(new MessageResource("Student does not exist"), HttpStatus.NOT_FOUND);
        if (studentService.removeStudent(universityId))
            return new ResponseEntity<>(new MessageResource("Student with id: "+universityId+" removed."), HttpStatus.OK);
        else
            return new ResponseEntity(new MessageResource("Something went wrong!"), HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "admin/module/{moduleCode}/studentAvailable", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<StudentResource>> getStudentsAvailableToAddToModule(@PathVariable String moduleCode) {
        List<StudentResource> students = new ArrayList<>();
        studentService.getStudentAvailableToAllocateToModule(moduleCode).forEach(student -> students.add(new StudentResource(student)));
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @RequestMapping(value = "admin/module/{moduleCode}/{group}/student", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity addAStudentToModule(@PathVariable String moduleCode,
                                              @PathVariable String group,
                                              @RequestBody StudentResource resource) {

        List<String> moduleGroups = new ArrayList<>();
        ResponseEntity validation;

        if (moduleService.getByModuleCode(moduleCode) == null)
            return new ResponseEntity(new MessageResource("Module with id: " + moduleCode + " does not exist."), HttpStatus.NOT_FOUND);

        validation = validateStudentResourceToAddToModule(resource);

        if(validation != null)
            return validation;

        moduleGroups = moduleService.getModuleGroups(moduleCode).stream().map(code -> code.getGroup()).collect(Collectors.toList());

        List<String> studentIds = studentService.getStudentAvailableToAllocateToModule(moduleCode)
                .stream().map(student -> student.getUniversityId()).collect(Collectors.toList());

        if (!studentIds.contains(resource.getUniversityId()))
             return new ResponseEntity<>(new MessageResource("Cannot allocate student with id: " + resource.getUniversityId()), HttpStatus.FORBIDDEN);

        if(moduleGroups.contains("NONE")) {
            if (!moduleGroups.contains(group.toUpperCase()))
                return new ResponseEntity<>(new MessageResource("Unknown group!"), HttpStatus.NOT_FOUND);
            if (moduleService.addAStudentToModuleAndAGroup(moduleCode, resource.getUniversityId(), group))
                return new ResponseEntity<>(new MessageResource("Student with id: "+resource.getUniversityId()+" allocated to module " +moduleCode+" ."), HttpStatus.OK);
            else
                return new ResponseEntity<>(new MessageResource("Something went wrong, please try again."), HttpStatus.FORBIDDEN);
        }
        else {
            moduleGroups.add("MODULE_ONLY");
            if (!moduleGroups.contains(group.toUpperCase()))
                return new ResponseEntity<>(new MessageResource("Unknown group!"), HttpStatus.NOT_FOUND);
            if(group.equals("MODULE_ONLY")) {
                if (moduleService.addAStudentToModule(moduleCode, resource.getUniversityId()))
                    return new ResponseEntity<>(new MessageResource("Student with id: "+resource.getUniversityId()+" allocated to module " +moduleCode+" ."), HttpStatus.OK);
                else
                    return new ResponseEntity<>(new MessageResource("Something went wrong, please try again."), HttpStatus.FORBIDDEN);
            }
            else {
                if (moduleService.addAStudentToModuleAndAGroup(moduleCode, resource.getUniversityId(), group))
                    return new ResponseEntity<>(new MessageResource("Student with id: "+resource.getUniversityId()+" allocated to module " +moduleCode+" ."), HttpStatus.OK);
                else
                    return new ResponseEntity<>(new MessageResource("Something went wrong, please try again."), HttpStatus.FORBIDDEN);
            }

        }
    }

    @RequestMapping(value = "admin/module/{moduleCode}/student/{studentUniversityId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity removeStudentFromModule(@PathVariable String moduleCode,
                                                  @PathVariable String studentUniversityId) {
        if (moduleService.getByModuleCode(moduleCode) == null)
            return new ResponseEntity(new MessageResource("Module with id: " + moduleCode + " does not exist."), HttpStatus.NOT_FOUND);

        List<String> studentIds = studentService.getStudentsAllocatedToAModule(moduleCode)
                .stream().map(student -> student.getUniversityId()).collect(Collectors.toList());

        if (!studentIds.contains(studentUniversityId))
            return new ResponseEntity(new MessageResource("Student with id: " + studentUniversityId + " does not exist or is not allocated to the module"), HttpStatus.NOT_FOUND);

        if (moduleService.removeStudentFromModule(moduleCode, studentUniversityId))
            return new ResponseEntity<>(new MessageResource("Student with id: "+studentUniversityId+" removed from module: "+moduleCode), HttpStatus.OK);
        else
            return new ResponseEntity<>(new MessageResource("Something went wrong, please try again."), HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "admin/module/{moduleCode}/group", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getModulegroups(@PathVariable String moduleCode) {
        if (moduleService.getByModuleCode(moduleCode) == null)
            return new ResponseEntity(new MessageResource("Module with id: " + moduleCode + " does not exist."), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(moduleService.getModuleGroups(moduleCode).stream().map(group -> new ModuleGroupResource(group)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @RequestMapping(value = "admin/user/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getUser(@PathVariable String uuid) {
        User user = userService.getUserByUuid(uuid);
        if (user == null)
            return new ResponseEntity<>(new MessageResource("User does not exist"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new UserResource(user), HttpStatus.OK);
    }

    @RequestMapping(value = "admin/user", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<UserResource>> getUsers() {
        return new ResponseEntity<List<UserResource>>(userService.getUsers()
                .stream()
                .map(user -> new UserResource(user))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @RequestMapping(value = "admin/lecturer", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<UserResource>> getLecturers() {
        return new ResponseEntity<List<UserResource>>(userService.getLecturers()
                .stream()
                .map(user -> new UserResource(user))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @RequestMapping(value = "admin/user", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createUser(@RequestBody UserResource resource) {
        ResponseEntity validation = validateUserCreateResource(resource);
        if (validation != null)
            return validation;
        if (userService.getUserByUsername(resource.getUsername()) != null)
            return new ResponseEntity<>(new MessageResource("User with username: " + resource.getUsername() + " already exists"), HttpStatus.FORBIDDEN);

        User user = userService.save(resource.getObject());
        if (user != null)
            return new ResponseEntity<>(new UserResource(user), HttpStatus.CREATED);
        else
            return new ResponseEntity<>(new MessageResource("Something went wrong, please try again."), HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "admin/user/{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity updateUserDetails(@RequestBody UserResource resource,
                                            @PathVariable String uuid) {
        ResponseEntity validation = validateUserEditResource(resource);
        User user = userService.findByUUID(uuid);
        if (validation != null)
            return validation;
        if (user == null)
            return new ResponseEntity<>(new MessageResource("User with uuid: " + uuid + " does not exist"), HttpStatus.NOT_FOUND);
        if (!resource.getUsername().equals(user.getUsername())) {
            if (userService.getUserByUsername(resource.getUsername()) != null)
                return new ResponseEntity<>(new MessageResource("User with username: " + resource.getUsername() + " already exists"), HttpStatus.FORBIDDEN);
        }
        resource.setUuid(uuid);
        user = userService.updateUserDetails(resource.getObject());
        if (user != null)
            return new ResponseEntity<>(new UserResource(user), HttpStatus.OK);
        else
            return new ResponseEntity<>(new MessageResource("Something went wrong, please try again."), HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "admin/user/{uuid}/password", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity resetUserPassword(@RequestBody PasswordResource resource,
                                            @PathVariable String uuid) {
        User user = userService.findByUUID(uuid);
        if (resource.getPassword() == null)
            return new ResponseEntity<>(new MessageResource("Password cannot be null."), HttpStatus.FORBIDDEN);
        if (user == null)
            return new ResponseEntity<>(new MessageResource("User does not exist"), HttpStatus.NOT_FOUND);
        if (userService.resetUserPassword(uuid, resource.getPassword()) == false)
            return new ResponseEntity<>(new MessageResource("Something went wrong, please try again."), HttpStatus.FORBIDDEN);
        else
            return new ResponseEntity<>(new UserResource(user), HttpStatus.OK);
    }

    @RequestMapping(value = "admin/user/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteUser(@Autowired Authentication auth,
                                     @PathVariable String uuid) {
        if (userService.findByUUID(uuid) == null)
            return new ResponseEntity<>(new MessageResource("User does not exist"), HttpStatus.NOT_FOUND);
        if (((User) auth.getPrincipal()).getUuid().equals(uuid))
            return new ResponseEntity<>(new MessageResource("Cannot delete your own account!"), HttpStatus.FORBIDDEN);
        if (userService.removeUser(uuid))
            return new ResponseEntity<>(new MessageResource("User with uuid " +uuid+ " deleted."), HttpStatus.OK);
        else
            return new ResponseEntity(new MessageResource("Something went wrong!"), HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "admin/student/{id}/pin/reset", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity resetStudentPin(@PathVariable String id) {
        Student student = studentService.getStudentByUniversityId(id);
        if (student == null)
            return new ResponseEntity<>(new MessageResource("Student does not exist"), HttpStatus.NOT_FOUND);
        String pin = studentService.resetStudentPin(id);
        if (pin == null)
            return new ResponseEntity<>(new MessageResource("Something went wrong, please try again."), HttpStatus.FORBIDDEN);
        else {
            student.setPin(pin);
            return new ResponseEntity<>(new CreatedStudentResource(student), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "admin/module", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createModule(@RequestBody ModuleToCreateResource resource) {

        ResponseEntity validation = validateModuleToCreateResource(resource);
        if (validation != null)
            return validation;

        if (moduleService.getByModuleCode(resource.getModuleCode()) != null)
            return new ResponseEntity(new MessageResource("Module with id: " + resource.getModuleCode() + " exists already."), HttpStatus.FORBIDDEN);

        if (userService.findByUUID(resource.getLecturerUuid()) == null)
            return new ResponseEntity(new MessageResource("Provided lecturer does not exist"), HttpStatus.FORBIDDEN);

        List<String> studentIds = studentService.findAll().stream().map(student -> student.getUniversityId()).collect(Collectors.toList());
        List<String> students = resource.getStudentIds();

        for (int i = 0; i < students.size(); i++) {
            if (!studentIds.contains(students.get(i)))
                return new ResponseEntity<>(new MessageResource("A student with id: " + students.get(i) + " does not exist."), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(new ModuleResource(moduleService.saveWithStudentsAllocated(resource.getObject(), students)), HttpStatus.CREATED);
    }

    @RequestMapping(value = "admin/module/{moduleCode}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity editModule(@RequestBody ModuleResource resource,
                                     @PathVariable String moduleCode) {

        ResponseEntity validation = validateEditModuleResource(resource);
        if (validation != null)
            return validation;

        if (moduleService.getByModuleCode(moduleCode) == null)
            return new ResponseEntity(new MessageResource("Module with id: " + resource.getModuleCode() + " does not exist"), HttpStatus.NOT_FOUND);

        if (userService.findByUUID(resource.getLecturerUuid()) == null)
            return new ResponseEntity(new MessageResource("Provided lecturer does not exist"), HttpStatus.NOT_FOUND);
        resource.setModuleCode(moduleCode);
        return new ResponseEntity<>(new ModuleResource(moduleService.updateModuleDetails(resource.getObject())), HttpStatus.OK);
    }

    @RequestMapping(value = "admin/module", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ModuleResource>> getModules() {
        List<ModuleResource> modules = new ArrayList<>();
        moduleService.getModules().forEach(module -> modules.add(new ModuleResource(module)));
        return new ResponseEntity<>(modules, HttpStatus.OK);
    }


    @RequestMapping(value = "admin/module/{moduleCode}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteModule(@PathVariable String moduleCode) {
        if (moduleService.getByModuleCode(moduleCode) == null)
            return new ResponseEntity(new MessageResource("Module not found!"), HttpStatus.NOT_FOUND);
        if (moduleService.removeModule(moduleCode))
            return new ResponseEntity<>(new MessageResource("Module with code: "+moduleCode+" deleted."), HttpStatus.OK);
        else
            return new ResponseEntity(new MessageResource("Something went wrong!"), HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "admin/module/{code}/class", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ScheduledClassResource>> getModulesClasses(@PathVariable String code) {
        return lecturerController.getModulesClasses(code);
    }

    @RequestMapping(value = "admin/class/{uuid}/student", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getClassStudents(@PathVariable String uuid) {
        if (scheduledClassService.findByUUID(uuid) == null)
            return new ResponseEntity<>(new MessageResource("Class with does not exist."), HttpStatus.NOT_FOUND);
        return new ResponseEntity(studentService.getStudentsAllocatedToAClass(uuid).stream().map(stud -> new StudentResource(stud)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @RequestMapping(value = "admin/module/{moduleCode}/student", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<StudentResource>> getModuleStudents(@PathVariable String moduleCode) {
        List<StudentResource> students = new ArrayList<>();
        studentService.getStudentsAllocatedToAModule(moduleCode).forEach(student -> students.add(new StudentResource(student)));
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/module/{module}/{group}/student", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getModuleGroupStudents(@PathVariable String module,
                                                 @PathVariable String group) {

        List<Student> students;
        boolean locked = true;

        if (moduleService.getByModuleCode(module) == null)
            return new ResponseEntity<>(new MessageResource("Module with code: " + module + " does not exist."), HttpStatus.NOT_FOUND);

        if (group.toUpperCase().equals("NONE")) {
            students = studentService.getStudentsAllocatedToAModule(module);
        } else {
            students = studentService.getStudentsAllocatedToAModuleGroup(module, group);
            if (students.isEmpty()) {
                students = studentService.getStudentsAllocatedOnlyToNoneGroup(module);
                if (students.isEmpty()) {
                    students = studentService.getStudentsAllocatedToAModuleButNotToItsClasses(module);
                }
                locked = false;
            }
        }
        return new ResponseEntity<>(new ModuleGroupStudentsResource(students.stream().map(stud -> new StudentResource(stud)).collect(Collectors.toList()), locked), HttpStatus.OK);
    }

    @RequestMapping(value = "admin/class", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createClass(@RequestBody ScheduledClassToCreateResource resource) {
        ScheduledClass sclass;
        List<Student> students;
        ResponseEntity validation = validateClassToCreateResource(resource);
        if (validation != null)
            return validation;

        if (moduleService.getByModuleCode(resource.getModuleCode()) == null)
            return new ResponseEntity<>(new MessageResource("Module with code: " + resource.getModuleCode() + " does not exist."), HttpStatus.NOT_FOUND);

        if (resource.getGroup().toUpperCase().equals("NONE")) {
            sclass = scheduledClassService.saveWithStudentsAllocated(resource.getObject(), studentService.getStudentsAllocatedToAModule(resource.getModuleCode())
                    .stream()
                    .map(stud -> stud.getUniversityId())
                    .collect(Collectors.toList()));
        } else {
            students = studentService.getStudentsAllocatedToAModuleGroup(resource.getModuleCode(), resource.getGroup());
            if (students.isEmpty()) {
                validation = validateNewModuleClassGroupStudents(resource.getStudentsToAllocate(), resource.getModuleCode());
                if (validation != null)
                    return validation;
                sclass = scheduledClassService.saveWithStudentsAllocated(resource.getObject(), resource.getStudentsToAllocate());
            } else {
                sclass = scheduledClassService.saveWithStudentsAllocated(resource.getObject(), students.stream()
                        .map(stud -> stud.getUniversityId())
                        .collect(Collectors.toList()));
            }
        }
        return new ResponseEntity<>(new ScheduledClassResource(sclass), HttpStatus.CREATED);
    }

    @RequestMapping(value = "admin/class/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteClass(@PathVariable String uuid) {
        if (scheduledClassService.findByUUID(uuid) == null)
            return new ResponseEntity(new MessageResource("Class not found!"), HttpStatus.NOT_FOUND);
        if (scheduledClassService.removeClass(uuid))
            return new ResponseEntity<>(new MessageResource("Class with uuid: "+uuid+" deleted."), HttpStatus.OK);
        else
            return new ResponseEntity(new MessageResource("Something went wrong!"), HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "admin/ip", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<IpRangeResource>> getIpRanges() {
        return new ResponseEntity<List<IpRangeResource>>(ipRangeService.getAllIpRanges()
                .stream()
                .map(range -> new IpRangeResource(range))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @RequestMapping(value = "admin/ip", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createIpRange(@RequestBody IpRangeResource resource) {
        ResponseEntity validation = validateIpRangeResource(resource);
        if (validation != null)
            return validation;

        List<IpRange> ranges = ipRangeService.getAllIpRanges();
        boolean found = false;
        for(IpRange range:ranges) {
            if(range.getIpStart().equals(resource.getRangeStart()) && range.getIpEnd().equals(resource.getRangeEnd())) {
                found = true;
                break;
            }
        }
        if(!found)
            return new ResponseEntity(new IpRangeResource(ipRangeService.save(resource.getObject())), HttpStatus.CREATED);
        else
            return new ResponseEntity(new MessageResource("Ip range: " + resource.getRangeStart() +" - "+ resource.getRangeEnd() + " already exists."), HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "admin/ip/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteIpRange(@PathVariable String uuid) {
        if (ipRangeService.findByUUID(uuid) == null)
            return new ResponseEntity(new MessageResource("Range not found!"), HttpStatus.NOT_FOUND);
        if (ipRangeService.delete(uuid))
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(new MessageResource("Something went wrong!"), HttpStatus.FORBIDDEN);
    }

    private ResponseEntity validateUserCreateResource(UserResource resource) {
        if (resource.getUsername() == null || resource.getUsername().isEmpty())
            return new ResponseEntity(new MessageResource("Missing username!"), HttpStatus.FORBIDDEN);
        if (resource.getUsername().length() > 25)
            return new ResponseEntity(new MessageResource("Username too long!"), HttpStatus.FORBIDDEN);
        if (resource.getName() == null || resource.getName().isEmpty())
            return new ResponseEntity(new MessageResource("Missing name!"), HttpStatus.FORBIDDEN);
        if (resource.getName().length() > 25)
            return new ResponseEntity(new MessageResource("Name too long!"), HttpStatus.FORBIDDEN);
        if (resource.getSurname() == null || resource.getSurname().isEmpty())
            return new ResponseEntity(new MessageResource("Missing surname!"), HttpStatus.FORBIDDEN);
        if (resource.getSurname().length() > 25)
            return new ResponseEntity(new MessageResource("Surname too long!"), HttpStatus.FORBIDDEN);
        if (resource.getEmail() == null || resource.getEmail().isEmpty())
            return new ResponseEntity(new MessageResource("Missing email!"), HttpStatus.FORBIDDEN);
        if (resource.getEmail().length() > 50)
            return new ResponseEntity(new MessageResource("Email too long!"), HttpStatus.FORBIDDEN);
        if (resource.getObject().getPassword() == null || resource.getObject().getPassword().isEmpty())
            return new ResponseEntity(new MessageResource("Missing password!"), HttpStatus.FORBIDDEN);
        if (resource.getObject().getPassword().length() > 25)
            return new ResponseEntity(new MessageResource("Password too long!"), HttpStatus.FORBIDDEN);

        if (resource.getRole() == null || resource.getRole().isEmpty())
            return new ResponseEntity(new MessageResource("Missing role!"), HttpStatus.FORBIDDEN);
        else if (!resource.getRole().equals("ROLE_ADMIN") && !resource.getRole().equals("ROLE_LECTURER")) {
            return new ResponseEntity(new MessageResource("Unrecognised role!"), HttpStatus.FORBIDDEN);
        }
        return null;
    }

    private ResponseEntity validateUserEditResource(UserResource resource) {
        if (resource.getUsername() == null || resource.getUsername().isEmpty())
            return new ResponseEntity(new MessageResource("Missing username!"), HttpStatus.FORBIDDEN);
        if (resource.getUsername().length() > 25)
            return new ResponseEntity(new MessageResource("Username too long!"), HttpStatus.FORBIDDEN);
        if (resource.getName() == null || resource.getName().isEmpty())
            return new ResponseEntity(new MessageResource("Missing name!"), HttpStatus.FORBIDDEN);
        if (resource.getName().length() > 25)
            return new ResponseEntity(new MessageResource("Name too long!"), HttpStatus.FORBIDDEN);
        if (resource.getSurname() == null || resource.getSurname().isEmpty())
            return new ResponseEntity(new MessageResource("Missing surname!"), HttpStatus.FORBIDDEN);
        if (resource.getSurname().length() > 25)
            return new ResponseEntity(new MessageResource("Surname too long!"), HttpStatus.FORBIDDEN);
        if (resource.getEmail() == null || resource.getEmail().isEmpty())
            return new ResponseEntity(new MessageResource("Missing email!"), HttpStatus.FORBIDDEN);
        if (resource.getEmail().length() > 50)
            return new ResponseEntity(new MessageResource("Email too long!"), HttpStatus.FORBIDDEN);
        return null;
    }

    private ResponseEntity validateStudentResource(StudentResource resource) {
        if (resource.getUniversityId() == null || resource.getUniversityId().isEmpty())
            return new ResponseEntity(new MessageResource("Missing university id!"), HttpStatus.FORBIDDEN);
        if (resource.getUniversityId().length() > 9)
            return new ResponseEntity(new MessageResource("Student id too long!"), HttpStatus.FORBIDDEN);
        if (resource.getName() == null || resource.getName().isEmpty())
            return new ResponseEntity(new MessageResource("Missing name!"), HttpStatus.FORBIDDEN);
        if (resource.getName().length() > 25)
            return new ResponseEntity(new MessageResource("Name too long!"), HttpStatus.FORBIDDEN);
        if (resource.getSurname() == null || resource.getSurname().isEmpty())
            return new ResponseEntity(new MessageResource("Missing surname!"), HttpStatus.FORBIDDEN);
        if (resource.getSurname().length() > 25)
            return new ResponseEntity(new MessageResource("Surname too long!"), HttpStatus.FORBIDDEN);
        if (resource.getEmail() == null || resource.getEmail().isEmpty())
            return new ResponseEntity(new MessageResource("Missing email!"), HttpStatus.FORBIDDEN);
        if (resource.getEmail().length() > 50)
            return new ResponseEntity(new MessageResource("Email too long!"), HttpStatus.FORBIDDEN);
        return null;
    }

    private ResponseEntity validateStudentResourceToAddToModule(StudentResource resource) {
        if (resource.getUniversityId() == null || resource.getUniversityId().isEmpty())
            return new ResponseEntity(new MessageResource("Missing university id!"), HttpStatus.FORBIDDEN);
        if (resource.getUniversityId().length() > 9)
            return new ResponseEntity(new MessageResource("Student id too long!"), HttpStatus.FORBIDDEN);
        return null;
    }

    private ResponseEntity validateModuleToCreateResource(ModuleToCreateResource resource) {
        if (resource.getModuleCode() == null || resource.getModuleCode().isEmpty())
            return new ResponseEntity(new MessageResource("Missing module code!"), HttpStatus.FORBIDDEN);
        if (resource.getModuleCode().length() > 6)
            return new ResponseEntity(new MessageResource("Module code too long!"), HttpStatus.FORBIDDEN);
        if (resource.getTitle() == null || resource.getTitle().isEmpty())
            return new ResponseEntity(new MessageResource("Missing title!"), HttpStatus.FORBIDDEN);
        if (resource.getTitle().length() > 36)
            return new ResponseEntity(new MessageResource("Title too long!"), HttpStatus.FORBIDDEN);
        if (resource.getLecturerUuid() == null || resource.getLecturerUuid().isEmpty())
            return new ResponseEntity(new MessageResource("Missing lecturer!"), HttpStatus.FORBIDDEN);
        if (resource.getStudentIds() == null || resource.getStudentIds().isEmpty())
            return new ResponseEntity(new MessageResource("Missing students!"), HttpStatus.FORBIDDEN);
        return null;
    }

    private ResponseEntity validateEditModuleResource(ModuleResource resource) {
        if (resource.getTitle() == null || resource.getTitle().isEmpty())
            return new ResponseEntity(new MessageResource("Missing title!"), HttpStatus.FORBIDDEN);
        if (resource.getTitle().length() > 36)
            return new ResponseEntity(new MessageResource("Title too long!"), HttpStatus.FORBIDDEN);
        if (resource.getLecturerUuid() == null || resource.getLecturerUuid().isEmpty())
            return new ResponseEntity(new MessageResource("Missing lecturer!"), HttpStatus.FORBIDDEN);
        return null;
    }

    private ResponseEntity validateClassToCreateResource(ScheduledClassToCreateResource resource) {
        List<String> groups = new ArrayList(Arrays.asList("NONE", "A", "B", "C", "D"));
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();

        if (resource.getModuleCode() == null || resource.getModuleCode().isEmpty())
            return new ResponseEntity(new MessageResource("Missing module code!"), HttpStatus.FORBIDDEN);
        if (resource.getStartDate() == null)
            return new ResponseEntity(new MessageResource("No start date provided"), HttpStatus.FORBIDDEN);
        if (resource.getEndDate() == null)
            return new ResponseEntity(new MessageResource("No end date provided"), HttpStatus.FORBIDDEN);

        start.setTime(new Date(resource.getStartDateTimestamp()));
        end.setTime(new Date(resource.getEndDateTimestamp()));

        if (resource.getStartDateTimestamp() > resource.getEndDateTimestamp()
                || resource.getStartDateTimestamp() == resource.getEndDateTimestamp()
                || end.get(Calendar.HOUR_OF_DAY) <= start.get(Calendar.HOUR_OF_DAY))
            return new ResponseEntity(new MessageResource("Invalid start or end date!"), HttpStatus.FORBIDDEN);
        if (resource.getGroup() == null || resource.getGroup().isEmpty())
            return new ResponseEntity(new MessageResource("Missing group name!"), HttpStatus.FORBIDDEN);
        if (!groups.contains(resource.getGroup().toUpperCase()))
            return new ResponseEntity(new MessageResource("Invalid group name"), HttpStatus.FORBIDDEN);
        if (resource.getRoom() == null || resource.getRoom().isEmpty())
            return new ResponseEntity(new MessageResource("Missing room number!"), HttpStatus.FORBIDDEN);
        if (resource.getRoom().length() > 6)
            return new ResponseEntity(new MessageResource("Room number too long!"), HttpStatus.FORBIDDEN);
        return null;
    }

    private ResponseEntity validateNewModuleClassGroupStudents(List<String> students, String moduleCode) {
        if (students.isEmpty())
            return new ResponseEntity<>(new MessageResource("No students provided for first class of the group!"), HttpStatus.FORBIDDEN);

        List<String> moduleStudentsIds = studentService.getStudentsAllocatedToAModule(moduleCode)
                .stream()
                .map(stud -> stud.getUniversityId())
                .collect(Collectors.toList());
        for (int i = 0; i < students.size(); i++) {
            if (!moduleStudentsIds.contains(students.get(i)))
                return new ResponseEntity<>(new MessageResource("A student with id: " + students.get(i) + " is not allocated to module or does not exist."), HttpStatus.FORBIDDEN);
        }
        return null;
    }

    private ResponseEntity validateIpRangeResource(IpRangeResource resource) {
        IpUtility util = new IpUtility();
        if (resource.getRangeStart() == null || resource.getRangeStart().isEmpty())
            return new ResponseEntity(new MessageResource("Missing ip range start!"), HttpStatus.FORBIDDEN);
        if (resource.getRangeEnd() == null || resource.getRangeEnd().isEmpty())
            return new ResponseEntity(new MessageResource("Missing ip range end!"), HttpStatus.FORBIDDEN);
        if (!util.validate(resource.getRangeStart()))
            return new ResponseEntity(new MessageResource("Invalid range start ip address!"), HttpStatus.FORBIDDEN);
        if (!util.validate(resource.getRangeEnd()))
            return new ResponseEntity(new MessageResource("Invalid range end ip address!"), HttpStatus.FORBIDDEN);
        return null;
    }
}