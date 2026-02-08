package org.acme.dto;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApplicationScoped
public class CreatePostRequestDTO {
    private String text;
}
