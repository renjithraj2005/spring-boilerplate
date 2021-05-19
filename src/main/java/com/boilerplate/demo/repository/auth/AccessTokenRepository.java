package com.boilerplate.demo.repository.auth;

import com.boilerplate.demo.domain.model.auth.AccessToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccessTokenRepository extends CrudRepository<AccessToken, String> {

    List<AccessToken> findByClientIdAndUsername(String clientId, String username);
    List<AccessToken> findByClientId(String clientId);
    Optional<AccessToken> findByAuthenticationId(String authenticationId);
}
