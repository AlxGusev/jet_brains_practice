package com.example.recipes.recipe;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.example.recipes.utils.Utils.*;
import static io.restassured.RestAssured.given;
@Disabled
class RecipeControllerTest {

    @BeforeAll
    static void beforeAll() {
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName(EMAIL);
        authScheme.setPassword(PASSWORD);
        RestAssured.authentication = authScheme;
        RestAssured.port = 8881;
    }

    @Test
    public void givenId_whenRequestGetRecipeById_then200OK() {
        given()
                .when()
                .get("/api/recipe/{id}", ID)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void givenId_whenRequestGetRecipeById_thenCorrectBody() {
        Response response = given().accept(ContentType.JSON)
                .get("/api/recipe/{id}", 1L);
        RecipeDto as = response.as(RecipeDto.class);
        System.out.println(as.getCategory());

    }
}