package org.acme.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class FollowersInfoDTO {
    private long followerId;
    private String followerName;
    private String email;
}
