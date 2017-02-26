package application.repository.user;

import application.domain.user.User;
import application.repository.NaturallyIdentifiableRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Row mapper for mapping user rows to user domain objects
 */
public class UserRowMapper implements NaturallyIdentifiableRowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUuid(rs.getString("uuid"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("psswd_salt"));
        user.setRole(rs.getString("type"));
        user.setEmail(rs.getString("email"));
        return user;
    }
}


