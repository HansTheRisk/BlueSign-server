package application.service;

import application.domain.entity.NaturallyIdentifiableEntity;

/**
 * This interface defines the basic functionality that a repository
 * for NaturallyIdentifiable objects must provide.
 * @param <T>
 */
public interface NaturallyIdentifiableService<T extends NaturallyIdentifiableEntity> extends IdentifiableEntityService<T>{
    public T findByUUID(String uuid);
}
