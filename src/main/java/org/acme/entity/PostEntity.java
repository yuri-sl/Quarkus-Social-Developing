package org.acme.entity;

import jakarta.persistence.*;

@Entity
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id_post;

    @Column
    private String text;

    @ManyToOne
    private UserEntity user;


}
