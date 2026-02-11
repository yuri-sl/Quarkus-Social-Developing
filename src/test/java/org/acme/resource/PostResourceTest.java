package org.acme.resource;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.CreatePostRequestDTO;
import org.acme.dto.CreateUserRequestDTO;
import org.acme.dto.FollowRequestDTO;
import org.acme.entity.UserEntity;
import org.acme.repository.PostRepository;
import org.acme.repository.UserRepository;
import org.acme.service.FollowerService;
import org.acme.service.PostService;
import org.junit.jupiter.api.*;


import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(PostResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostResourceTest {

    @Inject
    UserRepository userRepository;

    @Inject
    FollowerService followerService;

    long userId;
    long userId2;

    @BeforeEach
    @Transactional
    public void setUp(){
        UserEntity dados = UserEntity.builder()
                .name("Alberto")
                .email("Alberto.oliver@gmail.com")
                .age(27)
                .build();

        userRepository.persist(dados);
        userRepository.flush();
        userId = dados.getId_user();
        UserEntity dados2 = UserEntity.builder()
                .name("gohan")
                .email("gohan.oliver@gmail.com")
                .age(32)
                .build();

        userRepository.persist(dados2);
        userRepository.flush();
        userId2 = dados2.getId_user();

        FollowRequestDTO followRequestDTO = FollowRequestDTO.builder()
                .followerId(userId2).build();
        try{
            followerService.followUser(userId,followRequestDTO);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }



    }

    @Test
    @DisplayName("Should create a new post")
    @Order(1)
    public void createNewPostTest(){
        CreatePostRequestDTO criarPost = CreatePostRequestDTO.builder()
                .text("Nova mensagem aqui")
                .build();

        var response = given()
                .contentType(ContentType.JSON)
                .body(criarPost)
                .pathParam("user_id",userId)
                .when()
                .post("/{user_id}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        assertEquals("Nova mensagem aqui",response.getBody().jsonPath().getString("text"));


    }

    @Test
    @DisplayName("Should give an error when trying to post with a non existing user")
    public void failedToCreateNewPost(){
        CreatePostRequestDTO dados = CreatePostRequestDTO.builder()
                .text("Something goes here").build();

        var Response = given()
                .contentType(ContentType.JSON)
                .body(dados)
                .pathParam("user_id",99)
                .when()
                .post("/{user_id}")
                .then()
                .extract().response();

        assertEquals(404,Response.getStatusCode());
    }

    @Test
    @DisplayName("Should return 404 when user doesn't exist")
    public void failedToGetAllPosts(){
        given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("userId",99)
                .get("/{userId}")
                .then()
                .statusCode(404)
                .extract().response();
    }

    @Test
    @DisplayName("Should post a new message from a user")
    public void createNewPost(){
        CreatePostRequestDTO createPostRequestDTO = CreatePostRequestDTO.builder()
                .text("Primeira postagem de teste").build();


        given()
                .contentType(ContentType.JSON)
                .body(createPostRequestDTO)
                .pathParam("user_id",userId)
                .when()
                .post("/{user_id}")
                .then()
                .statusCode(200)
                .extract().response();

    }



    @Test
    @DisplayName("Should fetch all posts from a user")
    @Order(2)
    public void FetchedAllPosts(){
        given()
                .contentType(ContentType.JSON)
                .pathParam("userId",userId)
                .queryParam("usuario_logado",userId2)
                .when()
                .get("/{userId}")
                .then()
                .statusCode(202)
                .extract().response();
    }



}