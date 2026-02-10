package org.acme.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.acme.dto.CreatePostRequestDTO;
import org.acme.entity.PostEntity;
import org.acme.entity.UserEntity;
import org.acme.repository.FollowerRepository;
import org.acme.repository.PostRepository;
import org.acme.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@ApplicationScoped
public class PostService {
    final PostRepository postRepository;
    final UserRepository userRepository;
    final FollowerRepository followerRepository;
    final UserService userService;

    public List<PostEntity> buscarPosts(){
        return postRepository.fetchAllPosts();
    }

    public List<PostEntity> buscarPostPorIdUsuarioSeguir(long userId, long usuarioLogadoId){
        UserEntity usuarioBuscado = userService.listarUsuarioPorId(userId);
        UserEntity usuarioLogado = userService.listarUsuarioPorId(usuarioLogadoId);

        if(!followerRepository.isUserFollowed(usuarioBuscado,usuarioLogado))
            throw new RuntimeException("Não pode ver posts de quem vc n segue");

        return postRepository.fetchPostByIdSpecific(userId);
    }

    @Transactional
    public void criarPostagem(CreatePostRequestDTO dados,long user_id){
        UserEntity userEncontrado = userRepository.listarUsuarioPorId(user_id);
        LocalDateTime hora = LocalDateTime.now();

        PostEntity createPot = PostEntity.builder()
                .text(dados.getText())
                .time(hora)
                .user(userEncontrado).build();

        postRepository.persist(createPot);
    }


}
