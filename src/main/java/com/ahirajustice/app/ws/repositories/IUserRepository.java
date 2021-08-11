package com.ahirajustice.app.ws.repositories;

import com.ahirajustice.app.ws.entities.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

}
