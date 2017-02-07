package application.api.resource.scheduledClass;

import application.api.resource.date.DateResource;
import application.domain.scheduledClass.ScheduledClass;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ScheduledClassDates extends ScheduledClassResource {

    private List<DateResource> dates;

    public ScheduledClassDates(ScheduledClass scheduledClass, List<DateResource> dates) {
        super(scheduledClass);
        this.dates = dates;
    }

    @JsonProperty("dates")
    public List<DateResource> getDates() {
        return dates;
    }
}
