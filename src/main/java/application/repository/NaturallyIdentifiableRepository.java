package application.repository;

import application.domain.entity.NaturallyIdentifiableEntity;

/**
 * This interface sets out the basic functionality a repository
 * for NaturallyIdentifiableEntities must provide.
 * @param <T>
 */
public interface NaturallyIdentifiableRepository <T extends NaturallyIdentifiableEntity> extends IdentifiableRepository<T> {
    public T findByUuid(String uuid);
}
