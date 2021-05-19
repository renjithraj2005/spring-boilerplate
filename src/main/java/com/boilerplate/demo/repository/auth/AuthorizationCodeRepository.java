package com.boilerplate.demo.repository.auth;

import com.boilerplate.demo.domain.model.auth.AuthorizationCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationCodeRepository extends CrudRepository<AuthorizationCode, String> {
    AuthorizationCode findByCode(String code);
}
