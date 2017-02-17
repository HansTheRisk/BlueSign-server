package application.api.resource.module.attendance;

import com.fasterxml.jackson.annotation.JsonProperty;
import application.api.resource.BaseResource;
import application.domain.module.attendance.IndividualCumulativeModuleAttendance;

public class IndividualCumulativeModuleAttendanceResource extends BaseResource<IndividualCumulativeModuleAttendance> {
    public IndividualCumulativeModuleAttendanceResource(IndividualCumulativeModuleAttendance object) {
        super(object);
    }

    @JsonProperty("moduleCode")
    public String getModuleCode() {
        return object.getModuleCode();
    }

    @JsonProperty("totalToDate")
    public long getTotalToDate() {
        return object.getTotalCompletedClassesToDate();
    }

    @JsonProperty("totalAttended")
    public long getTotalAttended() {
        return object.getTotalAttended();
    }
}
