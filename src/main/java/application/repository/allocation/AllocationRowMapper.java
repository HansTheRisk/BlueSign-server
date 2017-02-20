package application.repository.allocation;

import application.domain.allocation.Allocation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Row mapper mapping allocation records into Allocation objects.
 */
public class AllocationRowMapper implements RowMapper<Allocation> {
    @Override
    public Allocation mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Allocation(rs.getString("university_id"), rs.getString("uuid"));
    }
}
