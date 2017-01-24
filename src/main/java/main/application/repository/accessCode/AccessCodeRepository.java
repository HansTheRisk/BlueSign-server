package main.application.repository.accessCode;

import main.application.domain.accessCode.AccessCode;
import main.application.repository.BaseJDBCRepository;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class AccessCodeRepository extends BaseJDBCRepository {

    public void deleteCodes() {
        executor.execute("TRUNCATE TABLE access_code");
    }

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
