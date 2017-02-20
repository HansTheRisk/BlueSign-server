package application.service;

import application.domain.entity.IdentifiableEntity;

/**
 * This interface defines the basic functionality that a repository
 * for IdentifiableEntity objects must provide.
 * @param <T>
 */
public interface IdentifiableEntityService<T extends IdentifiableEntity> {

    public T findById(Long id);
    public T save(T object);

}
