package application.repository.scheduledClass;

import application.domain.scheduledClass.ScheduledClass;
import application.repository.NaturallyIdentifiableRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * This row mapper maps class database rows to ScheduledClass objects.
 */
public class ScheduledClassRowMapper implements NaturallyIdentifiableRowMapper<ScheduledClass> {
    @Override
    public ScheduledClass mapRow(ResultSet rs, int rowNum) throws SQLException {
        Date startDate = new Date(rs.getTimestamp("start_date").getTime());
        Date endDate = new Date(rs.getTimestamp("end_date").getTime());

        ScheduledClass sclass = new ScheduledClass(rs.getString("module_code"),
                                                                startDate,
                                                                endDate);
        sclass.setId(rs.getLong("cl_id"));
        sclass.setUuid(rs.getString("uuid"));
        sclass.setRoom(rs.getString("room"));
        sclass.setAllocated(rs.getLong("allocated"));
        sclass.setGroup(rs.getString("group_name"));
        return sclass;
    }
}
