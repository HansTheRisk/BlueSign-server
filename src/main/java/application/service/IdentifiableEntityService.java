package application.service;

import application.domain.entity.IdentifiableEntity;

public interface IdentifiableEntityService<T extends IdentifiableEntity> {

    public T findById(Long id);
    public T save(T object);

}
