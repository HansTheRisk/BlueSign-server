package main.application.repository.module;

import main.application.domain.module.Module;
import main.application.repository.BaseJDBCRepository;
import main.application.repository.IdentifiableRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModuleRepository extends BaseJDBCRepository implements IdentifiableRepository<Module> {

    public List<Module> getModules(String universityId) {
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

    @Override
    public Module findById(Long id) {
        String SQL = "SELECT * FROM module WHERE id = ?";
        Module module = executor.queryForObject(SQL,
                new Object[]{id}, new ModuleRowMapper());
        return module;
    }
}
