package application.api.resource.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import application.api.resource.BaseResource;

public class MessageResource extends BaseResource<String>{

    public MessageResource() {
        super(null);
    }

    public MessageResource(String object) {
        super(object);
    }

    @JsonProperty("message")
    public String getMessage() {
        return object;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.object = message;
    }
}
