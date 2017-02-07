package application.repository.ipRange;

import application.domain.ipRange.IpRange;
import application.repository.NaturallyIdentifiableRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IpRangeRowMapper implements NaturallyIdentifiableRowMapper<IpRange>{
    @Override
    public IpRange mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new IpRange(rs.getLong("id"), rs.getString("uuid"), rs.getString("start"), rs.getString("end"));
    }
}