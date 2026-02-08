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
import java.util.concurrent.RejectedExecutionException;

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
        boolean userFound = followerRepository.isUserFollowed(usuarioEncontrado,usuarioEncontradoSeguir);


        if(!userFound){
            FollowerEntity followerEntity = FollowerEntity.builder()
                    .user(usuarioEncontradoSeguir)
                    .follower(usuarioEncontrado)
                    .build();
            followerRepository.persist(followerEntity);
        }else{
            throw new RejectedExecutionException("Usuário já existe no sistema");
        }

    }
/*
    public List<FollowerEntity>(long userId){

    }
*/



}
