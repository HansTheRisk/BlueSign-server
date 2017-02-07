package application.api.resource.attendance;

import com.fasterxml.jackson.annotation.JsonProperty;
import application.api.resource.BaseResource;
import application.domain.attendance.Attendance;

public class AttendanceResource extends BaseResource<Attendance> {
    public AttendanceResource(Attendance object) {
        super(object);
    }

    @JsonProperty("studentUniversityId")
    public String getStudentUniversityId() {
        return object.getStudentUniversityId();
    }

    @JsonProperty("classUuid")
    public String getClassUuid() {
        return object.getClassUuid();
    }

    @JsonProperty("moduleCode")
    public String getModuleCOde() {
        return object.getModuleCode();
    }

    @JsonProperty("date")
    public String getDate() {
        return object.getDate().toString();
    }

    @JsonProperty("dateTimestamp")
    public long getDateTimestamp() {
        return object.getDate().getTime();
    }
}