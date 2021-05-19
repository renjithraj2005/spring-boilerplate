package com.boilerplate.demo.repository.auth;

import com.boilerplate.demo.domain.model.auth.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String roleName);
    Boolean existsByName(String name);
    List<Role> findByNameIn(List<String> names);
    List<Role> findByIsActiveAndIsDefault(Boolean isActive, Boolean isDefault);
    List<Role> findByIsActiveAndNameIn(Boolean isActive, List<String> names);
    List<Role> findByIsActiveAndIsDefaultAndNameIn(Boolean isActive, Boolean isDefault, List<String> names);
}