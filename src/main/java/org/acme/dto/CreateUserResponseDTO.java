package org.acme.dto;


import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.entity.UserEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ApplicationScoped
public class CreateUserResponseDTO {
    private long id;
    private String name;
    private String email;
    private Integer age;

    public static CreateUserResponseDTO mapearEntidade(UserEntity user){
        CreateUserResponseDTO createUserResponseDTO = CreateUserResponseDTO.builder()
                .id(user.getId_user())
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .build();
        return  createUserResponseDTO;
    }
}
