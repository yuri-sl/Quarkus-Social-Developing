package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.acme.dto.CreateUserRequestDTO;
import org.acme.dto.CreateUserResponseDTO;
import org.acme.entity.UserEntity;
import org.acme.repository.UserRepository;

import java.util.List;

@AllArgsConstructor
@ApplicationScoped
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public CreateUserResponseDTO createNewUser(CreateUserRequestDTO dados){
        UserEntity usuarioCriado = UserEntity.builder()
                .name(dados.getName())
                .email(dados.getEmail())
                .age(dados.getAge())
                .build();
        userRepository.persist(usuarioCriado);

        CreateUserResponseDTO usuarioCriadoNoBanco = CreateUserResponseDTO.mapearEntidade(usuarioCriado);
        return usuarioCriadoNoBanco;

    }
    public List<UserEntity> listarTodosUsuarios(){
        return userRepository.findAll().stream().toList();
    }

}
