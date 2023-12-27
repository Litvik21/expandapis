package structure.expandapis.security;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import structure.expandapis.model.Role;
import structure.expandapis.model.User;
import structure.expandapis.service.RoleService;
import structure.expandapis.service.UserService;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService service;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public User addNewAccount(String username, String password) {
        User account = new User();

        account.setUsername(username);
        account.setPassword(password);
        account.setRole(Set.of(roleService.getRoleByRoleName(Role.RoleName.USER)));
        return service.add(account);
    }

    @Override
    public User login(String username, String password) {
        Optional<User> user = service.findByUsername(username);
        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) {
            throw new RuntimeException("Incorrect username or password!");
        }
        return user.get();
    }
}
