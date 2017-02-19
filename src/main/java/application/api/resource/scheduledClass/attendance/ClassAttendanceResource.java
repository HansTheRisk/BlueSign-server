package application.api.resource.scheduledClass.attendance;

import application.api.resource.BaseResource;
import application.domain.scheduledClass.attendance.CompletedClassAttendance;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents CompletedClassAttendance as JSON object.
 */
public class ClassAttendanceResource extends BaseResource<CompletedClassAttendance>{
    public ClassAttendanceResource(CompletedClassAttendance object) {
        super(object);
    }

    /**
     * This method returns class UUID
     * for the class attendance.
     * @return String
     */
    @JsonProperty("classUuid")
    public String getClassUuid() {
        return object.getClassUuid();
    }

    /**
     * This method returns the date for the completed class.
     * @return String
     */
    @JsonProperty("date")
    public String getDate() {
        return object.getDate().toString();
    }

    /**
     * This method returns the date timestamp for the completed class.
     * @return long
     */
    @JsonProperty("dateTimestamp")
    public long getDateTimestamp() {
        return object.getDate().getTime();
    }

    /**
     * This method returns the number of allocated students to the class.
     * @return long
     */
    @JsonProperty("allocated")
    public long getAllocated() {
        return object.getAllocated();
    }

    /**
     * This method returns the number of students who attended the class.
     * @return long
     */
    @JsonProperty("attended")
    public long getAttended() {
        return object.getAttended();
    }
}
