package main.application.domain.entity;

public interface NaturallyIdentifiableEntity extends IdentifiableEntity {

    public String getUuid();
    public void setUuid(String uuid);
}
