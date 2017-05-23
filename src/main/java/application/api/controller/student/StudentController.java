package application.api.controller.student;

import application.api.resource.attendance.AttendanceResource;
import application.api.resource.binary.BinaryResource;
import application.api.resource.message.MessageResource;
import application.api.resource.module.attendance.IndividualCumulativeModuleAttendanceResource;
import application.api.resource.module.ModuleResource;
import application.api.resource.signIn.SignInResource;
import application.domain.attendance.Attendance;
import application.domain.scheduledClass.ScheduledClass;
import application.domain.student.Student;
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

/**
 * This controller provides all the necessary
 * functionality for the student mobile application.
 */
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

    /**
     * This endpoint performs an existential check
     * on a combination of a student university id
     * and pin.
     * @param id
     * @param pin
     * @return BinaryResource
     */
    @RequestMapping(value="/student/{id}/{pin}", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BinaryResource> exists(@PathVariable String id,
                                                 @PathVariable String pin) {
        Boolean isIt = studentService.universityIdAndPinCombinationExist(id, pin);
            return new ResponseEntity<>(new BinaryResource<>(isIt), HttpStatus.OK);
    }


    /**
     * This endpoint returns a list of sign-ins
     * of a student with the given university id.
     * @param id
     * @return List of AttendanceResources
     */
    @RequestMapping(value="/student/{id}/{pin}/history", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getHistory(@PathVariable String id,
                                     @PathVariable String pin) {
        List<AttendanceResource> resources = new ArrayList<>();
        if(!studentService.universityIdAndPinCombinationExist(id, pin))
            return new ResponseEntity(new MessageResource("Invalid combination or student does not exist!"), HttpStatus.NOT_FOUND);
        attendanceService.getAttendanceRecordsForStudent(id).forEach(attendance -> resources.add(new AttendanceResource(attendance)));
        return new ResponseEntity<List<AttendanceResource>>(resources, HttpStatus.OK);
    }

    /**
     * This endpoint returns student attendance metrics
     * for the mobile presentation.
     * @param id
     * @return List of IndividualCumulativeModuleAttendanceResource
     */
    @RequestMapping(value="/student/{id}/{pin}/mobileMetrics", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<IndividualCumulativeModuleAttendanceResource>> getMobileMetrics(@PathVariable String id,
                                                                                               @PathVariable String pin) {
        List<IndividualCumulativeModuleAttendanceResource> resources = new ArrayList<>();
        if(!studentService.universityIdAndPinCombinationExist(id, pin))
            return new ResponseEntity(new MessageResource("Invalid combination or student does not exist!"), HttpStatus.NOT_FOUND);
        metricsService.getMobileCumulativeModuleMetricsForStudent(id).forEach(metrics -> resources.add(new IndividualCumulativeModuleAttendanceResource(metrics)));
        return new ResponseEntity<List<IndividualCumulativeModuleAttendanceResource>>(resources, HttpStatus.OK);
    }

    /**
     * This endpoint allows the user of the mobile application
     * to sign into the attendance system.
     *
     * The endpoint performs the following checks:
     *  -   Is the user at the university campus (IP check)?
     *  -   Does the user exist?
     *  -   Does the class - authentication code combination exist?
     *  -   Is the user allocated to the class?
     *  -   Has the user signed into the class already?
     * @param id
     * @param pin
     * @param signInResource
     * @param request
     * @return MessageResource
     */
    @RequestMapping(value="/student/{id}/{pin}/signIn", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<MessageResource> signIn(@PathVariable String id,
                                                  @PathVariable String pin,
                                                  @RequestBody SignInResource signInResource,
                                                  HttpServletRequest request) {

        ScheduledClass scheduledClass = scheduledClassService.getClassByAuthenticationCode(signInResource.getCode());
        Date now = new Date(System.currentTimeMillis());
        String address = request.getHeader("X-FORWARDED-FOR");
        if(address == null)
            address = request.getRemoteAddr();
        else
            address = address.split("\\s*,\\s*")[0];

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
                return new ResponseEntity<MessageResource>(new MessageResource("User Not Found or Incorrect Pin!"), HttpStatus.NOT_FOUND);
            }
        }
        else {
            return new ResponseEntity<MessageResource>(new MessageResource("Are you really at uni?"), HttpStatus.FORBIDDEN);
        }
    }

}
