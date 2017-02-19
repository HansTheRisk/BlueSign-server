package application.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import application.domain.entity.NaturallyIdentifiableEntity;

/**
 * This class extends the IdentifiableResource class
 * providing additional functionality for representing
 * NaturallyIdentifiableEntities.
 * @param <T>
 */
public class NaturallyIdentifiableResource<T extends NaturallyIdentifiableEntity> extends IdentifiableResource<T> {

    /**
     * Constructor for the class.
     * @param object
     */
    public NaturallyIdentifiableResource(T object) {
        super(object);
    }

    /**
     * This method returns the UUID for the resource.
     * @return String
     */
    @JsonProperty("uuid")
    public String getUuid() {
        return object.getUuid();
    }

}
