package application.api.resource.module.attendance;

import com.fasterxml.jackson.annotation.JsonProperty;
import application.api.resource.BaseResource;
import application.domain.module.attendance.IndividualCumulativeModuleAttendance;

/**
 * This class represents IndividualCumulativeModuleAttendance in JSON form.
 */
public class IndividualCumulativeModuleAttendanceResource extends BaseResource<IndividualCumulativeModuleAttendance> {
    public IndividualCumulativeModuleAttendanceResource(IndividualCumulativeModuleAttendance object) {
        super(object);
    }

    /**
     * This method returns the module code for the
     * attendance data.
     * @return String
     */
    @JsonProperty("moduleCode")
    public String getModuleCode() {
        return object.getModuleCode();
    }

    /**
     * This method returns the amount of
     * completed classes to date for the module
     * that are allocated to the student.
     * @return long
     */
    @JsonProperty("totalToDate")
    public long getTotalToDate() {
        return object.getTotalCompletedClassesToDate();
    }

    /**
     * This method returns the total number
     * of classes of the module attended
     * by the student.
     * @return long
     */
    @JsonProperty("totalAttended")
    public long getTotalAttended() {
        return object.getTotalAttended();
    }
}
