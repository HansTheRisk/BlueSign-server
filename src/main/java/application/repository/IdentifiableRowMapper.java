package application.repository;

import application.domain.entity.IdentifiableEntity;
import org.springframework.jdbc.core.RowMapper;

public interface IdentifiableRowMapper<T extends IdentifiableEntity> extends RowMapper<T>{

}
