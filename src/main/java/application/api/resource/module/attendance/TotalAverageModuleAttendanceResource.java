package application.api.resource.module.attendance;

import application.api.resource.BaseResource;
import application.domain.module.attendance.TotalAverageModuleAttendance;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TotalAverageModuleAttendanceResource extends BaseResource<TotalAverageModuleAttendance>{
    public TotalAverageModuleAttendanceResource(TotalAverageModuleAttendance object) {
        super(object);
    }

    @JsonProperty("moduleCode")
    public String getModuleCode() {
        return object.getModuleCode();
    }

    @JsonProperty("totaledClassAttendanceAveragePercentages")
    public double getTotaledClassAttendanceAveragePercentages() {
        return object.getTotaledClassAttendanceAveragePercentages();
    }

    @JsonProperty("numberOfClasses")
    public double getNumOfClasses() {
        return object.getNumOfClasses();
    }


    @JsonProperty("totalClassesCompletedToDate")
    public int getTotalClassesToDate() {
        return object.getClassesToDate();
    }
}
