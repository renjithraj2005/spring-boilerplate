package com.boilerplate.demo.service.oauth;

import com.boilerplate.demo.domain.model.auth.OAuthClientDetails;
import com.boilerplate.demo.repository.auth.ClientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class OAuthClientDetailsService implements ClientDetailsService {

    @Autowired
    private ClientDetailsRepository clientDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OAuthClientDetails clientDetails = clientDetailsRepository.findByClientId(clientId);
        //clientDetails.setClientSecret(passwordEncoder.d)
        if (clientDetails == null) {
            throw new ClientRegistrationException(String.format("Client with id %s not found", clientId));
        }
        return clientDetails;
    }
}