package main.application.repository.scheduledClass;

import main.application.domain.scheduledClass.ScheduledClass;
import main.application.repository.BaseJDBCRepository;
import main.application.repository.NaturallyIdentifiableRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledClassRepository extends BaseJDBCRepository implements NaturallyIdentifiableRepository <ScheduledClass> {

    public List<ScheduledClass> findClassesByStudentUniveristyIdAndModuleUuid(String universityId,
                                                                              String moduleCode) {
        String sql = "SELECT class.id, class.uuid, module.id AS module_id, module.module_code, start_date, end_date " +
                     "FROM class " +
                        "INNER JOIN module " +
                            "ON class.module_id = module.id " +
                        "INNER JOIN allocation " +
                            "ON class.id = allocation.class_id " +
                        "INNER JOIN student " +
                            "ON allocation.student_id = student.id " +
                        "WHERE " +
                            "student.university_id = ? " +
                            "AND module.module_code = ?";
        return executor.query(sql, new Object[]{universityId, moduleCode}, new ScheduledClassRowMapper());
    }

    public List<ScheduledClass> findClassesByStudentUniveristyId(String universityId) {
        String sql = "SELECT class.id, class.uuid, module.id AS module_id, module.module_code, start_date, end_date " +
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

    @Override
    public ScheduledClass findByUuid(String uuid) {
        return null;
    }

    @Override
    public ScheduledClass findById(Long id) {
        return null;
    }
}
