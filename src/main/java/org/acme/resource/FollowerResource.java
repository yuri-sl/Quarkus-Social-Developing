package org.acme.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.acme.dto.FollowRequestDTO;
import org.acme.dto.FollowResponseDTO;
import org.acme.dto.FollowersPerUserResponseDTO;
import org.acme.entity.FollowerEntity;
import org.acme.entity.UserEntity;
import org.acme.service.FollowerService;
import org.acme.service.UserService;
import org.jboss.resteasy.reactive.RestResponse;

import java.awt.*;
import java.util.concurrent.RejectedExecutionException;

@ApplicationScoped
@Path("/users/{userId}/followers")
@AllArgsConstructor
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {
    final FollowerService followerService;
    final UserService userService;

    //PUT é feito pois a resposta é mesma sempre. Só vai verificar se o cara segue o outro
    @PUT
    public RestResponse<?> followUser(
            @PathParam("userId") long userId, FollowRequestDTO followRequestDTO) {
        try {
            followerService.FollowUser(userId, followRequestDTO);
            return RestResponse.status(RestResponse.Status.OK, "Seguindo");
        } catch (ClassNotFoundException e) {
            return RestResponse.status(RestResponse.Status.NOT_FOUND, e.getMessage());
        } catch (RejectedExecutionException ex) {
            return  RestResponse.status(RestResponse.Status.BAD_REQUEST, ex.getMessage());
        } catch (IllegalArgumentException e) {
            return RestResponse.status(Response.Status.BAD_REQUEST,e.getMessage());
        }
        catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    public RestResponse<?> buscarTodosSeguidoresDeUsuario(@PathParam("userId") long userId){
        try{
            return RestResponse.status(RestResponse.Status.FOUND,followerService.buscarFollowersDeUsuario(userId));

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @DELETE
    public RestResponse<?> deixarDeSeguirUsuario(@PathParam("userId") long userId,@QueryParam("unfollowed_by") long unfollowed_by){
        try{
            return RestResponse.status(RestResponse.Status.OK,followerService.deletarFollowersUserId(userId,unfollowed_by));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
