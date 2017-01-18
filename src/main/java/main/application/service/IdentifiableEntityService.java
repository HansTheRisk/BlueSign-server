package main.application.service;

import main.application.domain.entity.IdentifiableEntity;

public interface IdentifiableEntityService<T extends IdentifiableEntity> {

    public T findById(Long id);
    public T save(T object);

}
