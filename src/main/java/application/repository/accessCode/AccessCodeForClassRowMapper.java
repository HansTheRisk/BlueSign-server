package application.repository.accessCode;

import application.domain.accessCode.AccessCode;
import application.domain.accessCode.AccessCodeForClass;
import application.domain.scheduledClass.ScheduledClass;
import application.repository.scheduledClass.ScheduledClassRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Row mapper mapping results of AccessCodeForClass queries into AccessCodeForClass objects.
 */
public class AccessCodeForClassRowMapper implements RowMapper<AccessCodeForClass>{
    @Override
    public AccessCodeForClass mapRow(ResultSet rs, int rowNum) throws SQLException {
        AccessCode ac = new AccessCodeRowMapper().mapRow(rs, rowNum);
        ScheduledClass sc = new ScheduledClassRowMapper().mapRow(rs, rowNum);
        return new AccessCodeForClass(ac, sc);
    }
}
