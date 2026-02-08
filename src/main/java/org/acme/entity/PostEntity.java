package org.acme.entity;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@ApplicationScoped
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id_post;

    @Column
    private String text;

    @Column
    private LocalDateTime time;

    @ManyToOne
    private UserEntity user;


}
