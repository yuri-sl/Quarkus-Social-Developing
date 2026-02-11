package org.acme.resource;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.FollowRequestDTO;
import org.acme.entity.UserEntity;
import org.acme.repository.UserRepository;
import org.acme.service.FollowerService;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FollowerResourceTest {
    @Inject
    UserRepository userRepository;

    @Inject
    FollowerService followerService;

    public long userId;
    public long userId2;

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
    }

    @Test
    @DisplayName("Should return a user with no followers")
    @Order(1)
    public void fetchAllFollowersTestNoFollowers(){
        given()
                .contentType(ContentType.JSON)
                .pathParam("userId",userId)
                .when()
                .get("/users/{userId}/followers")
                .then()
                .statusCode(202)
                .extract().response();
    }

    @Test
    @DisplayName("Should follow a new user")
    @Order(2)
    public void followNewUser(){
        FollowRequestDTO followRequestDTO = FollowRequestDTO.builder()
                .followerId(userId2).build();

        given()
                .contentType(ContentType.JSON)
                .body(followRequestDTO)
                .pathParam("userId",userId)
                .when()
                .put("/users/{userId}/followers")
                .then()
                .statusCode(200)
                .extract().response();
    }

    @Test
    @DisplayName("Should return all followers from a specific user")
    @Order(3)
    public void fetchAllFollowersTest(){
        FollowRequestDTO followRequestDTO = FollowRequestDTO.builder()
                .followerId(userId2).build();
        try{
            followerService.followUser(userId,followRequestDTO);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
       var response =  given()
                .contentType(ContentType.JSON)
                .pathParam("userId",userId)
                .when()
                .get("/users/{userId}/followers")
                .then()
                .statusCode(202)
                .extract().response();
    }

    @Test
    @DisplayName("Should return 404 if user not found")
    @Order(4)
    public void returnUserNotFound(){
        given()
                .contentType(ContentType.JSON)
                .pathParam("userId",99)
                .when()
                .get("/users/{userId}/followers")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Should unfollow an user")
    @Order(5)
    public void unfollowUser(){
        FollowRequestDTO followRequestDTO = FollowRequestDTO.builder()
                .followerId(userId2).build();
        try{
            followerService.followUser(userId,followRequestDTO);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        var response = given()
                .contentType(ContentType.JSON)
                .pathParam("userId",userId2)
                .queryParam("unfollowed_by",userId)
                .when()
                .delete("/users/{userId}/followers")
                .then()
                .statusCode(200)
                .extract().response();
        var followerCount = response.jsonPath().get("followerCount");
        assertEquals(0,followerCount);
    }
    @Test
    @DisplayName("Should unfollow an user")
    @Order(6)
    public void unfollowUserErrorNotFound(){
        FollowRequestDTO followRequestDTO = FollowRequestDTO.builder()
                .followerId(userId2).build();
        try{
            followerService.followUser(userId,followRequestDTO);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        var response = given()
                .contentType(ContentType.JSON)
                .pathParam("userId",99)
                .queryParam("unfollowed_by",userId)
                .when()
                .delete("/users/{userId}/followers")
                .then()
                .statusCode(400)
                .extract().response();
    }



}