package application.api.resource.accessCode;

import application.api.resource.BaseResource;
import application.domain.accessCode.AccessCode;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessCodeResource extends BaseResource<AccessCode>{
    public AccessCodeResource(AccessCode object) {
        super(object);
    }

    @JsonProperty("classUuid")
    public String getClassUuid() {
        return object.getClassUuid();
    }

    @JsonProperty("code")
    public int getCode() {
        return object.getCode();
    }
}
