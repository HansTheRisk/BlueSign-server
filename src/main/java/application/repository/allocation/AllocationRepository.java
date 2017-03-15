package application.repository.allocation;

import application.domain.allocation.Allocation;
import application.repository.BaseJDBCRepository;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
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
        String sql = "SELECT student.university_id, class.uuid, start, end " +
                     "FROM allocation INNER JOIN student " +
                        "ON allocation.student_id = student.id " +
                     "INNER JOIN class " +
                        "ON allocation.class_id = class.id " +
                     "WHERE university_id = ? " +
                     "AND uuid = ? AND allocation.end IS NULL";
        return executor.queryForObject(sql, new Object[]{studentUniversityId, classUuid}, new AllocationRowMapper());
    }

    public List<Allocation> findAllModulesClassesAllocations(String moduleCode) {
        String sql = "SELECT student.university_id, class.uuid, start, end " +
                     "FROM allocation " +
                        "INNER JOIN student ON allocation.student_id = student.id " +
                        "INNER JOIN class ON allocation.class_id = class.id " +
                        "INNER JOIN module ON class.module_id = module.id " +
                     "WHERE module_code = ?";
        return executor.query(sql, new Object[]{moduleCode.toUpperCase()}, new AllocationRowMapper());
    }

    public int[] insertAllocations(List<Allocation> allocations) {
        String sql = "INSERT INTO allocation(student_id, class_id, start) " +
                "VALUES((SELECT id FROM student WHERE university_id = ?), " +
                       "(SELECT id FROM class WHERE uuid = ?), ?)";
        return executor.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, allocations.get(i).getStudentUniversityId());
                ps.setString(2, allocations.get(i).getClassUuid());
                ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            }

            @Override
            public int getBatchSize() {
                return allocations.size();
            }
        });

    }

    public int[] insertAllocations(List<Allocation> allocations, long timestamp) {
        String sql = "INSERT INTO allocation(student_id, class_id, start) " +
                "VALUES((SELECT id FROM student WHERE university_id = ?), " +
                "(SELECT id FROM class WHERE uuid = ?), ?)";
        return executor.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, allocations.get(i).getStudentUniversityId());
                ps.setString(2, allocations.get(i).getClassUuid());
                ps.setTimestamp(3, new Timestamp(timestamp));
            }

            @Override
            public int getBatchSize() {
                return allocations.size();
            }
        });

    }

    public boolean cancelStudentsAllocationsToClasses(String universityId) {
        String sql = "UPDATE allocation " +
                        "SET allocation.end = ? " +
                     "FROM allocation " +
                        "INNER JOIN student ON allocation.student_id = student.id " +
                     "WHERE student.university_id = ? ";
        return executor.update(sql, new Object[]{new Timestamp(System.currentTimeMillis()), universityId}) == 1 ? true : false;
    }

    public boolean deleteAllocationsToModulesClasses(String moduleCode) {
        String sql = "DELETE allocation " +
                     "FROM allocation " +
                        "INNER JOIN class ON allocation.class_id = class.id " +
                        "INNER JOIN module ON class.module_id = module.id " +
                     "WHERE module.module_code = ? ";
        return executor.update(sql, new Object[]{moduleCode.toUpperCase()}) == 1 ? true : false;
    }

    public boolean deleteAllocationsToAClass(String classUuid) {
        String sql = "DELETE allocation " +
                "FROM allocation " +
                "INNER JOIN class ON allocation.class_id = class.id " +
                "WHERE class.uuid = ? ";
        return executor.update(sql, new Object[]{classUuid}) == 1 ? true : false;
    }

}
