package application.repository.allocation;

import application.domain.allocation.Allocation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Row mapper mapping allocation records into Allocation objects.
 */
public class AllocationRowMapper implements RowMapper<Allocation> {
    @Override
    public Allocation mapRow(ResultSet rs, int rowNum) throws SQLException {
        Timestamp start = rs.getTimestamp("start");
        Timestamp end = rs.getTimestamp("end");

        Date startDate = null;
        Date endDate = null;

        if(start != null)
            startDate = new Date(start.getTime());

        if(end != null)
            endDate = new Date(end.getTime());

        return new Allocation(rs.getString("university_id"),
                              rs.getString("uuid"),
                              startDate,
                              endDate);
    }
}
