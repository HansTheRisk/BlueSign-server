package application.repository.scheduledClass;

import application.domain.scheduledClass.ScheduledClass;
import application.domain.scheduledClass.attendance.CompletedClassAttendance;
import application.repository.BaseJDBCRepository;
import application.repository.NaturallyIdentifiableRepository;
import application.repository.scheduledClass.attendance.ClassAttendanceRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

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
                     "WHERE class.id = cl_id AND allocation.end IS NULL) as allocated " +
                     "FROM class " +
                        "INNER JOIN module " +
                            "ON class.module_id = module.id " +
                        "INNER JOIN allocation " +
                            "ON class.id = allocation.class_id " +
                        "INNER JOIN student " +
                            "ON allocation.student_id = student.id " +
                        "WHERE " +
                            "student.university_id = ? " +
                            "AND module.module_code = ? " +
                            "AND allocation.end IS NULL";
        return executor.query(sql, new Object[]{universityId, moduleCode.toUpperCase()}, new ScheduledClassRowMapper());
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
                     "WHERE class.id = cl_id AND allocation.end IS NULL) as allocated " +
                     "FROM class " +
                        "INNER JOIN module " +
                            "ON class.module_id = module.id " +
                     "WHERE module.module_code = ? ";
        return executor.query(sql, new Object[]{moduleCode.toUpperCase()}, new ScheduledClassRowMapper());
    }

    public List<ScheduledClass> findClassesOfModuleGroup(String moduleCode, String group) {
        String sql = "SELECT class.id AS cl_id, class.uuid, module.id AS module_id, module.module_code, start_date, end_date, room, group_name, " +
                "(SELECT COUNT(*) " +
                    "FROM class INNER JOIN allocation " +
                        "ON class.id = allocation.class_id " +
                    "WHERE class.id = cl_id AND allocation.end IS NULL) as allocated " +
                "FROM class INNER JOIN module " +
                    "ON class.module_id = module.id " +
                "WHERE module.module_code = ? AND (class.group_name = ? OR class.group_name = 'NONE') ";
        return executor.query(sql, new Object[]{moduleCode.toUpperCase(), group.toUpperCase()}, new ScheduledClassRowMapper());
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
                     "WHERE class.id = cl_id AND allocation.end IS NULL) as allocated " +
                     "FROM class " +
                        "INNER JOIN module " +
                            "ON class.module_id = module.id " +
                        "INNER JOIN allocation " +
                            "ON class.id = allocation.class_id " +
                        "INNER JOIN student " +
                            "ON allocation.student_id = student.id " +
                        "WHERE " +
                            "student.university_id = ? AND allocation.end IS NULL";
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
                     "WHERE class.id = cl_id AND allocation.end IS NULL) as allocated " +
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
                     "WHERE class.id = cl_id AND allocation.end IS NULL) as allocated " +
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
                             "WHERE class.uuid = ?" +
                                "AND (TIMESTAMP(allocation.start) < TIMESTAMP(DATE(?), TIME(class.end_date))" +
                                "AND (allocation.end IS NULL OR (TIMESTAMP(allocation.end) > TIMESTAMP(DATE(?), TIME(class.start_date)))))) AS allocated, " +
                            "(SELECT COUNT(*) FROM attendance " +
                                "INNER JOIN class ON class_id = class.id " +
                            "WHERE class.uuid = ? AND DATE(date) = DATE(?)) AS attended ";
        return executor.queryForObject(sql, new Object[]{classUuid, timestampObj, classUuid, timestampObj, timestampObj, classUuid, timestampObj}, new ClassAttendanceRowMapper());
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
                     "WHERE class.id = cl_id AND allocation.end IS NULL) as allocated " +
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

    public ScheduledClass saveClass(ScheduledClass sclass) {
        sclass.setUuid(UUID.randomUUID().toString());
        String sql = "INSERT INTO class(uuid, module_id, start_date, end_date, room, group_name) " +
                "VALUES(?, (SELECT id FROM module WHERE module_code = ?), ?, ?, ?, ?)";
        if(executor.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, sclass.getUuid());
                ps.setString(2, sclass.getModuleCode().toUpperCase());
                ps.setTimestamp(3, new Timestamp(sclass.getStartDate().getTime()));
                ps.setTimestamp(4, new Timestamp(sclass.getEndDate().getTime()));
                ps.setString(5, sclass.getRoom().toUpperCase());
                ps.setString(6, sclass.getGroup().toUpperCase());
            }
        }) == 1)
            return findByUuid(sclass.getUuid());
        else
            return null;
    }

    public boolean deleteModulesClasses(String moduleCode) {
        String sql = "DELETE class " +
                     "FROM class " +
                        "INNER JOIN module ON class.module_id = module.id " +
                     "WHERE module.module_code = ?";
        return executor.update(sql, new Object[]{moduleCode.toUpperCase()}) == 1 ? true : false;
    }

    public boolean removeClass(String classUuid) {
        String sql = "DELETE FROM class WHERE uuid = ?";
        return executor.update(sql, new Object[]{classUuid}) == 1 ? true : false;
    }
}
