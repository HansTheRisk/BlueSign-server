package main.application.api.resource.attendance;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.application.api.resource.BaseResource;
import main.application.domain.attendance.Attendance;

import java.util.Date;

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
    public Date getDate() {
        return object.getDate();
    }
}
