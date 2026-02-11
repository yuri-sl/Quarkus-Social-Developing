package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.acme.dto.CreateUserRequestDTO;
import org.acme.dto.CreateUserResponseDTO;
import org.acme.dto.EditUserRequestDTO;
import org.acme.entity.UserEntity;
import org.acme.repository.UserRepository;

import java.util.List;

@AllArgsConstructor
@ApplicationScoped
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public CreateUserResponseDTO createNewUser(CreateUserRequestDTO dados){
        validarCamposCriarUsuario(dados);
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
        return userRepository.listarTodosUsuarios();
    }

    public UserEntity listarUsuarioPorId(long idUser){
        return userRepository.listarUsuarioPorId(idUser);
    }


    @Transactional
    public void deletarUsuarioPorId(long idUser){
       UserEntity usuarioEncontrado =   userRepository.findById(idUser);
       if(usuarioEncontrado == null){
           throw new RuntimeException("Usuário não encontrado");
       }
       userRepository.deleteById(idUser);
    }

    public void validarCamposCriarUsuario(CreateUserRequestDTO dados){
        if(dados.getName().isBlank() || dados.getAge() == null ||
        dados.getEmail().isBlank()){
            throw new IllegalArgumentException("Todos os campos devem estar preenchidos");
        }
    }

    @Transactional
    public CreateUserResponseDTO editarUsuarioPorId(CreateUserRequestDTO dados, long user_id){
        UserEntity usuarioEncontrado = userRepository.listarUsuarioPorId(user_id);

        usuarioEncontrado.setAge(dados.getAge());
        usuarioEncontrado.setEmail(dados.getEmail());
        usuarioEncontrado.setName(dados.getName());

        userRepository.persist(usuarioEncontrado);

        CreateUserResponseDTO resposta = CreateUserResponseDTO.builder()
                .id(user_id)
                .name(dados.getName())
                .email(dados.getEmail())
                .age(dados.getAge())
                .build();

        return resposta;
    }

}
