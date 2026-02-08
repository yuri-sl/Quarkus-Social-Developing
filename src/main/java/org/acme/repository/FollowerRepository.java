package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.vertx.ext.auth.User;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.FollowerEntity;
import org.acme.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<FollowerEntity> {
    public List<FollowerEntity> listarSeguidoresUsuario(long userId){
        return find("WHERE user.id = ?1",userId).stream().toList();
    }

    public boolean isUserFollowed(UserEntity follower, UserEntity user){
        return count("WHERE follower = ?1 AND user = ?2",follower,user) > 0;
    }
}
