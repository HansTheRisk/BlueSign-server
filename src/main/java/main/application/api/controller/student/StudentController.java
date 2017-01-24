package main.application.api.controller.student;

import main.application.api.resource.attendance.AttendanceResource;
import main.application.api.resource.binary.BinaryResource;
import main.application.api.resource.message.MessageResource;
import main.application.api.resource.metrics.MobileCumulativeModuleMetricsResource;
import main.application.api.resource.module.ModuleResource;
import main.application.api.resource.student.StudentResource;
import main.application.service.attendance.AttendanceService;
import main.application.service.metrics.MetricsService;
import main.application.service.module.ModuleService;
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
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private MetricsService metricsService;

    @RequestMapping(value="/student", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<StudentResource>> test() {
        List<StudentResource> students = new ArrayList<>();
        studentService.findAll().forEach(student -> students.add(new StudentResource(student)));
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @RequestMapping(value="/student/{id}/{pin}", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BinaryResource> exists(@PathVariable String id,
                                                 @PathVariable String pin) {
        Boolean isIt = studentService.universityIdAndPinCombinationExist(id, pin);
            return new ResponseEntity<>(new BinaryResource<>(isIt), HttpStatus.OK);
    }

    public ResponseEntity<MessageResource> signIn() {
        return null;
    }

    @RequestMapping(value="/student/{id}/modules", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ModuleResource>> getModules(@PathVariable String id) {
        List<ModuleResource> modules = new ArrayList<>();
        moduleService.getModulesForStudent(id).forEach(module ->
                modules.add(new ModuleResource(module)));
        return new ResponseEntity<>(modules, HttpStatus.OK);
    }

    @RequestMapping(value="/student/{id}/history", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<AttendanceResource>> getHistory(@PathVariable String id) {
        List<AttendanceResource> resources = new ArrayList<>();
        attendanceService.getAttendanceForStudent(id).forEach(attendance -> resources.add(new AttendanceResource(attendance)));
        return new ResponseEntity<List<AttendanceResource>>(resources, HttpStatus.OK);
    }

    @RequestMapping(value="/student/{id}/mobileMetrics", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<MobileCumulativeModuleMetricsResource>> getMobileMetrics(@PathVariable String id) {
        List<MobileCumulativeModuleMetricsResource> resources = new ArrayList<>();
        metricsService.getMobileCumulativeModuleMetrics(id).forEach(metrics -> resources.add(new MobileCumulativeModuleMetricsResource(metrics)));
        return new ResponseEntity<List<MobileCumulativeModuleMetricsResource>>(resources, HttpStatus.OK);
    }

    @RequestMapping(value="/student/{id}/{pin}/signIn", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<AttendanceResource>> signIn(@PathVariable String id,
                                                           @PathVariable String pin) {

        //TODO PUT THE CODE HERE!
        
        List<AttendanceResource> resources = new ArrayList<>();
        attendanceService.getAttendanceForStudent(id).forEach(attendance -> resources.add(new AttendanceResource(attendance)));
        return new ResponseEntity<List<AttendanceResource>>(resources, HttpStatus.OK);
    }

}
