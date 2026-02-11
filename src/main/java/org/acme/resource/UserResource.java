package org.acme.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.acme.dto.CreateUserRequestDTO;
import org.acme.dto.CreateUserResponseDTO;
import org.acme.entity.UserEntity;
import org.acme.service.UserService;
import org.hibernate.engine.spi.Status;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@AllArgsConstructor
@ApplicationScoped
@Path("/user")
public class UserResource {
    final UserService userService;

    @Path("/health")
    @GET()
    public RestResponse<String> healthStatus(){
        try{
            return RestResponse.status(RestResponse.Status.OK,"Working");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    @GET
    public RestResponse<List<UserEntity>> getAllusers(){
        try {
            List<UserEntity> listarTodosUsuarios = userService.listarTodosUsuarios();
            return RestResponse.status(RestResponse.Status.FOUND,listarTodosUsuarios);
        }
        catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @POST
    public RestResponse<?> criarUsuarioBanco(CreateUserRequestDTO dadosInput){
        try{
            CreateUserResponseDTO usuarioCriado = userService.createNewUser(dadosInput);
            return RestResponse.status(Response.Status.CREATED,usuarioCriado);
        }catch (IllegalArgumentException e) {
            return RestResponse.status(RestResponse.Status.BAD_REQUEST,e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("/{UserId}")
    public RestResponse<UserEntity> listarUsuarioPorId(@PathParam("UserId") long userId){
        try {
            UserEntity usuarioEncontrado = userService.listarUsuarioPorId(userId);
            return RestResponse.status(RestResponse.Status.FOUND,usuarioEncontrado);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @DELETE
    @Path("/{UserId}")
    public RestResponse<?> deletarUsuarioPorId(@PathParam("UserId") long userId){
        try{
            userService.deletarUsuarioPorId(userId);
            return RestResponse.status(RestResponse.Status.OK,"Deletado");
        } catch (RuntimeException e) {
            return RestResponse.status(RestResponse.Status.NOT_FOUND,e.getMessage());
        }
    }

    @PUT
    public RestResponse<CreateUserResponseDTO> editarUsuarioPorNome(@HeaderParam("user_id") long user_id,CreateUserRequestDTO dados){
        try{
           CreateUserResponseDTO dadosRetornados = userService.editarUsuarioPorId(dados,user_id);
           return RestResponse.status(RestResponse.Status.FOUND,dadosRetornados);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
