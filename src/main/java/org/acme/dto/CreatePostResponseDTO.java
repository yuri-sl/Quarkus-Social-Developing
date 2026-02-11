package org.acme.dto;

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
public class CreatePostResponseDTO {
    private long postId;
    private String text;
    private LocalDateTime localDateTime;
    private UserEntity userEntityfrom;
}
