package application.api.resource.student;

import application.api.resource.attendance.AttendanceResource;
import application.domain.attendance.Attendance;
import application.domain.student.StudentAttendanceCorrelation;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentAttendanceCorrelationResource extends StudentResource{

    private Attendance attendance;

    public StudentAttendanceCorrelationResource(StudentAttendanceCorrelation object) {
        super(object.getStudent());
        attendance = object.getAttendance();
    }

    @JsonProperty("attendance")
    public AttendanceResource getAttendanceResource() {
        return new AttendanceResource(attendance);
    }
}
