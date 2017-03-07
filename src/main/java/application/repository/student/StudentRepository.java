package application.repository.student;

import application.domain.student.Student;
import application.domain.student.StudentAttendanceCorrelation;
import application.domain.student.StudentModuleAttendanceCorrelation;
import application.domain.user.User;
import application.repository.BaseJDBCRepository;
import application.repository.IdentifiableRepository;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Student repository for operating on the Student table
 */
@Component
public class StudentRepository extends BaseJDBCRepository implements IdentifiableRepository<Student> {

    public List<Student> findAllAllocatedToAModule(String moduleCode) {
        String sql = "SELECT student_id as studentId, university_id, name, surname, email, pin_salt " +
                       "FROM student " +
                          "INNER JOIN module_student ON student.id = student_id " +
                          "INNER JOIN module ON module.id = module_id " +
                        "WHERE module.module_code = ? ";
        return executor.query(sql, new Object[]{moduleCode.toUpperCase()}, new StudentRowMapper());
    }

    /**
     * Returns a list of students who attended a class
     * and their times of signing into the system.
     * @param classUuid
     * @param timestamp
     * @return List of StudentAttendanceCorrelation
     */
    public List<StudentAttendanceCorrelation> findAllWhoAttendedAClass(String classUuid, long timestamp) {
        Timestamp timestampObj = new Timestamp(timestamp);
        String sql = "SELECT student.id as studentId, university_id, name, surname, email, pin_salt, class.uuid AS class_uuid, attendance.date, module.module_code " +
                     "FROM student " +
                        "INNER JOIN attendance ON student.id = attendance.student_id " +
                        "INNER JOIN class ON attendance.class_id = class.id " +
                        "INNER JOIN module ON class.module_id = module.id " +
                     "WHERE class.uuid = ? AND DATE(attendance.date) = DATE(?) ";
        return executor.query(sql, new Object[]{classUuid, timestampObj}, new StudentAttendanceCorrelationRowMapper());
    }

    /**
     * Returns a list of students who were late for the class
     * and their times of signing in.
     * @param classUuid
     * @param timestamp
     * @return List of StudentAttendanceCorrelation
     */
    public List<StudentAttendanceCorrelation> findAllWhoWereLateForAClass(String classUuid, long timestamp) {
        Timestamp timestampObj = new Timestamp(timestamp);
        String sql = "SELECT student.id as studentId, university_id, name, surname, email, pin_salt, class.uuid AS class_uuid, attendance.date, module.module_code " +
                     "FROM student " +
                        "INNER JOIN attendance ON student.id = attendance.student_id " +
                        "INNER JOIN class ON attendance.class_id = class.id " +
                        "INNER JOIN module ON class.module_id = module.id " +
                     "WHERE class.uuid = ? " +
                     "AND DATE(attendance.date) = DATE(?) " +
                     "AND timediff(attendance.date, class.start_date) > '00:20:00'";
        return executor.query(sql, new Object[]{classUuid, timestampObj}, new StudentAttendanceCorrelationRowMapper());
    }

    /**
     * Returns all students who did not attend the class.
     * @param classUuid
     * @param timestamp
     * @return List of Students
     */
    public List<Student> findAllWhoDidNotAttendAClass(String classUuid, long timestamp) {
        Timestamp timestampObj = new Timestamp(timestamp);
        String sql = "SELECT student_id as studentId, university_id, name, surname, email, pin_salt " +
                     "FROM " +
                     "(SELECT student.id as student_id, university_id, name, surname, email, pin_salt " +
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

    /**
     * Returns correlations of students to modules for a specific class group.
     * It only maps the student to the number of his attendance records to the
     * module's specific group or NONE group classes.
     * The rest of the metrics is calculated in the MetricsService class.
     * @param moduleCode
     * @param groupName
     * @return List of StudentModuleAttendanceCorrelation
     */
    public List<StudentModuleAttendanceCorrelation> findStudentAttendanceCorrelationForModuleGroup(String moduleCode, String groupName) {
        String sql = "SELECT DISTINCT student.id studentId, university_id, name, surname, email, pin_salt, " +
                    "(SELECT COUNT(*) " +
                     "FROM attendance INNER JOIN student ON attendance.student_id = student.id " +
                        "INNER JOIN class ON attendance.class_id = class.id " +
                        "INNER JOIN module ON class.module_id = module.id " +
                     "WHERE student.id = studentId AND module_code = ? AND (group_name = ? OR group_name = 'none')) as count " +
                     "FROM student " +
                        "INNER JOIN allocation ON student.id = allocation.student_id " +
                        "INNER JOIN class ON allocation.class_id = class.id " +
                        "INNER JOIN module ON class.module_id = module.id " +
                     "WHERE module_code = ? AND group_name = ?";
        return executor.query(sql, new Object[]{moduleCode.toUpperCase(), groupName.toUpperCase(), moduleCode.toUpperCase(), groupName.toUpperCase()}, new StudentModuleAttendanceCorrelationRowMapper());
    }

    public List<Student> findStudentsAllocatedToAModuleGroup(String moduleCode, String groupName) {
        String sql = "SELECT DISTINCT student.id studentId, university_id, name, surname, email, pin_salt " +
                     "FROM student " +
                        "INNER JOIN allocation ON student.id = allocation.student_id " +
                        "INNER JOIN class ON allocation.class_id = class.id " +
                        "INNER JOIN module ON class.module_id = module.id " +
                     "WHERE module_code = ? AND group_name = ?";
        return executor.query(sql, new Object[]{moduleCode.toUpperCase(), groupName.toUpperCase()}, new StudentRowMapper());
    }

    public List<Student> findStudentsOnlyAllocatedToNoneGroup(String moduleCode) {
        String sql = "SELECT DISTINCT student.id AS studentId, university_id, name, surname, email, pin_salt " +
                     "FROM student " +
                        "INNER JOIN allocation ON student.id = allocation.student_id " +
                        "INNER JOIN class ON allocation.class_id = class.id " +
                        "INNER JOIN module ON class.module_id = module.id " +
                     "WHERE module_code = ? AND group_name = ('none') " +
                     "AND student.id " +
                        "NOT IN(SELECT DISTINCT student.id FROM student " +
                                    "INNER JOIN allocation ON student.id = allocation.student_id " +
                                    "INNER JOIN class ON allocation.class_id = class.id " +
                                    "INNER JOIN module ON class.module_id = module.id " +
                                "WHERE module_code = ? AND group_name !=('none'))";
        return executor.query(sql, new Object[]{moduleCode.toUpperCase(), moduleCode.toUpperCase()}, new StudentRowMapper());
    }

    /**
     * This method returns students and their attendance who are only allocated to group NONE classes
     * of the given module.
     * @param moduleCode
     * @return List of StudentModuleAttendanceCorrelation
     */
    public List<StudentModuleAttendanceCorrelation> findStudentAttendanceCorrelationForStudentsWithNoGroup(String moduleCode) {
        String sql = "SELECT DISTINCT student.id AS studentId, university_id, name, surname, email, pin_salt, " +
                     "(SELECT COUNT(*) " +
                     "FROM attendance INNER JOIN student ON attendance.student_id = student.id " +
                        "INNER JOIN class ON attendance.class_id = class.id " +
                        "INNER JOIN module ON class.module_id = module.id " +
                     "WHERE student.id = studentId AND module_code = ? AND group_name = 'none') as count FROM student " +
                        "INNER JOIN allocation ON student.id = allocation.student_id " +
                        "INNER JOIN class ON allocation.class_id = class.id " +
                        "INNER JOIN module ON class.module_id = module.id " +
                     "WHERE module_code = ? AND group_name = ('none') and student.id " +
                     "NOT IN(SELECT DISTINCT student.id FROM student " +
                        "INNER JOIN allocation ON student.id = allocation.student_id " +
                        "INNER JOIN class ON allocation.class_id = class.id " +
                        "INNER JOIN module ON class.module_id = module.id " +
                     "WHERE module_code = ? AND group_name !=('none'))";
        return executor.query(sql, new Object[]{moduleCode.toUpperCase(), moduleCode.toUpperCase(), moduleCode.toUpperCase()}, new StudentModuleAttendanceCorrelationRowMapper());
    }

    /**
     * This method returns a student with the given university id
     * and pin combination.
     * @param universityId
     * @param pin
     * @return Student
     */
    public Student findByUniversityIdAndPin(String universityId, String pin) {
        String sql = "SELECT id as studentId, university_id, name, surname, email, pin_salt " +
                     "FROM student " +
                     "WHERE university_id = ? AND pin_salt = ?";
        return executor.queryForObject(sql,
                new Object[]{universityId, pin}, new StudentRowMapper());
    }

    /**
     * This method returns a student with the given university id.
     * @param universityId
     * @return Student
     */
    public Student findByUniversityId(String universityId) {
        String sql = "SELECT id as studentId, university_id, name, surname, email, pin_salt " +
                "FROM student " +
                "WHERE university_id = ?";
        return executor.queryForObject(sql,
                new Object[]{universityId}, new StudentRowMapper());
    }

    /**
     * This method returns all the students.
     * @return List of Students
     */
    public List<Student> findAll() {
        String sql = "SELECT student.id as studentId, university_id, name, surname, email, pin_salt " +
                     "FROM student";
        return executor.query(sql, new StudentRowMapper());
    }

    public String resetStudentPin(String id, String pin) {
        String sql = "UPDATE student "+
                "SET pin_salt = ? "+
                "WHERE university_id = ? ";
        if(executor.update(sql,
                new Object[]{pin, id}) ==1)
            return pin;
        else
            return null;
    }

    public Student updateStudentDetails(Student student) {
        String sql = "UPDATE student "+
                "SET name = ?, " +
                "surname = ?, " +
                "email = ? " +
                "WHERE university_id = ? ";
        if(executor.update(sql,
                new Object[]{
                        student.getName(),
                        student.getSurname(),
                        student.getEmail(),
                        student.getUniversityId()}) ==1)
            return findByUniversityId(student.getUniversityId());
        else
            return null;
    }

    /**
     * This method returns a student with the given id.
     * @param id
     * @return Student
     */
    @Override
    public Student findById(Long id) {
        String sql = "SELECT student.id as studentId, university_id, name, surname, email, pin_salt " +
                     "FROM student WHERE id = ?";
        return executor.queryForObject(sql,
                new Object[]{id}, new StudentRowMapper());
    }

    public Student saveStudent(Student student) {
        String sql = "INSERT INTO student(university_id, name, surname, email, pin_salt) " +
                     "VALUES(?, ?, ?, ?, ?)";
        if(executor.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, student.getUniversityId().toLowerCase());
                ps.setString(2, student.getName());
                ps.setString(3, student.getSurname());
                ps.setString(4, student.getEmail());
                ps.setString(5, student.getPin());
            }
        }) == 1) {
            student.setId(findByUniversityId(student.getUniversityId()).getId());
            return student;
        }
        else
            return null;
    }
}
