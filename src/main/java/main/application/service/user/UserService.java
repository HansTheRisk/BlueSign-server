package main.application.service.user;

import main.application.domain.user.User;
import main.application.repository.user.UserRepository;
import main.application.service.NaturallyIdentifiableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserService implements NaturallyIdentifiableService <User>, UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUUID(String uuid) {
        return null;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public User save(User object) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);

        if (user == null)
            throw new UsernameNotFoundException("User not found");
        else
            return user;
    }
}
