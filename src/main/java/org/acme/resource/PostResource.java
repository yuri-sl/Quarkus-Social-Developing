package org.acme.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.acme.dto.CreatePostRequestDTO;
import org.acme.entity.PostEntity;
import org.acme.service.PostService;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@AllArgsConstructor
@ApplicationScoped
@Path("/post")
public class PostResource {
    final PostService postService;

    @POST
    @Path("/{user_id}")
    public RestResponse<?> criarPostagem(@PathParam("user_id") long user_id, CreatePostRequestDTO dados){
        try{
            postService.criarPostagem(dados,user_id);
            return RestResponse.status(RestResponse.Status.OK,"Postagem Criada");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    public RestResponse<List<PostEntity>> buscarTodasPostagens(){
        try{
            List postsBuscados = postService.buscarPosts();
            return RestResponse.status(Response.Status.FOUND,postsBuscados);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("/{userId}")
    public RestResponse<List<PostEntity>> buscarTodasPostagens(@PathParam("userId") long userId,
                                                               @QueryParam("usuario_logado") long usuarioLogadoId){
        try{
            return RestResponse.status(Response.Status.FOUND,postService.buscarPostPorIdUsuarioSeguir(userId,usuarioLogadoId));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
