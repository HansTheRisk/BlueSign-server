package application.repository;

import application.domain.entity.NaturallyIdentifiableEntity;

/**
 * This interface classifies a row mapper as a NaturallyIdentifiableRowMapper
 * for mapping NaturallyIdentifiableEntities.
 * @param <T>
 */
public interface NaturallyIdentifiableRowMapper<T extends NaturallyIdentifiableEntity> extends IdentifiableRowMapper<T> {
}
