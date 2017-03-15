package application.repository.module;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModuleGroupRowMapper implements RowMapper<ModuleGroup> {
    @Override
    public ModuleGroup mapRow(ResultSet resultSet, int i) throws SQLException {
        return new ModuleGroup(resultSet.getString("group_name"));
    }
}
