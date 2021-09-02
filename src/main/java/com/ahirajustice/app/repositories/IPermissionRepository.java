package com.ahirajustice.app.repositories;

import java.util.Optional;

import com.ahirajustice.app.entities.Permission;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPermissionRepository extends CrudRepository<Permission, Long> {

    Optional<Permission> findByName(String name);

}