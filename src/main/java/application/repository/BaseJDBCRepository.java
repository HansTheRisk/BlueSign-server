package application.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BaseJDBCRepository {

    @Autowired
    protected BaseJdbcTemplate executor;

    protected  <T> ResultSetExtractor<Optional<T>> optionalResultSetExtractor(RowMapper<? extends T> mapper) {
        return rs -> rs.next() ? Optional.of(mapper.mapRow(rs, 1)) : Optional.empty();
    }
}
