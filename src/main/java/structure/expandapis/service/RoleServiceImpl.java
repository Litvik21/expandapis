package structure.expandapis.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import structure.expandapis.model.Role;
import structure.expandapis.repository.RoleRepository;
import java.util.List;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getRoleByRoleName(Role.RoleName roleName) {
        return roleRepository.getRoleByRoleName(roleName);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
