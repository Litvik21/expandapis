package structure.expandapis.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import structure.expandapis.model.User;
import structure.expandapis.service.UserService;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomAccountDetailsService implements UserDetailsService {
    private final UserService service;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> account = service.findByUsername(username);

        UserBuilder builder;
        if (account.isPresent()) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(account.get().getPassword());
            builder.roles(account.get().getRole()
                    .stream()
                    .map(r -> r.getRoleName().name())
                    .toArray(String[]::new));
            return builder.build();
        }
        throw new UsernameNotFoundException("Account not found.");
    }
}
