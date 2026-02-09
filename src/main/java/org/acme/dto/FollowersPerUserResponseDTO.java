package org.acme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.entity.UserEntity;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class FollowersPerUserResponseDTO {
    private String name;
    private List<FollowersInfoDTO> listaSeguidores;
}
