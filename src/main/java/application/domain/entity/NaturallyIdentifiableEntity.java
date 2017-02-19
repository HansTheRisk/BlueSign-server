package application.domain.entity;

/**
 * An interface that forms the foundations
 * for domain classes which database records can be
 * identified by UUIDs.
 */
public interface NaturallyIdentifiableEntity extends IdentifiableEntity {

    public String getUuid();
    public void setUuid(String uuid);
}
