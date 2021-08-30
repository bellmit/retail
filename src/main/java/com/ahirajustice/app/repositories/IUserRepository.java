package com.ahirajustice.app.repositories;

import com.ahirajustice.app.entities.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

}
