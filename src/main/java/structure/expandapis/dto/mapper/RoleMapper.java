package structure.expandapis.dto.mapper;

import org.springframework.stereotype.Component;
import structure.expandapis.dto.RoleResponseDto;
import structure.expandapis.model.Role;

@Component
public class RoleMapper {

    public RoleResponseDto toDto(Role role) {
        return new RoleResponseDto(
                role.getId(),
                role.getRoleName().name());
    }
}
