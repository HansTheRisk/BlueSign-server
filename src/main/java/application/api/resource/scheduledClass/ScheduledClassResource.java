package application.api.resource.scheduledClass;

import com.fasterxml.jackson.annotation.JsonProperty;
import application.api.resource.NaturallyIdentifiableResource;
import application.domain.scheduledClass.ScheduledClass;

import java.util.Calendar;

/**
 * This class represents the ScheduledClass as JSON object
 */
public class ScheduledClassResource extends NaturallyIdentifiableResource<ScheduledClass> {

    String[] days = new String[]{
            "SUNDAY",
            "MONDAY",
            "TUESDAY",
            "WEDNESDAY",
            "THURSDAY",
            "FRIDAY",
            "SATURDAY"
    };

    public ScheduledClassResource(ScheduledClass object) {
        super(object);
    }

    /**
     * This method returns the day when the class runs.
     * @return String
     */
    @JsonProperty("day")
    public String getDay() {
        Calendar calendar =  Calendar.getInstance();
        calendar.setTimeInMillis(object.getStartDate().getTime());
        return days[(calendar.get(Calendar.DAY_OF_WEEK) -1)];
    }

    /**
     * This method returns the module code
     * of the class.
     * @return  String
     */
    @JsonProperty("moduleCode")
    public String getModuleCode() {
        return object.getModuleCode();
    }

    /**
     * This method returns the room where the class
     * is conducted.
     * @return String
     */
    @JsonProperty("room")
    public String getRoom() {
        return object.getRoom();
    }

    /**
     * This method returns the group of the class.
     * @return String
     */
    @JsonProperty("group")
    public String getGroup() {
        return object.getGroup();
    }

    /**
     * This method returns the start date of the class.
     * It includes the date and the starting time.
     * @return String
     */
    @JsonProperty("startDate")
    public String getStartDate() {
        return object.getStartDate().toString();
    }

    /**
     * This method returns the ending date for the class.
     * @return String
     */
    @JsonProperty("endDate")
    public String getEndDate() {
        return object.getEndDate().toString();
    }

    /**
     * This method returns the start date timestamp for the class.
     * @return long
     */
    @JsonProperty("startDateTimestamp")
    public long getStartDateTimestamp() {
        return object.getStartDate().getTime();
    }

    /**
     * This method returns the end date timestamp for the class.
     * @return
     */
    @JsonProperty("endDateTimestamp")
    public long getEndDateTimestamp() {
        return object.getEndDate().getTime();
    }
}
