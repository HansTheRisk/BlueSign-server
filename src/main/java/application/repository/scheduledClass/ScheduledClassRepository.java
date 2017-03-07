package application.repository.scheduledClass;

import application.domain.scheduledClass.ScheduledClass;
import application.domain.scheduledClass.attendance.CompletedClassAttendance;
import application.repository.BaseJDBCRepository;
import application.repository.NaturallyIdentifiableRepository;
import application.repository.scheduledClass.attendance.ClassAttendanceRowMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * Repository class for operating on the class database table.
 */
@Component
public class ScheduledClassRepository extends BaseJDBCRepository implements NaturallyIdentifiableRepository <ScheduledClass> {

    /**
     * This method returns classes for allocated to a student with the given module.
     * @param universityId
     * @param moduleCode
     * @return List of ScheduledClasses
     */
    public List<ScheduledClass> findClassesByStudentUniveristyIdAndModuleCode(String universityId,
                                                                              String moduleCode) {
        String sql = "SELECT class.id AS cl_id, class.uuid, module.id AS module_id, module.module_code, start_date, end_date, room, group_name, " +
                    "(SELECT COUNT(*) " +
                     "FROM class INNER JOIN allocation " +
                        "ON class.id = allocation.class_id " +
                     "WHERE class.id = cl_id) as allocated " +
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
        return executor.query(sql, new Object[]{universityId, moduleCode.toLowerCase()}, new ScheduledClassRowMapper());
    }

    /**
     * This method returns classes belonging to the module
     * with the given module code.
     * @param moduleCode
     * @return List of ScheduledClasses
     */
    public List<ScheduledClass> findClassesByModuleCode(String moduleCode) {
        String sql = "SELECT class.id AS cl_id, class.uuid, module.id AS module_id, module.module_code, start_date, end_date, room, group_name, " +
                    "(SELECT COUNT(*) " +
                        "FROM class INNER JOIN allocation " +
                     "ON class.id = allocation.class_id " +
                     "WHERE class.id = cl_id) as allocated " +
                     "FROM class " +
                        "INNER JOIN module " +
                            "ON class.module_id = module.id " +
                     "WHERE module.module_code = ? ";
        return executor.query(sql, new Object[]{moduleCode.toLowerCase()}, new ScheduledClassRowMapper());
    }

    /**
     * This method returns all classes allocated to the student with the
     * given university id.
     * @param universityId
     * @return List of ScheduledClasses
     */
    public List<ScheduledClass> findClassesByStudentUniveristyId(String universityId) {
        String sql = "SELECT class.id AS cl_id, class.uuid, module.id AS module_id, module.module_code, start_date, end_date, room, group_name, " +
                    "(SELECT COUNT(*) " +
                     "FROM class INNER JOIN allocation " +
                        "ON class.id = allocation.class_id " +
                     "WHERE class.id = cl_id) as allocated " +
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

    /**
     * This method returns the class by its time-framed access code
     * @param code
     * @return ScheduledClass
     */
    public ScheduledClass findClassByAuthenticationCode(int code) {
        String sql = "SELECT class.id AS cl_id, class.uuid, module.id AS module_id, module.module_code, start_date, end_date, room, group_name, " +
                    "(SELECT COUNT(*) " +
                     "FROM class INNER JOIN allocation " +
                        "ON class.id = allocation.class_id " +
                     "WHERE class.id = cl_id) as allocated " +
                     "FROM class " +
                        "INNER JOIN module " +
                            "ON class.module_id = module.id " +
                        "INNER JOIN access_code " +
                            "ON class.id = access_code.class_id " +
                        "WHERE " +
                            "access_code.code = ? ";
        return executor.queryForObject(sql, new Object[]{code}, new ScheduledClassRowMapper());
    }

    /**
     * This method returns all the currently running classes.
     * @return List of ScheduledClasses
     */
    public List<ScheduledClass> findCurrentlyRunningClasses() {
        String sql = "SELECT class.id AS cl_id, class.uuid, module.id AS module_id, module.module_code, start_date, end_date, room, group_name, " +
                    "(SELECT COUNT(*) " +
                     "FROM class INNER JOIN allocation " +
                        "ON class.id = allocation.class_id " +
                     "WHERE class.id = cl_id) as allocated " +
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

    /**
     * This method returns attendance for a completed class
     * identified by the class UUID and the date of completion.
     * @param classUuid
     * @param timestamp
     * @return CompletedClassAttendance
     */
    public CompletedClassAttendance getCompletedClassAttendance(String classUuid, long timestamp) {
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

    /**
     * This method returns a class identified by the given UUID
     * @param uuid
     * @return ScheduledClass
     */
    @Override
    public ScheduledClass findByUuid(String uuid) {
        String sql = "SELECT class.id AS cl_id, class.uuid, module.id AS module_id, module.module_code, start_date, end_date, room, group_name, " +
                    "(SELECT COUNT(*) " +
                     "FROM class INNER JOIN allocation " +
                        "ON class.id = allocation.class_id " +
                     "WHERE class.id = cl_id) as allocated " +
                "FROM class " +
                "INNER JOIN module " +
                "ON class.module_id = module.id " +
                "WHERE class.uuid = ? ";
        return executor.queryForObject(sql, new Object[]{uuid}, new ScheduledClassRowMapper());
    }

    /**
     * This method returns a class by its id.
     * @param id
     * @return ScheduledClass
     */
    @Override
    public ScheduledClass findById(Long id) {
        return null;
    }
}
