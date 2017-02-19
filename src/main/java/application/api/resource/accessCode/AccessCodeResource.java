package application.api.resource.accessCode;

import application.api.resource.BaseResource;
import application.domain.accessCode.AccessCode;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents the access code
 * for a class in JSON.
 */
public class AccessCodeResource extends BaseResource<AccessCode>{
    public AccessCodeResource(AccessCode object) {
        super(object);
    }

    /**
     * This method returns the class UUID.
     * @return String
     */
    @JsonProperty("classUuid")
    public String getClassUuid() {
        return object.getClassUuid();
    }

    /**
     * This method returns the access code for the class.
     * @return String
     */
    @JsonProperty("code")
    public int getCode() {
        return object.getCode();
    }
}
