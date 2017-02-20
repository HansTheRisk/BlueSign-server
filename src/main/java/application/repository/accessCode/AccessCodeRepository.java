package application.repository.accessCode;

import application.domain.accessCode.AccessCode;
import application.domain.accessCode.AccessCodeForClass;
import application.repository.BaseJDBCRepository;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Repository allowing operations on the access_code table.
 */
@Component
public class AccessCodeRepository extends BaseJDBCRepository {

    /**
     * This method deletes all the codes in the table.
     */
    public void deleteCodes() {
        executor.execute("TRUNCATE TABLE access_code");
    }

    /**
     * This method retrieves an access code for a running class
     * for the given lecturer.
     * @param lecturerUuid
     * @return AccessCodeForClass
     */
    public AccessCodeForClass getClassAccessCodeForLecturer(String lecturerUuid) {
        String sql = "SELECT class_id, code, class.id AS cl_id, class.uuid, module.id AS module_id, module.module_code, start_date, end_date, room, group_name, " +
                     "(SELECT COUNT(*) "+
                     "FROM class INNER JOIN allocation "+
                        "ON class.id = allocation.class_id " +
                     "WHERE class.id = cl_id) as allocated " +
                     "FROM access_code " +
                        "INNER JOIN class ON access_code.class_id = class.id " +
                        "INNER JOIN module ON class.module_id = module.id " +
                        "INNER JOIN user ON module.lecturer_id = user.id " +
                     "WHERE user.uuid = ?";
        return executor.queryForObject(sql, new Object[]{lecturerUuid}, new AccessCodeForClassRowMapper());
    }

    /**
     * This method inserts access codes into the database.
     * @param codes
     */
    public void insertCodes(List<AccessCode> codes) {
        String sql = "INSERT INTO access_code(class_id, code) " +
                      "VALUES((SELECT id FROM class WHERE uuid = ?), ?)";
        executor.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, codes.get(i).getClassUuid());
                ps.setInt(2, codes.get(i).getCode());
            }

            @Override
            public int getBatchSize() {
                return codes.size();
            }
        });
    }

}
