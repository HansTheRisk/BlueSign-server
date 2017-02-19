package application.api.resource.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import application.api.resource.BaseResource;

/**
 * A class that is used to represent simple text
 * messages in JSON object form.
 */
public class MessageResource extends BaseResource<String>{

    public MessageResource() {
        super(null);
    }

    public MessageResource(String object) {
        super(object);
    }

    /**
     * Returns the text message.
     * @return String
     */
    @JsonProperty("message")
    public String getMessage() {
        return object;
    }

    /**
     * Sets the text message.
     * @param message
     */
    @JsonProperty("message")
    public void setMessage(String message) {
        this.object = message;
    }
}
