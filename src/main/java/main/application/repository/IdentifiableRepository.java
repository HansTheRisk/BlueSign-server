package main.application.repository;

import main.application.domain.entity.identifiable.IdentifiableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IdentifiableRepository<T extends IdentifiableEntity> extends JpaRepository<T, Long> {

}
