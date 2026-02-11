package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import lombok.AllArgsConstructor;
import org.acme.dto.FollowRequestDTO;
import org.acme.dto.FollowResponseDTO;
import org.acme.dto.FollowersInfoDTO;
import org.acme.dto.FollowersPerUserResponseDTO;
import org.acme.entity.FollowerEntity;
import org.acme.entity.UserEntity;
import org.acme.repository.FollowerRepository;
import org.acme.repository.UserRepository;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@ApplicationScoped
@AllArgsConstructor
public class FollowerService {
    final FollowerRepository followerRepository;
    final UserService userService;
    final UserRepository userRepository;


    @Transactional
    public void followUser(long userid, FollowRequestDTO followRequestDTO) throws ClassNotFoundException {

        UserEntity usuarioEncontrado = userService.listarUsuarioPorId(userid);
        UserEntity usuarioEncontradoSeguir = userService.listarUsuarioPorId(followRequestDTO.getFollowerId());

        if(usuarioEncontrado == null || usuarioEncontradoSeguir == null){
            throw new ClassNotFoundException("Usuário não encontrado");
        }
        if(usuarioEncontrado == usuarioEncontradoSeguir){
            throw new IllegalArgumentException("Nao pode seguir a si mesmo");
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

    public FollowersPerUserResponseDTO buscarFollowersDeUsuario(long id){
        userService.verificarSeUsuarioExiste(id);
      List<FollowerEntity> listaFollowers  = followerRepository.listarSeguidoresDeUsuario(id);
      List<FollowersInfoDTO> infosFollowers = new ArrayList<>();
      String nomeDeUsuario = userRepository.findByIdOptional(id)
              .map(UserEntity::getName)
              .orElseThrow(() -> new WebApplicationException("Usuário não encontrado",404));
      for(FollowerEntity f : listaFollowers){
          FollowersInfoDTO dadoFollowerAtual = FollowersInfoDTO.builder()
                  .followerId(f.getFollower().getId_user())
                  .followerName(f.getFollower().getName())
                  .email(f.getFollower().getEmail())
                  .build();

          infosFollowers.add(dadoFollowerAtual);
      }

      FollowersPerUserResponseDTO followersPerUserResponseDTO = FollowersPerUserResponseDTO.builder()
              .followerCount(infosFollowers.size())
              .name(nomeDeUsuario)
              .listaSeguidores(infosFollowers)
              .build();
      return followersPerUserResponseDTO;
    }
    @Transactional
    public FollowersPerUserResponseDTO deletarFollowersUserId(long id, long removeFollowerId){
        UserEntity follower = userService.listarUsuarioPorId(removeFollowerId);
        UserEntity user = userService.listarUsuarioPorId(id);
        if(followerRepository.isUserFollowed(follower,user)){
            followerRepository.delete("follower = ?1 AND user = ?2",follower,user);
        }
        followerRepository.flush();
        List<FollowerEntity> listaFollowers  = followerRepository.listarSeguidoresDeUsuario(id);
        List<FollowersInfoDTO> infosFollowers = new ArrayList<>();
        String nomeDeUsuario = user.getName();
        for(FollowerEntity f : listaFollowers){
            FollowersInfoDTO dadoFollowerAtual = FollowersInfoDTO.builder()
                    .followerId(f.getFollower().getId_user())
                    .followerName(f.getFollower().getName())
                    .email(f.getFollower().getEmail())
                    .build();

            infosFollowers.add(dadoFollowerAtual);
        }

        FollowersPerUserResponseDTO followersPerUserResponseDTO = FollowersPerUserResponseDTO.builder()
                .followerCount(infosFollowers.size())
                .name(nomeDeUsuario)
                .listaSeguidores(infosFollowers)
                .build();
        return followersPerUserResponseDTO;
    }



}
