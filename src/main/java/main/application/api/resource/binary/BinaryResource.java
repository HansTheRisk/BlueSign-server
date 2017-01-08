package main.application.api.resource.binary;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.application.api.resource.BaseResource;

public class BinaryResource<T extends Boolean> extends BaseResource<T>{

    public BinaryResource(T object) {
        super(object);
    }

    @JsonProperty("value")
    public boolean getValue() {
        return object.booleanValue();
    }
}
