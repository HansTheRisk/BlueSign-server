package application.repository.attendance;

import application.domain.attendance.Attendance;
import application.repository.BaseJDBCRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * This repository allows for operating on the Attendance table.
 */
@Component
public class AttendanceRepository extends BaseJDBCRepository {

    /**
     * Retrieves all the attendance records for a student.
     * @param universityId
     * @return List of Attendance
     */
    public List<Attendance> getAttendanceRecordsForStudent(String universityId) {
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

    /**
     * Retrieves all the attendance records for a module.
     * @param moduleCode
     * @return List of Attendance
     */
    public List<Attendance> getAttendanceRecordsForModule(String moduleCode) {
        String sql = "SELECT student.university_id, class.uuid AS class_uuid, attendance.date, module.module_code " +
                "FROM attendance INNER JOIN student " +
                "   ON attendance.student_id = student.id " +
                "INNER JOIN class " +
                "   ON attendance.class_id = class.id " +
                "INNER JOIN module ON " +
                "   class.module_id = module.id " +
                "WHERE module_code = ?";
        return executor.query(sql, new Object[]{moduleCode}, new AttendanceRowMapper());
    }

    /**
     * Retrieves an attendance record by universityId, classUuid and date.
     * Existential check.
     * @param universityId
     * @param classUuid
     * @param date
     * @return Attendance
     */
    public Attendance checkIfAttendanceExists(String universityId, String classUuid, Date date) {
        String sql = "SELECT student.university_id, class.uuid AS class_uuid, attendance.date, module.module_code " +
                "FROM attendance INNER JOIN student " +
                "   ON attendance.student_id = student.id " +
                "INNER JOIN class " +
                "   ON attendance.class_id = class.id " +
                "INNER JOIN module ON " +
                "   class.module_id = module.id " +
                "WHERE university_id = ? " +
                "AND class.uuid = ? " +
                "AND DATE(attendance.date) = DATE(?) ";
        return executor.queryForObject(sql, new Object[]{universityId, classUuid, date}, new AttendanceRowMapper());
    }

    /**
     * Inserts a new attendance record into the database.
     * @param attendance
     * @return int
     */
    @Transactional(rollbackFor = DataAccessException.class)
    public boolean insertAttendance(Attendance attendance) {
        String sql = "INSERT INTO attendance(student_id, class_id, date) " +
                     "VALUES((SELECT id FROM student WHERE university_id = ?), " +
                            "(SELECT id FROM class WHERE uuid = ?), ?)";
        return executor.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, attendance.getStudentUniversityId());
                ps.setString(2, attendance.getClassUuid());
                ps.setTimestamp(3, new Timestamp(attendance.getDate().getTime()));
            }
        }) == 1 ? true : false;
    }

    @Transactional(rollbackFor = DataAccessException.class)
    public boolean deleteAttendanceRecordsForModule(String moduleCode) {
        String sql = "DELETE attendance" +
                     "FROM attendance " +
                        "INNER JOIN class ON attendance.class_id = class.id " +
                        "INNER JOIN module ON class.module_id = module.id " +
                     "WHERE module.module_code = ?";
        return executor.update(sql, new Object[]{moduleCode.toUpperCase()}) == 1 ? true : false;
    }

}
