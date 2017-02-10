package application.api.resource.scheduledClass.attendance;

import application.api.resource.BaseResource;
import application.domain.scheduledClass.attendance.CompletedClassAttendance;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassAttendanceResource extends BaseResource<CompletedClassAttendance>{
    public ClassAttendanceResource(CompletedClassAttendance object) {
        super(object);
    }

    @JsonProperty("classUuid")
    public String getClassUuid() {
        return object.getClassUuid();
    }

    @JsonProperty("date")
    public String getDate() {
        return object.getDate().toString();
    }

    @JsonProperty("dateTimestamp")
    public long getDateTimestamp() {
        return object.getDate().getTime();
    }

    @JsonProperty("allocated")
    public int getAllocated() {
        return object.getAllocated();
    }

    @JsonProperty("attended")
    public int getAttended() {
        return object.getAttended();
    }
}
