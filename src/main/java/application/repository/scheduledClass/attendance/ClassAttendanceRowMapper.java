package application.repository.scheduledClass.attendance;

import application.domain.scheduledClass.attendance.ClassAttendance;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ClassAttendanceRowMapper implements RowMapper<ClassAttendance>{
    @Override
    public ClassAttendance mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ClassAttendance(rs.getString("class_uuid"),
                new Date(rs.getTimestamp("date").getTime()),
                rs.getInt("allocated"),
                rs.getInt("attended"));
    }
}
