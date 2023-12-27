package structure.expandapis.dto;

import structure.expandapis.lib.ValidUsername;
import javax.validation.constraints.Size;

public record UserRegisterDto(@Size(min = 4, max = 16, message = "Must contains 4 - 16 symbols")
                              @ValidUsername
                              String username,
                              @Size(min = 4, max = 16, message = "Must contains 4 - 16 symbols")
                              String password) {
}
