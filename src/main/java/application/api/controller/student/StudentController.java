package application.api.controller.student;

import application.api.resource.attendance.AttendanceResource;
import application.api.resource.binary.BinaryResource;
import application.api.resource.message.MessageResource;
import application.api.resource.metrics.MobileCumulativeModuleMetricsResource;
import application.api.resource.module.ModuleResource;
import application.api.resource.signIn.SignInResource;
import application.domain.attendance.Attendance;
import application.domain.scheduledClass.ScheduledClass;
import application.service.allocation.AllocationService;
import application.service.attendance.AttendanceService;
import application.service.ipRange.IpRangeService;
import application.service.metrics.MetricsService;
import application.service.module.ModuleService;
import application.service.scheduledClass.ScheduledClassService;
import application.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private MetricsService metricsService;
    @Autowired
    private ScheduledClassService scheduledClassService;
    @Autowired
    private AllocationService allocationService;
    @Autowired
    private IpRangeService ipRangeService;

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
        metricsService.getMobileCumulativeModuleMetricsForStudent(id).forEach(metrics -> resources.add(new MobileCumulativeModuleMetricsResource(metrics)));
        return new ResponseEntity<List<MobileCumulativeModuleMetricsResource>>(resources, HttpStatus.OK);
    }

    @RequestMapping(value="/student/{id}/{pin}/signIn", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<MessageResource> signIn(@PathVariable String id,
                                                  @PathVariable String pin,
                                                  @RequestBody SignInResource signInResource,
                                                  HttpServletRequest request) {

        ScheduledClass scheduledClass = scheduledClassService.findClassByAuthenticationCode(signInResource.getCode());
        Date now = new Date(System.currentTimeMillis());
        String address = request.getHeader("X-FORWARDED-FOR").split("\\s*,\\s*")[0];

        if(ipRangeService.checkIfIpInRange(address)) {
            if (studentService.universityIdAndPinCombinationExist(id, pin)) {
                if (scheduledClass != null) {
                    if (allocationService.checkIfAllocationExists(id, scheduledClass.getUuid())) {
                        if (!attendanceService.checkIfAttendanceExists(id, scheduledClass.getUuid(), now)) {
                            if (attendanceService.insertAttendance(new Attendance(id, scheduledClass.getUuid(), now)))
                                return new ResponseEntity<MessageResource>(new MessageResource("Successfully signed in!"), HttpStatus.OK);
                            else
                                return new ResponseEntity<MessageResource>(new MessageResource("Something went terribly wrong!"), HttpStatus.OK);
                        }
                        else {
                            return new ResponseEntity<MessageResource>(new MessageResource("You have already signed in!"), HttpStatus.FORBIDDEN);
                        }
                    }
                    else {
                        return new ResponseEntity<MessageResource>(new MessageResource("Are you sure this is your class?"), HttpStatus.FORBIDDEN);
                    }
                }
                else {
                    return new ResponseEntity<MessageResource>(new MessageResource("Wrong code!"), HttpStatus.NOT_FOUND);
                }
            }
            else {
                return new ResponseEntity<MessageResource>(new MessageResource("User Not Found!"), HttpStatus.NOT_FOUND);
            }
        }
        else {
            return new ResponseEntity<MessageResource>(new MessageResource("Are you really at uni?"), HttpStatus.FORBIDDEN);
        }
    }

}