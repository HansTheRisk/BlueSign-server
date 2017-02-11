package application.repository.student;

import application.domain.student.Student;
import application.domain.student.StudentAttendanceCorrelation;
import application.repository.BaseJDBCRepository;
import application.repository.IdentifiableRepository;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Component
public class StudentRepository extends BaseJDBCRepository implements IdentifiableRepository<Student> {

    public List<StudentAttendanceCorrelation> findAllWhoAttendedAClass(String classUuid, long timestamp) {
        Timestamp timestampObj = new Timestamp(timestamp);
        String sql = "SELECT student.id, university_id, name, surname, pin_salt, class.uuid AS class_uuid, attendance.date, module.module_code " +
                     "FROM student " +
                        "INNER JOIN attendance ON student.id = attendance.student_id " +
                        "INNER JOIN class ON attendance.class_id = class.id " +
                        "INNER JOIN module ON class.module_id = module.id " +
                     "WHERE class.uuid = ? AND DATE(attendance.date) = DATE(?) ";
        return executor.query(sql, new Object[]{classUuid, timestampObj}, new StudentAttendanceCorrelationRowMapper());
    }

    public List<StudentAttendanceCorrelation> findAllWhoWereLateForAClass(String classUuid, long timestamp) {
        Timestamp timestampObj = new Timestamp(timestamp);
        String sql = "SELECT student.id, university_id, name, surname, pin_salt, class.uuid AS class_uuid, attendance.date, module.module_code " +
                     "FROM student " +
                        "INNER JOIN attendance ON student.id = attendance.student_id " +
                        "INNER JOIN class ON attendance.class_id = class.id " +
                        "INNER JOIN module ON class.module_id = module.id " +
                     "WHERE class.uuid = ? " +
                     "AND DATE(attendance.date) = DATE(?) " +
                     "AND timediff(attendance.date, class.start_date) > '00:20:00'";
        return executor.query(sql, new Object[]{classUuid, timestampObj}, new StudentAttendanceCorrelationRowMapper());
    }

    public List<Student> findAllWhoDidNotAttendAClass(String classUuid, long timestamp) {
        Timestamp timestampObj = new Timestamp(timestamp);
        String sql = "SELECT student_id as id, university_id, name, surname, pin_salt " +
                     "FROM " +
                     "(SELECT student.id as student_id, university_id, name, surname, pin_salt " +
                      "FROM student " +
                        "INNER JOIN allocation ON student.id = allocation.student_id " +
                        "INNER JOIN class ON allocation.class_id = class.id " +
                      "WHERE class.uuid = ?) allocated " +
                        "LEFT JOIN (SELECT student.id FROM student " +
                        "INNER JOIN attendance ON student.id = attendance.student_id " +
                        "INNER JOIN class ON attendance.class_id = class.id " +
                      "WHERE class.uuid = ? " +
                      "AND DATE(attendance.date) = DATE(?)) attended ON " +
                                           "attended.id = allocated.student_id WHERE attended.id IS NULL";
        return executor.query(sql, new Object[]{classUuid, classUuid, timestampObj}, new StudentRowMapper());
    }

    public Student findByUniversityIdAndPin(String universityId, String pin) {
        String sql = "SELECT id, university_id, name, surname, pin_salt " +
                     "FROM student " +
                     "WHERE university_id = ? AND pin_salt = ?";
        return executor.queryForObject(sql,
                new Object[]{universityId, pin}, new StudentRowMapper());
    }

    public List<Student> findAll() {
        String sql = "SELECT student.id, university_id, name, surname, pin_salt " +
                     "FROM student";
        return executor.query(sql, new StudentRowMapper());
    }

    @Override
    public Student findById(Long id) {
        String sql = "SELECT student.id, university_id, name, surname, pin_salt " +
                     "FROM student WHERE id = ?";
        return executor.queryForObject(sql,
                new Object[]{id}, new StudentRowMapper());
    }
}
