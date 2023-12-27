package structure.expandapis.dto;

import java.util.List;

public record UserResponseDto(Long id,
                              String username,
                              List<RoleResponseDto> role) {
}
