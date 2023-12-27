package structure.expandapis.service;

import structure.expandapis.model.User;
import java.util.Optional;

public interface UserService {
    User add(User account);

    Optional<User> findByUsername(String username);

    Long getIdByUsername(String username);
}
