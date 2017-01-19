package main.application.repository.scheduledClass;

import main.application.domain.scheduledClass.ScheduledClass;
import main.application.repository.NaturallyIdentifiableRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ScheduledClassRowMapper implements NaturallyIdentifiableRowMapper<ScheduledClass> {
    @Override
    public ScheduledClass mapRow(ResultSet rs, int rowNum) throws SQLException {
        Date startDate = new Date(rs.getTimestamp("start_date").getTime());
        Date endDate = new Date(rs.getTimestamp("end_date").getTime());

        ScheduledClass sclass = new ScheduledClass(rs.getString("module_code"),
                                                                startDate,
                                                                endDate);
        sclass.setId(rs.getLong("id"));
        sclass.setUuid(rs.getString("uuid"));
        return sclass;
    }
}
