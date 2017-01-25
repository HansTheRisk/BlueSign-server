package main.application.api.resource.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import main.application.api.resource.BaseResource;

public class MessageResource extends BaseResource<String>{

    public MessageResource(String object) {
        super(object);
    }

    @JsonProperty("message")
    public String getMessage() {
        return object;
    }
}
