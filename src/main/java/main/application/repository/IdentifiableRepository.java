package main.application.repository;

import main.application.domain.entity.IdentifiableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

public interface IdentifiableRepository<T extends IdentifiableEntity> {
    public T findById(Long id);
}
