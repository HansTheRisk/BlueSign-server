package application.api.resource.module.attendance;

import application.api.resource.BaseResource;
import application.domain.module.attendance.TotalAverageModuleAttendance;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents TotalAverageModuleAttendance as JSON object.
 */
public class TotalAverageModuleAttendanceResource extends BaseResource<TotalAverageModuleAttendance>{
    public TotalAverageModuleAttendanceResource(TotalAverageModuleAttendance object) {
        super(object);
    }

    /**
     * Returns module code for the attendance data.
     * @return String
     */
    @JsonProperty("moduleCode")
    public String getModuleCode() {
        return object.getModuleCode();
    }

    /**
     * Returns the totaled average attendance percentage of
     * the module classes.
     * @return double
     */
    @JsonProperty("totaledClassAttendanceAveragePercentages")
    public double getTotaledClassAttendanceAveragePercentages() {
        return object.getTotaledClassAttendanceAveragePercentages();
    }

    /**
     * Returns the number of scheduled classes
     * of the module.
     * @return double
     */
    @JsonProperty("numberOfClasses")
    public double getNumOfClasses() {
        return object.getNumOfClasses();
    }

    /**
     * Returns the number of completed classes
     * for the module to date.
     * @return int
     */
    @JsonProperty("totalClassesCompletedToDate")
    public int getTotalClassesToDate() {
        return object.getClassesToDate();
    }
}
