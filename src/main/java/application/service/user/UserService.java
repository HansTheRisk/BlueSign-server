package application.service.user;

import application.domain.user.User;
import application.repository.user.UserRepository;
import application.service.NaturallyIdentifiableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Service allowing operations on the User table in the database.
 */
@Component
public class UserService implements NaturallyIdentifiableService <User>, UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    /**
     * Finds a user by UUID.
     * @param uuid
     * @return User
     */
    @Override
    public User findByUUID(String uuid) {
        return null;
    }

    /**
     * Finds a user by id.
     * @param id
     * @return User
     */
    @Override
    public User findById(Long id) {
        return null;
    }

    /**
     * Saves a user into the database.
     * @param object
     * @return User
     */
    @Override
    public User save(User object) {
        return null;
    }

    /**
     * Loads a user by the username.
     * @param s
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);

        if (user == null)
            throw new UsernameNotFoundException("User not found");
        else
            return user;
    }
}
