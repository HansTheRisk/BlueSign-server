package application.repository;

import application.domain.entity.IdentifiableEntity;

public interface IdentifiableRepository<T extends IdentifiableEntity> {
    public T findById(Long id);
}
