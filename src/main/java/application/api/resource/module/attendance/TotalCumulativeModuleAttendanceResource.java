package application.api.resource.module.attendance;

import application.domain.module.attendance.TotalCumulativeModuleAttendance;
import com.fasterxml.jackson.annotation.JsonProperty;
import application.api.resource.BaseResource;

public class TotalCumulativeModuleAttendanceResource extends BaseResource<TotalCumulativeModuleAttendance> {
    public TotalCumulativeModuleAttendanceResource(TotalCumulativeModuleAttendance object) {
        super(object);
    }

    @JsonProperty("moduleCode")
    public String getModuleCode() {
        return object.getModuleCode();
    }

    @JsonProperty("totalExpectedAttendancesToDate")
    public long getTotalToDate() {
        return object.getTotalOfExpectedAttendances();
    }

    @JsonProperty("totalActualAttendances")
    public long getTotalAttended() {
        return object.getTotalOfActualAttendances();
    }

    @JsonProperty("totalClassesCompletedToDate")
    public long getTotalClassesToDate() {
        return object.getCompletedClassesToDate();
    }
}
