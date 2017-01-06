package main.application.domain.entity.naturallyIdentifiable;

import main.application.domain.entity.identifiable.IdentifiableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;

public interface NaturallyIdentifiableEntity extends IdentifiableEntity {

    public String getUuid();
    public void setUuid(String uuid);
}
