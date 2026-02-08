package org.acme.dto;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.entity.UserEntity;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApplicationScoped
public class CreatePostDTO {
    private String text;
    private LocalDateTime localDateTime;
    private UserEntity userEntityfrom;
}
