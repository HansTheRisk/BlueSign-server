package application.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Base mother class for repositories.
 */
@Component
public class BaseJDBCRepository {

    @Autowired
    protected BaseJdbcTemplate executor;

}
