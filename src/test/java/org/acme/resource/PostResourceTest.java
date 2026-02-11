package org.acme.resource;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.CreatePostRequestDTO;
import org.acme.dto.CreateUserRequestDTO;
import org.acme.entity.UserEntity;
import org.acme.repository.PostRepository;
import org.acme.repository.UserRepository;
import org.junit.jupiter.api.*;


import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(PostResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostResourceTest {

    @Inject
    UserRepository userRepository;

    long userId;

    @BeforeEach
    @Transactional
    public void setUp(){
        UserEntity dados = UserEntity.builder()
                .name("Alberto")
                .email("Alberto.oliver@gmail.com")
                .age(27)
                .build();

        userRepository.persist(dados);
        userId = dados.getId_user();
    }

    @Test
    @DisplayName("Should create a new post")
    public void createNewPostTest(){
        CreatePostRequestDTO criarPost = CreatePostRequestDTO.builder()
                .text("Nova mensagem aqui")
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(criarPost)
                .pathParam("user_id",userId)
                .when()
                .post("/{user_id}")
                .then()
                .statusCode(200);

    }



}