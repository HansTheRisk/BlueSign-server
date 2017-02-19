package application.api.resource.student;

import application.api.resource.module.attendance.IndividualCumulativeModuleAttendanceResource;
import application.domain.module.attendance.IndividualCumulativeModuleAttendance;
import application.domain.student.StudentModuleAttendanceCorrelation;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class correlates the Student and IndividualCumulativeModuleAttendance.
 * Student -> Module attendance
 * Used for listing module students' attendance in the web application.
 */
public class StudentModuleAttendanceCorrelationResource extends StudentResource{

    private IndividualCumulativeModuleAttendance attendance;

    public StudentModuleAttendanceCorrelationResource(StudentModuleAttendanceCorrelation object) {
        super(object.getStudent());
        attendance = object.getModuleAttendance();
    }

    /**
     * This method returns the IndividualCumulativeModuleAttendanceResource
     * for the student.
     * @return IndividualCumulativeModuleAttendanceResource
     */
    @JsonProperty("attendance")
    public IndividualCumulativeModuleAttendanceResource getAttendanceResource() {
        return new IndividualCumulativeModuleAttendanceResource(attendance);
    }
}
