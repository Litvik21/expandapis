package structure.expandapis.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import structure.expandapis.model.User;
import structure.expandapis.repository.UserRepository;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User add(User account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return repository.save(account);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Long getIdByUsername(String username) {
        User user = findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Can't find user bu username: " + username));
        return user.getId();
    }
}
