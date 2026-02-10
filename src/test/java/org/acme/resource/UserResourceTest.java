package org.acme.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.acme.dto.CreateUserRequestDTO;
import org.acme.dto.CreateUserResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UserResourceTest {

    @Test
    @DisplayName("O sistema deve estar funcionando")
    public void healthTest(){
        Response requisition =
                given()
                        .contentType(ContentType.JSON)
                .when()
                        .get("/user/health")
                .then()
                        .extract().response();

        assertEquals(200,requisition.getStatusCode());
        assertEquals("Working",requisition.getBody().asString());
    }

    @Test
    @DisplayName("Should create a new user successfuly")
    public void createUserTest(){
        CreateUserRequestDTO createUserRequestDTO = CreateUserRequestDTO.builder()
                .name("Alberto")
                .email("alberto.sdbh@gmail.com")
                .age(25)
                .build();
        Response resposta =
                given()
                        .contentType(ContentType.JSON)
                        .body(createUserRequestDTO)
                .when()
                        .post("/user")
                .then()
                        .extract().response();

        CreateUserResponseDTO respostaDTO = resposta.as(CreateUserResponseDTO.class);

        assertEquals(201,resposta.getStatusCode());
        assertNotNull(respostaDTO.getId());
        assertEquals("Alberto",respostaDTO.getName());
        assertEquals("alberto.sdbh@gmail.com",respostaDTO.getEmail());
        assertEquals(25,respostaDTO.getAge());
    }



}