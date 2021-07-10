package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tacos.data.UserRepository;
import tacos.domain.User;

@Service("userDetailsService")
public class UserRepositoryUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserRepositoryUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("## username : " + username);
        User user = userRepository.findByUsername(username);

        if (user != null) {
            System.out.println("user found " + user.getUsername());
            return user;
        }

        throw new UsernameNotFoundException("'User '" + username + "' not found'");
    }
}
