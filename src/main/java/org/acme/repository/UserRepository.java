package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.UserEntity;

import java.util.List;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity> {
    public List<UserEntity> listarTodosUsuarios(){
        return findAll().stream().toList();
    }

    public UserEntity listarUsuarioPorId(long idUser){
        return findById(idUser);
    }

    public UserEntity listarUsuarioPorNome(String username){
        return find("WHERE name = ?1",username).stream().toList().getFirst();
    }
}
