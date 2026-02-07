package org.acme.dto;


import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
