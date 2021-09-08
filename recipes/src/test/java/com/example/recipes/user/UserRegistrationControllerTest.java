package com.example.recipes.user;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class UserRegistrationControllerTest {

    private final String URL = "http://localhost:8881/api/register";
    private final String email = "test@test.com";
    private final String password = "testtest88";
    private HashMap<String, String> body;

    private RestTemplate restTemplate;

    @BeforeEach
    void createDefaultUser() {
        body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);
    }

    @Test
    void whenRegisterNewUser_then200OK() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post(URL);

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

}