package org.acme.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.acme.dto.CreateUserRequestDTO;
import org.acme.dto.CreateUserResponseDTO;
import org.acme.entity.UserEntity;
import org.acme.service.UserService;
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
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @POST
    public RestResponse<CreateUserResponseDTO> criarUsuarioBanco(CreateUserRequestDTO dadosInput){
        try{
            CreateUserResponseDTO usuarioCriado = userService.createNewUser(dadosInput);
            return RestResponse.status(Response.Status.CREATED,usuarioCriado);
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

}
