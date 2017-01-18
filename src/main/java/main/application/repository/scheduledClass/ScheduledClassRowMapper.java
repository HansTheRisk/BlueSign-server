package main.application.repository.scheduledClass;

import main.application.domain.scheduledClass.ScheduledClass;
import main.application.repository.NaturallyIdentifiableRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.Date;

public class ScheduledClassRowMapper implements NaturallyIdentifiableRowMapper<ScheduledClass> {
    @Override
    public ScheduledClass mapRow(ResultSet rs, int rowNum) throws SQLException {
        Date startDate = new Date(rs.getDate("start_date").getTime());
        Date endDate = new Date(rs.getDate("end_date").getTime());

        ScheduledClass sclass = new ScheduledClass(rs.getString("module_code"),
                                                                DayOfWeek.valueOf(rs.getString("day")),
                                                                startDate,
                                                                endDate,
                                                                rs.getTime("start_time"),
                                                                rs.getTime("end_time"));
        sclass.setId(rs.getLong("id"));
        sclass.setUuid(rs.getString("uuid"));
        return sclass;
    }
}
