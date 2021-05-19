package com.boilerplate.demo.repository.auth;

import com.boilerplate.demo.domain.model.auth.Permission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long> {
    Optional<Permission> findByName(String permissionName);
    Boolean existsByName(String permissionName);
    List<Permission> findAll();
    List<Permission> findByNameIn(List<String> names);
    List<Permission> findByIsActiveAndIsDefault(Boolean isActive, Boolean isDefault);
    List<Permission> findByIsActiveAndNameIn(Boolean isActive, List<String> names);
    List<Permission> findByIsActiveAndIsDefaultAndNameIn(Boolean isActive, Boolean isDefault, List<String> names);
}