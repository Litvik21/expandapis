package structure.expandapis.security;

import structure.expandapis.model.User;

public interface AuthenticationService {
    User addNewAccount(String username, String password);

    User login(String username, String password);
}
