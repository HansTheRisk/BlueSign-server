package main.application.api.resource.scheduledClass;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.application.api.resource.NaturallyIdentifiableResource;
import main.application.domain.scheduledClass.ScheduledClass;

public class ScheduledClassResource extends NaturallyIdentifiableResource<ScheduledClass> {

    public ScheduledClassResource(ScheduledClass object) {
        super(object);
    }

//    @JsonProperty("day")
//    public String getDay() {
//        return object.getDay().toString();
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

//    @JsonProperty("startTime")
//    public String getStartTime() {
//        return object.getStartTime().toString();
//    }
//
//    @JsonProperty("endTime")
//    public String getEndTime() {
//        return object.getEndTime().toString();
//    }
}
