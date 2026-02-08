package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.acme.dto.FollowRequestDTO;
import org.acme.dto.FollowResponseDTO;
import org.acme.entity.FollowerEntity;
import org.acme.entity.UserEntity;
import org.acme.repository.FollowerRepository;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class FollowerService {
    final FollowerRepository followerRepository;
    final UserService userService;


    @Transactional
    public void FollowUser(long userid, FollowRequestDTO followRequestDTO) throws ClassNotFoundException {
        UserEntity usuarioEncontrado = userService.listarUsuarioPorId(userid);
        UserEntity usuarioEncontradoSeguir = userService.listarUsuarioPorId(followRequestDTO.getFollowerId());

        if(usuarioEncontrado == null || usuarioEncontradoSeguir == null){
            throw new ClassNotFoundException("Usuário não encontrado");
        }
        boolean userFound = followerRepository.isUserFollowed(usuarioEncontradoSeguir,usuarioEncontrado);


        if(!userFound){
            FollowerEntity followerEntity = FollowerEntity.builder()
                    .user(usuarioEncontradoSeguir)
                    .follower(usuarioEncontrado)
                    .build();
            followerRepository.persist(followerEntity);

            /*
            FollowResponseDTO followResponseDTO = FollowResponseDTO.builder()
                    .user(usuarioEncontrado)
                    .follower(usuarioEncontradoSeguir)
                    .build();

             */

        }

    }
/*
    public List<FollowerEntity>(long userId){

    }
*/



}
