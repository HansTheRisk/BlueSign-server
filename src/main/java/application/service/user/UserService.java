package application.service.user;

import application.domain.user.User;
import application.repository.user.UserRepository;
import application.service.NaturallyIdentifiableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Service allowing operations on the User table in the database.
 */
@Component
public class UserService implements NaturallyIdentifiableService <User>, UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Finds a user by UUID.
     * @param uuid
     * @return User
     */
    @Override
    public User findByUUID(String uuid) {
        return userRepository.findByUuid(uuid);
    }

    /**
     * Finds a user by id.
     * @param id
     * @return User
     */
    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Saves a user into the database.
     * @param object
     * @return User
     */
    @Override
    public User save(User object) {
        object.setUuid(UUID.randomUUID().toString());
        return userRepository.saveUser(object);
    }

    /**
     * Updates a user's details (name, surname, email or username)
     * in the database.
     * @param object
     * @return User
     */
    public User updateUserDetails(User object) {
        return userRepository.updateUserDetails(object);
    }

    /**
     * Gets user by username.
     * @param username
     * @return
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Loads a user by the username.
     * For authentication use.
     * @param s
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = getUserByUsername(s);

        if (user == null)
            throw new UsernameNotFoundException("User not found");
        else
            return user;
    }
}
