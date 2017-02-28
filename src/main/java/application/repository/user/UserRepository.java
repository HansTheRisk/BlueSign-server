package application.repository.user;

import application.domain.user.User;
import application.repository.BaseJDBCRepository;
import application.repository.NaturallyIdentifiableRepository;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * UserRepository for operating on the User rows in the database.
 */
@Component
public class UserRepository extends BaseJDBCRepository implements NaturallyIdentifiableRepository<User> {

    public List<User> findAll() {
        String sql = "SELECT id, uuid, username, name, surname, psswd_salt, type, email " +
                "FROM user ";
        return executor.query(sql, new UserRowMapper());
    }

    /**
     * Retrieves a User by the username.
     * @param username
     * @return User
     */
    public User findByUsername(String username) {
        String sql = "SELECT id, uuid, username, name, surname, psswd_salt, type, email " +
                "FROM user " +
                "WHERE username = ?";
        return executor.queryForObject(sql, new Object[]{username}, new UserRowMapper());
    }

    public boolean resetUserPassword(String uuid, String password) {
        String sql = "UPDATE user "+
                     "SET psswd_salt = ? "+
                     "WHERE uuid = ? ";
        if(executor.update(sql,
                new Object[]{password, uuid}) ==1)
            return true;
        else
            return false;
    }

    public User updateUserDetails(User user) {
        String sql = "UPDATE user "+
                     "SET username = ?, " +
                         "name = ?, " +
                         "surname = ?, " +
                         "email = ? " +
                     "WHERE uuid = ? ";
        if(executor.update(sql,
                           new Object[]{user.getUsername(),
                                        user.getName(),
                                        user.getSurname(),
                                        user.getEmail(),
                                        user.getUuid()}) ==1)
            return findByUuid(user.getUuid());
        else
            return null;
    }

    public User saveUser(User user) {
        String sql = "INSERT INTO user(uuid, username, name, surname, psswd_salt, type, email) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        if(executor.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, user.getUuid());
                ps.setString(2, user.getUsername());
                ps.setString(3, user.getName());
                ps.setString(4, user.getSurname());
                ps.setString(5, user.getPassword());
                ps.setString(6, user.getRole());
                ps.setString(7, user.getEmail());
            }
        }) == 1)
            return findByUuid(user.getUuid());
        else
            return null;
    }

    @Override
    public User findByUuid(String uuid) {
        String sql = "SELECT id, uuid, username, name, surname, psswd_salt, type, email " +
                "FROM user " +
                "WHERE uuid = ?";
        return executor.queryForObject(sql, new Object[]{uuid}, new UserRowMapper());
    }

    @Override
    public User findById(Long id) {
        return null;
    }
}
