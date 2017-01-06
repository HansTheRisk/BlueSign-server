package main.application.repository;

import main.application.domain.entity.naturallyIdentifiable.NaturallyIdentifiableEntity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface NaturallyIdentifiableRepository <T extends NaturallyIdentifiableEntity> extends IdentifiableRepository<T> {
    public T findByUuid(String uuid);
}