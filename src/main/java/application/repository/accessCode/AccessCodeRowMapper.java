package application.repository.accessCode;

import application.domain.accessCode.AccessCode;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccessCodeRowMapper implements RowMapper<AccessCode>{
    @Override
    public AccessCode mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AccessCode(rs.getString("uuid"), rs.getInt("code"));
    }
}
