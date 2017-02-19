package application.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Base mother class for repositories.
 */
@Component
public class BaseJDBCRepository {

    @Autowired
    protected BaseJdbcTemplate executor;

}
