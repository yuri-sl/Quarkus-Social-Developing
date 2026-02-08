package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.PostEntity;

import java.util.List;

@ApplicationScoped
public class PostRepository implements PanacheRepository<PostEntity> {

    public List<PostEntity> fetchAllPosts(){
        return findAll().stream().toList();
    }

    public PostEntity fetchPostById(long userId){
        return find("WHERE userentity.id = ?1",userId).stream().toList().getFirst();
    }


}
