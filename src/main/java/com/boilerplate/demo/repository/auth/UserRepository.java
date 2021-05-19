package com.boilerplate.demo.repository.auth;

import com.boilerplate.demo.domain.model.auth.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);


	List<User> findAll();

	@Transactional
	public boolean deleteByUsername(String username);

	List<User> findByRoles_Name(String name);

	List<User> findByIdIn(Collection<String> ids);
}
