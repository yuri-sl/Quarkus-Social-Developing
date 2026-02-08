package org.acme.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.entity.UserEntity;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class FollowResponseDTO {
    private long follow_id;
    private UserEntity user;
    private UserEntity follower;
}
