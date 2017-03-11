package application.repository.ipRange;

import application.domain.ipRange.IpRange;
import application.repository.BaseJDBCRepository;
import application.repository.NaturallyIdentifiableRepository;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * This repository allows operating on the ip_range table.
 */
@Component
public class IpRangeRepository extends BaseJDBCRepository implements NaturallyIdentifiableRepository<IpRange>{

    /**
     * This method returns all the ip ranges from the table.
     * @return List of IpRanges
     */
    public List<IpRange> findAllRanges() {
        String sql = "SELECT id, uuid, start, end " +
                     "FROM ip_range";
        return executor.query(sql, new IpRangeRowMapper());
    }

    public IpRange save(IpRange range) {
        range.setUuid(UUID.randomUUID().toString());
        String sql = "INSERT INTO ip_range(uuid, start, end) " +
                "VALUES(?, ?, ?)";
        if(executor.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, range.getUuid());
                ps.setString(2, range.getIpStart());
                ps.setString(3, range.getIpEnd());
            }
        }) == 1) {
            return findByUuid(range.getUuid());
        }
        else
            return null;
    }

    public boolean delete(String uuid) {
        String sql = "DELETE FROM ip_range WHERE uuid = ?";
        if(executor.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, uuid);
            }
        }) == 1) {
            return true;
        }
        else
            return false;
    }

    @Override
    public IpRange findByUuid(String uuid) {
        String sql = "SELECT id, uuid, start, end " +
                "FROM ip_range WHERE uuid = ?";
        return executor.queryForObject(sql, new Object[]{uuid}, new IpRangeRowMapper());
    }

    @Override
    public IpRange findById(Long id) {
        String sql = "SELECT id, uuid, start, end " +
                "FROM ip_range WHERE id = ?";
        return executor.queryForObject(sql, new Object[]{id}, new IpRangeRowMapper());
    }
}
