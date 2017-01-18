package main.application.repository;

import main.application.domain.entity.IdentifiableEntity;
import org.springframework.jdbc.core.RowMapper;

public interface IdentifiableRowMapper<T extends IdentifiableEntity> extends RowMapper<T>{

}
