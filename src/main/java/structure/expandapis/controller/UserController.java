package structure.expandapis.controller;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import structure.expandapis.dto.UserLoginDto;
import structure.expandapis.dto.UserRegisterDto;
import structure.expandapis.dto.UserResponseDto;
import structure.expandapis.dto.mapper.UserMapper;
import structure.expandapis.model.User;
import structure.expandapis.security.AuthenticationService;
import structure.expandapis.security.JwtTokenProvider;
import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider provider;
    private final UserMapper userMapper;

    @PostMapping("/add")
    @ApiOperation(value = "registration new account for user")
    public UserResponseDto add(@RequestBody @Valid UserRegisterDto dto) {
        User user = authenticationService.addNewAccount(dto.username(),
                dto.password());
        return userMapper.toDto(user);
    }

    @PostMapping("/authenticate")
    @ApiOperation(value = "login to account")
    public ResponseEntity<Object> authenticate(@RequestBody @Valid UserLoginDto dto) {
        User user = authenticationService.login(dto.username(), dto.password());
        String token = provider.createToken(user.getUsername(), user.getRole().stream()
                .map(r -> r.getRoleName().name())
                .collect(Collectors.toList()));
        return new ResponseEntity<>(Map.of("token", token,
                "id", user.getId(), "username", user.getUsername()), HttpStatus.OK);
    }
}
