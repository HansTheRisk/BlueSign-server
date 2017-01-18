package main.application.service;

import main.application.domain.entity.NaturallyIdentifiableEntity;

public interface NaturallyIdentifiableService<T extends NaturallyIdentifiableEntity> extends IdentifiableEntityService<T>{
    public T findByUUID(String uuid);
}
