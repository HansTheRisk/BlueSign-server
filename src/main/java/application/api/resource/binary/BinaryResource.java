package application.api.resource.binary;

import com.fasterxml.jackson.annotation.JsonProperty;
import application.api.resource.BaseResource;

/**
 * Class used to represent boolean
 * values as JSON responses.
 * @param <T>
 */
public class BinaryResource<T extends Boolean> extends BaseResource<T>{

    public BinaryResource(T object) {
        super(object);
    }

    /**
     * Returns the value of the boolean
     * @return boolean
     */
    @JsonProperty("value")
    public boolean getValue() {
        return object.booleanValue();
    }
}
