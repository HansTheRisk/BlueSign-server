package main.application.repository.user;

import main.application.domain.user.User;
import main.application.repository.BaseJDBCRepository;
import main.application.repository.NaturallyIdentifiableRepository;
import org.springframework.stereotype.Component;

@Component
public class UserRepository extends BaseJDBCRepository implements NaturallyIdentifiableRepository<User> {

    public User findByUsername(String username) {
        String sql = "SELECT id, uuid, username, name, surname, psswd_salt, type " +
                     "FROM user " +
                     "WHERE username = ?";
        return executor.queryForObject(sql, new Object[]{username}, new UserRowMapper());
    }

    @Override
    public User findByUuid(String uuid) {
        return null;
    }

    @Override
    public User findById(Long id) {
        return null;
    }
}
