package application.repository.scheduledClass;

import application.domain.scheduledClass.ScheduledClass;
import application.domain.scheduledClass.attendance.ClassAttendance;
import application.repository.BaseJDBCRepository;
import application.repository.NaturallyIdentifiableRepository;
import application.repository.scheduledClass.attendance.ClassAttendanceRowMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class ScheduledClassRepository extends BaseJDBCRepository implements NaturallyIdentifiableRepository <ScheduledClass> {

    public List<ScheduledClass> findClassesByStudentUniveristyIdAndModuleCode(String universityId,
                                                                              String moduleCode) {
        String sql = "SELECT class.id, class.uuid, module.id AS module_id, module.module_code, start_date, end_date, room" +
                     "FROM class " +
                        "INNER JOIN module " +
                            "ON class.module_id = module.id " +
                        "INNER JOIN allocation " +
                            "ON class.id = allocation.class_id " +
                        "INNER JOIN student " +
                            "ON allocation.student_id = student.id " +
                        "WHERE " +
                            "student.university_id = ? " +
                            "AND module.module_code = ? ";
        return executor.query(sql, new Object[]{universityId, moduleCode}, new ScheduledClassRowMapper());
    }

    public List<ScheduledClass> findClassesByModuleCode(String moduleCode) {
        String sql = "SELECT class.id, class.uuid, module.id AS module_id, module.module_code, start_date, end_date, room " +
                     "FROM class " +
                        "INNER JOIN module " +
                            "ON class.module_id = module.id " +
                     "WHERE module.module_code = ? ";
        return executor.query(sql, new Object[]{moduleCode}, new ScheduledClassRowMapper());
    }

    public List<ScheduledClass> findClassesByStudentUniveristyId(String universityId) {
        String sql = "SELECT class.id, class.uuid, module.id AS module_id, module.module_code, start_date, end_date, room " +
                     "FROM class " +
                        "INNER JOIN module " +
                            "ON class.module_id = module.id " +
                        "INNER JOIN allocation " +
                            "ON class.id = allocation.class_id " +
                        "INNER JOIN student " +
                            "ON allocation.student_id = student.id " +
                        "WHERE " +
                            "student.university_id = ? ";
        return executor.query(sql, new Object[]{universityId}, new ScheduledClassRowMapper());
    }

    public ScheduledClass findClassByAuthenticationCode(int code) {
        String sql = "SELECT class.id, class.uuid, module.id AS module_id, module.module_code, start_date, end_date, room " +
                     "FROM class " +
                        "INNER JOIN module " +
                            "ON class.module_id = module.id " +
                        "INNER JOIN access_code " +
                            "ON class.id = access_code.class_id " +
                        "WHERE " +
                            "access_code.code = ? ";
        return executor.queryForObject(sql, new Object[]{code}, new ScheduledClassRowMapper());
    }

    public List<ScheduledClass> findCurrentlyRunningClasses() {
        String sql = "SELECT class.id, class.uuid, module.id AS module_id, module.module_code, start_date, end_date, room " +
                "FROM class " +
                "INNER JOIN module " +
                    "ON class.module_id = module.id " +
                "WHERE " +
                    "DATE(start_date) <= DATE(NOW()) " +
                "AND " +
                    "DATE(end_date) >= DATE(NOW()) " +
                "AND " +
                    "DAYOFWEEK(start_date) = DAYOFWEEK(NOW()) " +
                "AND " +
                    "TIME(start_date) <= TIME(NOW()) " +
                "AND " +
                    "TIME(end_date) > TIME(NOW())";
        return executor.query(sql, new ScheduledClassRowMapper());
    }

    public ClassAttendance getClassAttendance(String classUuid, long timestamp) {
        Timestamp timestampObj = new Timestamp(timestamp);
        String sql = "SELECT ? AS class_uuid, DATE(?) AS date, " +
                            "(SELECT COUNT(*) FROM allocation " +
                                "INNER JOIN class ON class_id = class.id " +
                             "WHERE class.uuid = ?) AS allocated, " +
                            "(SELECT COUNT(*) FROM attendance " +
                                "INNER JOIN class ON class_id = class.id " +
                            "WHERE class.uuid = ? AND DATE(date) = DATE(?)) AS attended ";
        return executor.queryForObject(sql, new Object[]{classUuid, timestampObj, classUuid, classUuid, timestampObj}, new ClassAttendanceRowMapper());
    }

    @Override
    public ScheduledClass findByUuid(String uuid) {
        String sql = "SELECT class.id, class.uuid, module.id AS module_id, module.module_code, start_date, end_date, room " +
                "FROM class " +
                "INNER JOIN module " +
                "ON class.module_id = module.id " +
                "WHERE class.uuid = ? ";
        return executor.queryForObject(sql, new Object[]{uuid}, new ScheduledClassRowMapper());
    }

    @Override
    public ScheduledClass findById(Long id) {
        return null;
    }
}
