package application.api.resource.scheduledClass;

import application.api.resource.date.DateResource;
import application.domain.scheduledClass.ScheduledClass;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * This class adds dates of completed classes to the ScheduledClassResource.
 */
public class ScheduledClassDatesResource extends ScheduledClassResource {

    private List<DateResource> dates;

    public ScheduledClassDatesResource(ScheduledClass scheduledClass, List<DateResource> dates) {
        super(scheduledClass);
        this.dates = dates;
    }

    /**
     * Dates of completed classes.
     * @return List of DateResources
     */
    @JsonProperty("dates")
    public List<DateResource> getDates() {
        return dates;
    }
}
