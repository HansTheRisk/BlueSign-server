package main.application.service;

import main.application.domain.entity.identifiable.IdentifiableEntity;
import main.application.repository.IdentifiableRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class IdentifiableEntityService<T extends IdentifiableEntity> {

    @Autowired
    public <S extends IdentifiableRepository<T>> IdentifiableEntityService(S repository) {
        this.repository = repository;
    }

    public T findOne(Long id)
    {
        return repository.findOne(id);
    }

    public T save(T object)
    {
        return repository.save(object);
    }

    protected IdentifiableRepository<T> repository;
}
