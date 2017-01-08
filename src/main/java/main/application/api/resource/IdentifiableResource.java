package main.application.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.application.domain.entity.IdentifiableEntity;

public class IdentifiableResource<T extends IdentifiableEntity> extends BaseResource<T> {

    public IdentifiableResource(T object) {
        super(object);
    }

    @JsonProperty("id")
    public long getId() {
        return object.getId();
    }
}
