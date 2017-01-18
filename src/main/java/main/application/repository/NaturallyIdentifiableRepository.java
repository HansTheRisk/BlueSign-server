package main.application.repository;

import main.application.domain.entity.NaturallyIdentifiableEntity;

public interface NaturallyIdentifiableRepository <T extends NaturallyIdentifiableEntity> extends IdentifiableRepository<T> {
    public T findByUuid(String uuid);
}
