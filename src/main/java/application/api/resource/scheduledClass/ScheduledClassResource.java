package application.api.resource.scheduledClass;

import com.fasterxml.jackson.annotation.JsonProperty;
import application.api.resource.NaturallyIdentifiableResource;
import application.domain.scheduledClass.ScheduledClass;

public class ScheduledClassResource extends NaturallyIdentifiableResource<ScheduledClass> {

    public ScheduledClassResource(ScheduledClass object) {
        super(object);
    }

    //TODO: Write a converter for Calendar Day to DayOfWeek
//    @JsonProperty("day")
//    public String getDay() {
//        Calendar calendar =  Calendar.getInstance();
//        calendar.setTimeInMillis(object.getStartDate().getTime());
//        return calendar.get(Calendar.DAY_OF_WEEK);
//    }

    @JsonProperty("moduleCode")
    public String getModuleCode() {
        return object.getModuleCode();
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
