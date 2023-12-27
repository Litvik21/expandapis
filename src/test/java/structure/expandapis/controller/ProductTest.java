package structure.expandapis.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import structure.expandapis.dto.Payload;
import structure.expandapis.dto.UserLoginDto;
import structure.expandapis.dto.UserRegisterDto;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProductTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void testProductAdditionAndListing() {
        UserRegisterDto userDto = new UserRegisterDto("testuser", "testpassword");
        ResponseEntity<String> createUserResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/user/add", userDto, String.class);

        assertEquals(HttpStatus.OK, createUserResponse.getStatusCode());

        UserLoginDto authRequest = new UserLoginDto("testuser", "testpassword");
        ResponseEntity<Map> authenticateUserResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/user/authenticate", authRequest, Map.class);

        assertEquals(HttpStatus.OK, authenticateUserResponse.getStatusCode());
        String authToken = "Bearer " + authenticateUserResponse.getBody().get("token");

        Payload productDto = new Payload("products",
                List.of(Map.of("entryDate", "03-01-2023",
                        "itemCode", "11111",
                        "itemName", "Test Inventory 1",
                        "itemQuantity", "20",
                        "status", "Paid"),
                        Map.of("entryDate", "03-01-2023",
                        "itemCode", "11111",
                        "itemName", "Test Inventory 2",
                        "itemQuantity", "30",
                        "status", "NotPaid")));


        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Payload> requestEntity = new HttpEntity<>(productDto, headers);

        ResponseEntity<String> addProductResponse = restTemplate.exchange(
                "http://localhost:" + port + "/products/add",
                HttpMethod.POST,
                requestEntity,
                String.class);

        assertEquals(HttpStatus.OK, addProductResponse.getStatusCode());

        HttpEntity<Void> requestEntityAll = new HttpEntity<>(headers);

        ResponseEntity<String> getProductsResponse = restTemplate.exchange(
                "http://localhost:" + port + "/products/all",
                HttpMethod.GET,
                requestEntityAll,
                String.class);

        assertEquals(HttpStatus.OK, getProductsResponse.getStatusCode());
    }
}
