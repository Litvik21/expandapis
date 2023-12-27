package structure.expandapis.dto.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import structure.expandapis.dto.UserResponseDto;
import structure.expandapis.model.User;

@AllArgsConstructor
@Component
public class UserMapper {
    private final RoleMapper roleMapper;
    public UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getRole().stream().map(roleMapper::toDto).toList());
    }
}
