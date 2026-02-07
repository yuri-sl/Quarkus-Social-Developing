package org.acme.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.acme.dto.CreateUserRequestDTO;
import org.acme.dto.CreateUserResponseDTO;
import org.acme.service.UserService;
import org.jboss.resteasy.reactive.RestResponse;

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

    @POST
    public RestResponse<CreateUserResponseDTO> criarUsuarioBanco(CreateUserRequestDTO dadosInput){
        try{
            CreateUserResponseDTO usuarioCriado = userService.createNewUser(dadosInput);
            return RestResponse.status(Response.Status.CREATED,usuarioCriado);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
