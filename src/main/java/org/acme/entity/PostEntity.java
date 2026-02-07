package org.acme.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
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
