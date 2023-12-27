package structure.expandapis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import structure.expandapis.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getRoleByRoleName(Role.RoleName roleName);
}
