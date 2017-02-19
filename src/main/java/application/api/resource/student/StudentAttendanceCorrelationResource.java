package application.api.resource.student;

import application.api.resource.attendance.AttendanceResource;
import application.domain.attendance.Attendance;
import application.domain.student.StudentAttendanceCorrelation;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents StudentAttendanceCorrelation as JSON object.
 * Used for listing attendance for a completed class.
 */
public class StudentAttendanceCorrelationResource extends StudentResource{

    private Attendance attendance;

    public StudentAttendanceCorrelationResource(StudentAttendanceCorrelation object) {
        super(object.getStudent());
        attendance = object.getAttendance();
    }

    /**
     * This method returns attendance resource for the student
     * attending a class.
     * @return  AttendanceResource
     */
    @JsonProperty("attendance")
    public AttendanceResource getAttendanceResource() {
        return new AttendanceResource(attendance);
    }
}
