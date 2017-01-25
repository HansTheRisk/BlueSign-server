package main.application.repository.allocation;

import main.application.domain.allocation.Allocation;
import main.application.repository.BaseJDBCRepository;
import org.springframework.stereotype.Component;

@Component
public class AllocationRepository extends BaseJDBCRepository {

    public Allocation findAllocationByStudentIdAndClassUuid(String studentUniversityId, String classUuid) {
        String sql = "SELECT student.university_id, class.uuid " +
                     "FROM allocation INNER JOIN student " +
                        "ON allocation.student_id = student.id " +
                     "INNER JOIN class " +
                        "ON allocation.class_id = class.id " +
                     "WHERE university_id = ? " +
                     "AND uuid = ? ";
        return executor.queryForObject(sql, new Object[]{studentUniversityId, classUuid}, new AllocationRowMapper());
    }

}
