package application.repository.module;

import application.domain.module.Module;
import application.domain.user.User;
import application.repository.BaseJDBCRepository;
import application.repository.IdentifiableRepository;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Repository for operating on the module table.
 */
@Component
public class ModuleRepository extends BaseJDBCRepository implements IdentifiableRepository<Module> {

    /**
     * This method returns all the modules of the student by class allocation.
     * @param universityId
     * @return List of Modules
     */
    public List<Module> getModulesForStudent(String universityId) {
        String sql = "SELECT module.id, title, module_code, user.uuid AS lecturer_uuid " +
                     "FROM module " +
                        "INNER JOIN class ON " +
                            "module.id = class.module_id " +
                        "INNER JOIN allocation ON " +
                            "class.id = allocation.class_id " +
                        "INNER JOIN student ON " +
                            "allocation.student_id = student.id " +
                        "INNER JOIN user ON " +
                            "module.lecturer_id = user.id " +
                    "WHERE student.university_id = ? AND allocation.end IS NULL " +
                    "GROUP BY module.id";
        return executor.query(sql, new Object[]{universityId}, new ModuleRowMapper());
    }

    /**
     * This method returns all the modules for a lecturer.
     * @param uuid
     * @return List of Modules
     */
    public List<Module> getModulesForLecturer(String uuid) {
        String sql = "SELECT module.id, title, module_code, user.uuid AS lecturer_uuid " +
                     "FROM module " +
                        "INNER JOIN user ON " +
                            "module.lecturer_id = user.id " +
                        "WHERE user.uuid = ? ";
        return executor.query(sql, new Object[]{uuid}, new ModuleRowMapper());
    }

    /**
     * This method returns a module by its code.
     * @param moduleCode
     * @return Module
     */
    public Module getByModuleCode(String moduleCode) {
        String sql = "SELECT module.id, title, module_code, user.uuid AS lecturer_uuid " +
                     "FROM module " +
                     "INNER JOIN user ON " +
                        "module.lecturer_id = user.id " +
                     "WHERE module_code = ? ";
        return executor.queryForObject(sql, new Object[]{moduleCode.toUpperCase()}, new ModuleRowMapper());
    }

    public List<ModuleGroup> getModuleGroups(String moduleCode) {
        String sql = "SELECT DISTINCT group_name " +
                     "FROM class INNER JOIN module " +
                        "ON class.module_id = module.id " +
                     "WHERE module_code = ? ";
        return executor.query(sql, new Object[]{moduleCode.toUpperCase()}, new ModuleGroupRowMapper());
    }

    public List<Module> findAll() {
        String sql = "SELECT module.id, title, module_code, user.uuid AS lecturer_uuid " +
                     "FROM module " +
                     "INNER JOIN user ON " +
                        "module.lecturer_id = user.id ";
        return executor.query(sql, new Object[]{}, new ModuleRowMapper());
    }

    /**
     * This method returns a module by its id.
     * @param id
     * @return Module
     */
    @Override
    public Module findById(Long id) {
        String SQL = "SELECT * FROM module WHERE id = ?";
        Module module = executor.queryForObject(SQL,
                new Object[]{id}, new ModuleRowMapper());
        return module;
    }

    public Module saveModule(Module module) {
        String sql = "INSERT INTO module(title, module_code, lecturer_id) " +
                "VALUES(?, ?, (SELECT id FROM user WHERE uuid = ?))";
        if(executor.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, module.getTitle());
                ps.setString(2, module.getModuleCode().toUpperCase());
                ps.setString(3, module.getLecturerUuid());
            }
        }) == 1)
            return getByModuleCode(module.getModuleCode());
        else
            return null;
    }

    public boolean insertModuleAllocations(String moduleCode, List<String> studentIds) {
        String sql = "INSERT INTO module_student(module_id, student_id) " +
                "VALUES((SELECT id FROM module WHERE module_code = ?), " +
                "(SELECT id FROM student WHERE university_id = ?))";
        return Arrays.asList(executor.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, moduleCode);
                ps.setString(2, studentIds.get(i));
            }

            @Override
            public int getBatchSize() {
                return studentIds.size();
            }
        })).contains(0) ? false : true;
    }

    public Module updateModuleDetails(Module module) {
        String sql = "UPDATE module "+
                "SET title = ?, " +
                "lecturer_id = (SELECT id FROM user WHERE uuid = ?), " +
                "WHERE module_code = ? ";
        if(executor.update(sql,
                new Object[]{module.getTitle(),
                        module.getLecturerUuid(), module.getModuleCode().toUpperCase()}) ==1)
            return getByModuleCode(module.getModuleCode().toUpperCase());
        else
            return null;
    }

    public boolean removeStudentsAllocationsToModules(String studentUniversityId) {
        String sql = "DELETE module_student " +
                "FROM module_student " +
                "INNER JOIN student ON module_student.student_id = student.id " +
                "WHERE student.university_id = ?";
        return executor.update(sql, new Object[]{studentUniversityId}) == 1 ? true : false;
    }

    public boolean removeStudentsAllocationToModule(String moduleCode, String studentUniversityId) {
        String sql = "DELETE module_student " +
                "FROM module_student " +
                "INNER JOIN student ON module_student.student_id = student.id " +
                "INNER JOIN module ON module_student.module_id = module.id " +
                "WHERE student.university_id = ? AND module.module_code = ?";
        return executor.update(sql, new Object[]{studentUniversityId, moduleCode.toUpperCase()}) == 1 ? true : false;
    }

    public boolean removeModuleAllocations(String moduleCode) {
        String sql = "DELETE module_student " +
                     "FROM module_student " +
                        "INNER JOIN module ON module_student.module_id = module.id " +
                     "WHERE module.module_code = ?";
        return executor.update(sql, new Object[]{moduleCode.toUpperCase()}) == 1 ? true : false;
    }

    public boolean removeModule(String moduleCode) {
        String sql = "DELETE FROM module WHERE module_code = ?";
        return executor.update(sql, new Object[]{moduleCode.toUpperCase()}) == 1 ? true : false;
    }
}
