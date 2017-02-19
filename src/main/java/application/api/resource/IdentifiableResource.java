package application.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import application.domain.entity.IdentifiableEntity;

/**
 * This class extends BaseResource class
 * and provides the basic resource functionality
 * needed to represent Identifiable entities in JSON.
 * @param <T>
 */
public class IdentifiableResource<T extends IdentifiableEntity> extends BaseResource<T> {

    /**
     * Constructor for the class.
     * @param object
     */
    public IdentifiableResource(T object) {
        super(object);
    }

    /**
     * Id of the resource.
     * @return long
     */
    @JsonProperty("id")
    public long getId() {
        return object.getId();
    }
}
