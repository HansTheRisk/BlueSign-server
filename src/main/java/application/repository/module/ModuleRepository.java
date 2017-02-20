package application.repository.module;

import application.domain.module.Module;
import application.repository.BaseJDBCRepository;
import application.repository.IdentifiableRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Repository for operating on the module table.
 */
@Component
public class ModuleRepository extends BaseJDBCRepository implements IdentifiableRepository<Module> {

    /**
     * This method returns all the modules of the student.
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
                    "WHERE student.university_id = ? " +
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
        return executor.queryForObject(sql, new Object[]{moduleCode}, new ModuleRowMapper());
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
}
