package main.application.domain.entity.naturallyIdentifiable;

import main.application.domain.entity.identifiable.IdentifiableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;

@Entity
@Inheritance
public abstract class NaturallyIdentifiableEntity extends IdentifiableEntity {

    @Column(name="uuid")
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
