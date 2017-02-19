package application.repository;

import application.domain.entity.IdentifiableEntity;
import org.springframework.jdbc.core.RowMapper;

/**
 * This interface classifies a row mapper as an IdentifiableRowMapper
 * for mapping IdentifiableEntities.
 * @param <T>
 */
public interface IdentifiableRowMapper<T extends IdentifiableEntity> extends RowMapper<T>{

}
