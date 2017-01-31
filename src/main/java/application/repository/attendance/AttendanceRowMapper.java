package application.repository.attendance;

import application.domain.attendance.Attendance;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class AttendanceRowMapper implements RowMapper<Attendance>{
    @Override
    public Attendance mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Attendance(rs.getString("university_id"),
                              rs.getString("class_uuid"),
                              new Date(rs.getTimestamp("date").getTime()),
                              rs.getString("module_code"));
    }
}
