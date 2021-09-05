package com.ahirajustice.app.repositories;

import java.util.Optional;

import com.ahirajustice.app.entities.Role;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);

}