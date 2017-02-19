package application.repository;

import application.domain.entity.IdentifiableEntity;

/**
 * This interface sets out the basic functionality a repository
 * for IdentifiableEntities must provide.
 * @param <T>
 */
public interface IdentifiableRepository<T extends IdentifiableEntity> {
    public T findById(Long id);
}
