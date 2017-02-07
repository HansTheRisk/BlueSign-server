package application.api.controller.lecturer;

import application.api.resource.date.DateResource;
import application.api.resource.module.ModuleResource;
import application.api.resource.scheduledClass.ScheduledClassDates;
import application.api.resource.scheduledClass.ScheduledClassResource;
import application.api.resource.scheduledClass.attendance.ClassAttendanceResource;
import application.domain.scheduledClass.ScheduledClass;
import application.domain.scheduledClass.attendance.ClassAttendance;
import application.domain.user.User;
import application.service.metrics.MetricsService;
import application.service.module.ModuleService;
import application.service.scheduledClass.ScheduledClassService;
import application.service.student.StudentService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LecturerController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private ScheduledClassService scheduledClassService;

    @Autowired
    private MetricsService metricsService;

//    @RequestMapping(value = "lecturer/student", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseEntity<List<StudentResource>> getStudents(@Autowired Authentication auth) {
//        List<StudentResource> students = new ArrayList<>();
//        studentService.findAll().forEach(student -> students.add(new StudentResource(student)));
//        return new ResponseEntity<>(students, HttpStatus.OK);
//    }

    @RequestMapping(value = "lecturer/modules", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ModuleResource>> getModules(@Autowired Authentication auth) {
        List<ModuleResource> modules = new ArrayList<>();
        moduleService.getModulesForLecturer(((User)auth.getPrincipal()).getUuid()).forEach(module -> modules.add(new ModuleResource(module)));
        return new ResponseEntity<>(modules, HttpStatus.OK);
    }

    @RequestMapping(value = "lecturer/modules/{moduleCode}/classes", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ScheduledClassResource>> getModulesClasses(@PathVariable String moduleCode) {
        List<ScheduledClassResource> classes = new ArrayList<>();
        scheduledClassService.findClassesByModuleCode(moduleCode).forEach(scheduledClass -> classes.add(new ScheduledClassResource(scheduledClass)));
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @RequestMapping(value = "lecturer/class/{classUuid}/toDate", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ScheduledClassDates> getClassesToDate(@PathVariable String classUuid) {
        ScheduledClass scheduledClass = scheduledClassService.findByUUID(classUuid);
        if (scheduledClass == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ScheduledClassDates(scheduledClass, metricsService.getDatesOfCompletedClasses(scheduledClass)
                                                  .stream().map(date -> new DateResource<>(date))
                                                  .collect(Collectors.toList())), HttpStatus.OK);
    }

    @RequestMapping(value = "lecturer/class/{classUuid}/{dateTimestamp}/attendance", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ClassAttendanceResource> getClassAttendace(@PathVariable String classUuid,
                                                             @PathVariable long dateTimestamp) {
        ScheduledClass scheduledClass = scheduledClassService.findByUUID(classUuid);
        if (scheduledClass == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ClassAttendanceResource(scheduledClassService.getClassAttendance(classUuid, dateTimestamp)), HttpStatus.OK);
    }

//    @RequestMapping(value = "lecturer/student", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseEntity<MessageResource> postTest(@Autowired Authentication auth, @RequestBody MessageResource messageResource) {
//        return new ResponseEntity<MessageResource>(new MessageResource("true"), HttpStatus.OK);
//    }

    @RequestMapping("/lecturer")
    public String lecturer() {
        return "lecturerPage";
    }

}
