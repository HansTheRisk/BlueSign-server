package application.domain.entity;

/**
 * An interface that creates the foundations for all
 * database entities that can be identified with a single
 * internal id.
 */
public interface IdentifiableEntity {

    public Long getId();
    public void setId(Long id);
}
