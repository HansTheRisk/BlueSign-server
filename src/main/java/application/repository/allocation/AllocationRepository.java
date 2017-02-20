package application.repository.allocation;

import application.domain.allocation.Allocation;
import application.repository.BaseJDBCRepository;
import org.springframework.stereotype.Component;

/**
 * Repository used to operate on allocation records in the database.
 */
@Component
public class AllocationRepository extends BaseJDBCRepository {

    /**
     * Finds class allocation by studentUniversityId and classUuid.
     * Existential check.
     * @param studentUniversityId
     * @param classUuid
     * @return Allocation
     */
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
