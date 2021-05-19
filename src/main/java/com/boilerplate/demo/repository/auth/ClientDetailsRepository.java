package com.boilerplate.demo.repository.auth;

import com.boilerplate.demo.domain.model.auth.OAuthClientDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDetailsRepository extends CrudRepository<OAuthClientDetails, String> {
    OAuthClientDetails findByClientId(String clientId);
    void deleteByClientId(String clientId);
    Boolean existsByClientId(String clientId);
}
