package main.application.api.controller.admin;

import main.application.api.resource.scheduledClass.ScheduledClassResource;
import main.application.api.resource.student.StudentResource;
import main.application.service.scheduledClass.ScheduledClassService;
import main.application.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@EnableAutoConfiguration
public class AdminController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ScheduledClassService scheduledClassService;

    @RequestMapping(value="admin/class/{universityId}/{moduleCode}", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ScheduledClassResource>> getModules(@PathVariable String universityId,
                                                                   @PathVariable String moduleCode) {
        List<ScheduledClassResource> classes = new ArrayList<>();
        scheduledClassService.findClassesByStudentUniveristyIdAndModuleUuid(universityId, moduleCode).forEach(scheduledClass ->
                classes.add(new ScheduledClassResource(scheduledClass)));
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @RequestMapping(value="admin/student", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<StudentResource>> getStudents() {
        List<StudentResource> students = new ArrayList<>();
        studentService.findAll().forEach(student -> students.add(new StudentResource(student)));
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

}
