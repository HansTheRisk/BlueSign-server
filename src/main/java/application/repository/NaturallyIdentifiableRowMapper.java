package application.repository;

import application.domain.entity.NaturallyIdentifiableEntity;

public interface NaturallyIdentifiableRowMapper<T extends NaturallyIdentifiableEntity> extends IdentifiableRowMapper<T> {
}
