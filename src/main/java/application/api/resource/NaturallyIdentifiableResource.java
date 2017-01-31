package application.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import application.domain.entity.NaturallyIdentifiableEntity;

public class NaturallyIdentifiableResource<T extends NaturallyIdentifiableEntity> extends IdentifiableResource<T> {

    public NaturallyIdentifiableResource(T object) {
        super(object);
    }

    @JsonProperty("uuid")
    public String getUuid() {
        return object.getUuid();
    }

}
