package main.application.repository.attendance;

import main.application.domain.attendance.Attendance;
import main.application.repository.BaseJDBCRepository;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class AttendanceRepository extends BaseJDBCRepository {

    public List<Attendance> getAttendanceForStudent(String universityId) {
        String sql = "SELECT student.university_id, class.uuid AS class_uuid, attendance.date, module.module_code " +
                "FROM attendance INNER JOIN student " +
                "   ON attendance.student_id = student.id " +
                "INNER JOIN class " +
                "   ON attendance.class_id = class.id " +
                "INNER JOIN module ON " +
                "   class.module_id = module.id " +
                "WHERE university_id = ?";
        return executor.query(sql, new Object[]{universityId}, new AttendanceRowMapper());
    }

    public void insertAttendance(Attendance attendance) {
        String sql = "INSERT INTO attendance(student_id, class_id, date) " +
                     "VALUES((SELECT id FROM student WHERE university_id = ?), " +
                            "(SELECT id FROM class WHERE uuid = ?), ?)";
        executor.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, attendance.getStudentUniversityId());
                ps.setString(2, attendance.getClassUuid());
                ps.setDate(3, new Date(attendance.getDate().getTime()));
            }
        });
    }

}
