package org.acme.resource;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.acme.dto.CreateUserRequestDTO;
import org.acme.dto.CreateUserResponseDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserResourceTest {

    @TestHTTPResource("/user")
    URL apiURL;

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
    @Order(1)
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
                        .post(apiURL)
                .then()
                        .extract().response();

        CreateUserResponseDTO respostaDTO = resposta.as(CreateUserResponseDTO.class);

        assertEquals(201,resposta.getStatusCode());
        assertNotNull(respostaDTO.getId());
        assertEquals("Alberto",respostaDTO.getName());
        assertEquals("alberto.sdbh@gmail.com",respostaDTO.getEmail());
        assertEquals(25,respostaDTO.getAge());
    }

    @Test
    @DisplayName("Deve retornar erro de BadRequest ao criar um usuário")
    public void testeFalhaAoCriarUsuario(){
        CreateUserRequestDTO dados = CreateUserRequestDTO.builder()
                .name("")
                .email(null)
                .age(46)
                .build();

        var response = given()
                .contentType(ContentType.JSON)
                .body(dados)
                .when()
                .post(apiURL)
                .then()
                .extract()
                .response();

        assertEquals(400,response.getStatusCode());
        //response.jsonPath().getString("message") => Buscar um campo especifico do JSON
        assertEquals("Todos os campos devem estar preenchidos",response.getBody().asString());
    }

    @Test
    @DisplayName("Should list all users")
    @Order(2)
    public void listAllusersTest(){
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(apiURL)
                .then()
                .statusCode(302)
                .body("size()", Matchers.is(1));
    }



}