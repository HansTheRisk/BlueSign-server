package application.api.resource.attendance;

import com.fasterxml.jackson.annotation.JsonProperty;
import application.api.resource.BaseResource;
import application.domain.attendance.Attendance;

/**
 * This class represents Attendance as JSON resource.
 */
public class AttendanceResource extends BaseResource<Attendance> {
    public AttendanceResource(Attendance object) {
        super(object);
    }

    /**
     * Returns the university id of the student
     * who issued the attendance record.
     * @return String
     */
    @JsonProperty("studentUniversityId")
    public String getStudentUniversityId() {
        return object.getStudentUniversityId();
    }

    /**
     * Returns the UUID of the class
     * the attendance record was issued for.
     * @return String
     */
    @JsonProperty("classUuid")
    public String getClassUuid() {
        return object.getClassUuid();
    }

    /**
     * Returns module code for the class
     * @return String
     */
    @JsonProperty("moduleCode")
    public String getModuleCOde() {
        return object.getModuleCode();
    }

    /**
     * Returns date of the attendance record
     * @return String
     */
    @JsonProperty("date")
    public String getDate() {
        return object.getDate().toString();
    }

    /**
     * Returns timestamp for the date
     * of the attendance record.
     * @return long
     */
    @JsonProperty("dateTimestamp")
    public long getDateTimestamp() {
        return object.getDate().getTime();
    }
}
