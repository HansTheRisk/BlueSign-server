package application.api.resource.student;

import application.api.resource.module.attendance.IndividualCumulativeModuleAttendanceResource;
import application.domain.module.attendance.IndividualCumulativeModuleAttendance;
import application.domain.student.StudentModuleAttendanceCorrelation;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentModuleAttendanceCorrelationResource extends StudentResource{

    private IndividualCumulativeModuleAttendance attendance;

    public StudentModuleAttendanceCorrelationResource(StudentModuleAttendanceCorrelation object) {
        super(object.getStudent());
        attendance = object.getModuleAttendance();
    }

    @JsonProperty("attendance")
    public IndividualCumulativeModuleAttendanceResource getAttendanceResource() {
        return new IndividualCumulativeModuleAttendanceResource(attendance);
    }
}
