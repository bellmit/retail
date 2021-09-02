package com.ahirajustice.app.repositories;

import java.util.Optional;

import com.ahirajustice.app.entities.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
