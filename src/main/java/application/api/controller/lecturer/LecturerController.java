package application.api.controller.lecturer;

import application.api.resource.accessCode.AccessCodeForClassResource;
import application.api.resource.date.DateResource;
import application.api.resource.empty.EmptyJsonResource;
import application.api.resource.module.ModuleResource;
import application.api.resource.module.attendance.TotalAverageModuleAttendanceResource;
import application.api.resource.scheduledClass.ScheduledClassDatesResource;
import application.api.resource.scheduledClass.ScheduledClassResource;
import application.api.resource.scheduledClass.attendance.ClassAttendanceResource;
import application.api.resource.student.StudentAttendanceCorrelationResource;
import application.api.resource.student.StudentModuleAttendanceCorrelationResource;
import application.api.resource.student.StudentResource;
import application.domain.accessCode.AccessCodeForClass;
import application.domain.module.Module;
import application.domain.scheduledClass.ScheduledClass;
import application.domain.user.User;
import application.service.accessCode.AccessCodeService;
import application.service.metrics.MetricsService;
import application.service.module.ModuleService;
import application.service.scheduledClass.ScheduledClassService;
import application.service.student.StudentService;
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

/**
 * This api controller provides all the functionality necessary
 * for the BluSign lecturer web application.
 */
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

    @Autowired
    private AccessCodeService accessCodeService;

    /**
     * This endpoint returns all the modules for the lecturer.
     * @param auth
     * @return List of ModuleResource
     */
    @RequestMapping(value = "lecturer/module", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ModuleResource>> getModules(@Autowired Authentication auth) {
        List<ModuleResource> modules = new ArrayList<>();
        moduleService.getModulesForLecturer(((User)auth.getPrincipal()).getUuid()).forEach(module -> modules.add(new ModuleResource(module)));
        return new ResponseEntity<>(modules, HttpStatus.OK);
    }

    /**
     * This endpoint returns a list of all scheduled classes for
     * the given module code.
     * @param moduleCode
     * @return  List of ScheduledClassResources
     */
    @RequestMapping(value = "lecturer/module/{moduleCode}/class", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ScheduledClassResource>> getModulesClasses(@PathVariable String moduleCode) {
        if (moduleService.getByModuleCode(moduleCode) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<ScheduledClassResource> classes = new ArrayList<>();
        scheduledClassService.getClassesByModuleCode(moduleCode).forEach(scheduledClass -> classes.add(new ScheduledClassResource(scheduledClass)));
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    /**
     * This endpoint returns the average attendance percentage for
     * the module by the given module code.
     * @param moduleCode
     * @return TotalAverageModuleAttendanceResource
     */
    @RequestMapping(value = "lecturer/module/{moduleCode}/totalAverageAttendance", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<TotalAverageModuleAttendanceResource> getModuleTotalAverageAttendance(@PathVariable String moduleCode) {
        if (moduleService.getByModuleCode(moduleCode) != null)
            return new ResponseEntity<>(new TotalAverageModuleAttendanceResource(metricsService.getTotalAverageModuleAttendance(moduleCode)), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * This endpoint returns all the completed classes for a
     * scheduled class.
     * @param classUuid
     * @return ScheduledClassDates
     */
    @RequestMapping(value = "lecturer/class/{classUuid}/toDate", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ScheduledClassDatesResource> getClassesToDate(@PathVariable String classUuid) {
        ScheduledClass scheduledClass = scheduledClassService.findByUUID(classUuid);
        if (scheduledClass == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ScheduledClassDatesResource(scheduledClass, metricsService.getDatesOfCompletedClasses(scheduledClass)
                                                                                          .stream()
                                                                                          .sorted((dateOne, dateTwo) -> {
            if(dateOne.getTime() < dateTwo.getTime())
                return 1;
            else
                return -1;
        }).map(date -> new DateResource<>(date))
          .collect(Collectors.toList())), HttpStatus.OK);
    }

    /**
     * This endpoint returns attendance for a class that
     * has been completed.
     * @param classUuid
     * @param dateTimestamp
     * @return ClassAttendanceResource
     */
    @RequestMapping(value = "lecturer/class/{classUuid}/{dateTimestamp}/attendance", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ClassAttendanceResource> getClassAttendace(@PathVariable String classUuid,
                                                                     @PathVariable long dateTimestamp) {
        ScheduledClass scheduledClass = scheduledClassService.findByUUID(classUuid);
        if (scheduledClass == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ClassAttendanceResource(scheduledClassService.getClassAttendance(classUuid, dateTimestamp)), HttpStatus.OK);
    }

    /**
     * This endpoint returns a list of students
     * who attended a completed class.
     * @param classUuid
     * @param dateTimestamp
     * @return List of StudentAttendanceCorrelationResource
     */
    @RequestMapping(value = "lecturer/class/{classUuid}/{dateTimestamp}/attended", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<StudentAttendanceCorrelationResource>> getStudentsWhoAttendedClass(@PathVariable String classUuid,
                                                                                                  @PathVariable long dateTimestamp) {
        ScheduledClass scheduledClass = scheduledClassService.findByUUID(classUuid);
        if (scheduledClass == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(studentService.getAllWhoAttendedAClass(classUuid, dateTimestamp)
                                                  .stream()
                                                  .map(student -> new StudentAttendanceCorrelationResource(student))
                                                  .collect(Collectors.toList()), HttpStatus.OK);
    }

    /**
     * This endpoint returns a list of students
     * who were late for a completed class (20 min. after class start).
     * @param classUuid
     * @param dateTimestamp
     * @return List of StudentAttendanceCorrelationResource
     */
    @RequestMapping(value = "lecturer/class/{classUuid}/{dateTimestamp}/late", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<StudentAttendanceCorrelationResource>> getStudentsWhoWereLateForClass(@PathVariable String classUuid,
                                                                                                     @PathVariable long dateTimestamp) {
        ScheduledClass scheduledClass = scheduledClassService.findByUUID(classUuid);
        if (scheduledClass == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(studentService.getAllWhoWereLateForClass(classUuid, dateTimestamp)
                .stream()
                .map(student -> new StudentAttendanceCorrelationResource(student))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    /**
     * This endpoint returns a list of students
     * who did not attend a completed class.
     * @param classUuid
     * @param dateTimestamp
     * @return List of StudentResource
     */
    @RequestMapping(value = "lecturer/class/{classUuid}/{dateTimestamp}/notAttended", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<StudentResource>> getStudentsWhoDidNotAttendedClass(@PathVariable String classUuid,
                                                                                   @PathVariable long dateTimestamp) {
        ScheduledClass scheduledClass = scheduledClassService.findByUUID(classUuid);
        if (scheduledClass == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(studentService.getAllWhoDidNotAttendAClass(classUuid, dateTimestamp)
                                                  .stream()
                                                  .map(student -> new StudentResource(student))
                                                  .collect(Collectors.toList()), HttpStatus.OK);
    }

    /**
     * This endpoint returns a list of students of a module
     * and the corresponding attendance percentage for each
     * of the students.
     * @param moduleCode
     * @return List of StudentModuleAttendanceCorrelationResource
     */
    @RequestMapping(value = "lecturer/module/{moduleCode}/attendanceList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<StudentModuleAttendanceCorrelationResource>> getModuleAttendanceList(@PathVariable String moduleCode) {
        Module module = moduleService.getByModuleCode(moduleCode);
        if (module == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(metricsService.getStudentAttendanceList(moduleCode)
                .stream().sorted((studA, studB) -> {
                    double studAAttended = studA.getModuleAttendance().getTotalAttended();
                    double studBAttended = studB.getModuleAttendance().getTotalAttended();

                    double studACompleted = studA.getModuleAttendance().getTotalCompletedClassesToDate();
                    double studBCompleted = studB.getModuleAttendance().getTotalCompletedClassesToDate();
                    if((studAAttended / studACompleted) < (studBAttended / studBCompleted))
                        return 1;
                    else
                        return -1;
                })
                .map(attendance -> new StudentModuleAttendanceCorrelationResource(attendance))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    /**
     * This endpoint returns an access code for a class
     * running currently for the given lecturer.
     * @param auth
     * @return ResponseEntity
     */
    @RequestMapping(value = "lecturer/accessCode", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getAccessCode(@Autowired Authentication auth) {
        AccessCodeForClass ac = accessCodeService.getClassAccessCodeForLecturer(((User)auth.getPrincipal()).getUuid());
        if(ac == null)
            return new ResponseEntity<>(new EmptyJsonResource(), HttpStatus.OK);
        else
            return new ResponseEntity<>(new AccessCodeForClassResource(ac), HttpStatus.OK);
    }

    /**
     * This endpoint returns the lecturer page.
     * @return String
     */
    @RequestMapping("/lecturer")
    public String lecturer() {
        return "lecturerPage";
    }

}
