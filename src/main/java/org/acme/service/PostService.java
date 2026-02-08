package org.acme.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.acme.dto.CreatePostDTO;
import org.acme.dto.CreatePostRequestDTO;
import org.acme.entity.PostEntity;
import org.acme.entity.UserEntity;
import org.acme.repository.PostRepository;
import org.acme.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@ApplicationScoped
public class PostService {
    final PostRepository postRepository;
    final UserRepository userRepository;

    public List<PostEntity> buscarPosts(){
        return postRepository.fetchAllPosts();
    }

    public PostEntity buscarPostPorId(long userId){
        return postRepository.fetchPostById(userId);
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
