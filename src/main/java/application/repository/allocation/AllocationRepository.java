package application.repository.allocation;

import application.domain.allocation.Allocation;
import application.repository.BaseJDBCRepository;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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

    /**
     * This method inserts access codes into the database.
     * @param allocations
     */
    public int[] insertAllocations(List<Allocation> allocations) {
        String sql = "INSERT INTO allocation(student_id, class_id) " +
                "VALUES((SELECT id FROM student WHERE university_id = ?), " +
                       "(SELECT id FROM class WHERE uuid = ?))";
        return executor.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, allocations.get(i).getStudentUniversityId());
                ps.setString(2, allocations.get(i).getClassUuid());
            }

            @Override
            public int getBatchSize() {
                return allocations.size();
            }
        });
    }

}
