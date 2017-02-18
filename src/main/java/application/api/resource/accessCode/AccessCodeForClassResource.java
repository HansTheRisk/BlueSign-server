package application.api.resource.accessCode;

import application.api.resource.BaseResource;
import application.api.resource.scheduledClass.ScheduledClassResource;
import application.domain.accessCode.AccessCodeForClass;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessCodeForClassResource extends BaseResource<AccessCodeForClass> {
    public AccessCodeForClassResource(AccessCodeForClass object) {
        super(object);
    }

    @JsonProperty("accessCode")
    public AccessCodeResource getAccessCode() {
        return new AccessCodeResource(object.getAccessCode());
    }

    @JsonProperty("class")
    public ScheduledClassResource getScheduledClass() {
        return new ScheduledClassResource(object.getScheduledClass());
    }
}
