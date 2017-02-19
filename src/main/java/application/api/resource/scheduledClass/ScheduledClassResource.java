package application.api.resource.scheduledClass;

import com.fasterxml.jackson.annotation.JsonProperty;
import application.api.resource.NaturallyIdentifiableResource;
import application.domain.scheduledClass.ScheduledClass;

import java.util.Calendar;

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

    @JsonProperty("day")
    public String getDay() {
        Calendar calendar =  Calendar.getInstance();
        calendar.setTimeInMillis(object.getStartDate().getTime());
        return days[(calendar.get(Calendar.DAY_OF_WEEK) -1)];
    }

    @JsonProperty("moduleCode")
    public String getModuleCode() {
        return object.getModuleCode();
    }

    @JsonProperty("room")
    public String getRoom() {
        return object.getRoom();
    }

    @JsonProperty("group")
    public String getGroup() {
        return object.getGroup();
    }

    @JsonProperty("startDate")
    public String getStartDate() {
        return object.getStartDate().toString();
    }

    @JsonProperty("endDate")
    public String getEndDate() {
        return object.getEndDate().toString();
    }

    @JsonProperty("startDateTimestamp")
    public long getStartDateTimestamp() {
        return object.getStartDate().getTime();
    }

    @JsonProperty("endDateTimestamp")
    public long getEndDateTimestamp() {
        return object.getEndDate().getTime();
    }
}
