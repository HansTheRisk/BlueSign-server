package application.repository.scheduledClass.attendance;

import application.domain.scheduledClass.attendance.CompletedClassAttendance;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * This row mapper maps results of ClassAttendance queries to the
 * ClassAttendance objects.
 */
public class ClassAttendanceRowMapper implements RowMapper<CompletedClassAttendance>{
    @Override
    public CompletedClassAttendance mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CompletedClassAttendance(rs.getString("class_uuid"),
                new Date(rs.getTimestamp("date").getTime()),
                rs.getInt("allocated"),
                rs.getInt("attended"));
    }
}
