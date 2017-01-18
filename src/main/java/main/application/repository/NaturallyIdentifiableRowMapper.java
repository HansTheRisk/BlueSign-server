package main.application.repository;

import main.application.domain.entity.NaturallyIdentifiableEntity;

public interface NaturallyIdentifiableRowMapper<T extends NaturallyIdentifiableEntity> extends IdentifiableRowMapper<T> {
}
