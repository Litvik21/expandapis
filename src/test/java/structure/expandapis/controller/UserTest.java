package structure.expandapis.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import structure.expandapis.dto.UserLoginDto;
import structure.expandapis.dto.UserRegisterDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql({"/sql/create-table-roles.sql", "/sql/create-table-users.sql", "/sql/create-table-users-role.sql", "/sql/initial-roles.sql"})
public class UserTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void testUserCreationAndAuthentication() {
        UserRegisterDto userDto = new UserRegisterDto("testuser", "testpassword");
        ResponseEntity<String> createUserResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/user/add", userDto, String.class);

        assertEquals(HttpStatus.OK, createUserResponse.getStatusCode());

        UserLoginDto authRequest = new UserLoginDto("testuser", "testpassword");
        ResponseEntity<String> authenticateUserResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/user/authenticate", authRequest, String.class);

        assertEquals(HttpStatus.OK, authenticateUserResponse.getStatusCode());
    }
}
