package application.api.resource.accessCode;

import application.api.resource.BaseResource;
import application.api.resource.scheduledClass.ScheduledClassResource;
import application.domain.accessCode.AccessCodeForClass;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class correlates an access code resource
 * with a scheduled class resource.
 */
public class AccessCodeForClassResource extends BaseResource<AccessCodeForClass> {
    public AccessCodeForClassResource(AccessCodeForClass object) {
        super(object);
    }

    /**
     * This method returns the AccessCodeResource.
     * @return AccessCodeResource
     */
    @JsonProperty("accessCode")
    public AccessCodeResource getAccessCode() {
        return new AccessCodeResource(object.getAccessCode());
    }

    /**
     * This method returns the ScheduledClassResource
     * correlated with the access code.
     * @return ScheduledClassResource
     */
    @JsonProperty("class")
    public ScheduledClassResource getScheduledClass() {
        return new ScheduledClassResource(object.getScheduledClass());
    }
}
