package org.acme.dto;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@ApplicationScoped
@Data
@Builder
@NoArgsConstructor
public class EditUserRequestDTO {
    private String email;
    private Integer age;
}
