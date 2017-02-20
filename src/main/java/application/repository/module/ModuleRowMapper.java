package application.repository.module;

import application.domain.module.Module;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This row mapper maps Module database rows into Module objects.
 */
public class ModuleRowMapper implements RowMapper<Module> {
    @Override
    public Module mapRow(ResultSet resultSet, int i) throws SQLException {
        Module module = new Module();
        module.setId(resultSet.getLong("id"));
        module.setTitle(resultSet.getString("title"));
        module.setModuleCode(resultSet.getString("module_code"));
        String lecturerUuid = resultSet.getString("lecturer_uuid");
        if (lecturerUuid != null)
            module.setLecturerUuid(lecturerUuid);
        return module;
    }
}
