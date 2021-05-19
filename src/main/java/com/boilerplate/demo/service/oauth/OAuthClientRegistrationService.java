package com.boilerplate.demo.service.oauth;

import com.boilerplate.demo.domain.model.auth.OAuthClientDetails;
import com.boilerplate.demo.repository.auth.ClientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OAuthClientRegistrationService implements ClientRegistrationService {

    @Autowired
    private ClientDetailsRepository clientDetailsRepository;
    
    @Autowired 
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Override
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
        if (clientDetailsRepository.existsByClientId(clientDetails.getClientId())){
            throw new ClientAlreadyExistsException(String.format("Client with id %s already exists",clientDetails.getClientId()));
        }
        OAuthClientDetails newOAuthClient = new OAuthClientDetails();
        newOAuthClient.setClientId(clientDetails.getClientId());
        newOAuthClient.setResourceIds(clientDetails.getResourceIds());
        newOAuthClient.setClientSecret(passwordEncoder.encode(clientDetails.getClientSecret()));
        newOAuthClient.setScope(clientDetails.getScope());
        newOAuthClient.setAuthorizedGrantTypes(clientDetails.getAuthorizedGrantTypes());
        newOAuthClient.setRegisteredRedirectUri(clientDetails.getRegisteredRedirectUri());
        newOAuthClient.setAuthorities(clientDetails.getAuthorities());
        newOAuthClient.setAccessTokenValiditySeconds(clientDetails.getAccessTokenValiditySeconds());
        newOAuthClient.setRefreshTokenValiditySeconds(clientDetails.getRefreshTokenValiditySeconds());
        newOAuthClient.isAutoApprove("true");
        newOAuthClient.setAdditionalInformation(clientDetails.getAdditionalInformation());
        clientDetailsRepository.save(newOAuthClient);
    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
        OAuthClientDetails existingClientDetails = clientDetailsRepository.findByClientId(clientDetails.getClientId());
        if(existingClientDetails == null) {
            throw new NoSuchClientException(String.format("Client with id %s not found", clientDetails.getClientId()));
        }
        existingClientDetails.setResourceIds(clientDetails.getResourceIds());
        existingClientDetails.setScope(clientDetails.getScope());
        existingClientDetails.setAuthorizedGrantTypes(clientDetails.getAuthorizedGrantTypes());
        existingClientDetails.setRegisteredRedirectUri(clientDetails.getRegisteredRedirectUri());
        existingClientDetails.setAuthorities(clientDetails.getAuthorities());
        existingClientDetails.setAccessTokenValiditySeconds(clientDetails.getAccessTokenValiditySeconds());
        existingClientDetails.setRefreshTokenValiditySeconds(clientDetails.getRefreshTokenValiditySeconds());
        existingClientDetails.setAdditionalInformation(clientDetails.getAdditionalInformation());
        clientDetailsRepository.save(existingClientDetails);
    }

    @Override
    public void updateClientSecret(String clientId, String clientSecret) throws NoSuchClientException {
        OAuthClientDetails existingClientDetails = clientDetailsRepository.findByClientId(clientId);
        if(existingClientDetails == null) {
            throw new NoSuchClientException(String.format("Client with id %s not found", clientId));
        }
        existingClientDetails.setClientSecret(clientSecret);
        clientDetailsRepository.save(existingClientDetails);
    }

    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {
        clientDetailsRepository.deleteByClientId(clientId);
    }

    @Override
    public List<ClientDetails> listClientDetails() {
        List<ClientDetails> existingClients =  new ArrayList<ClientDetails>();
        List<OAuthClientDetails> clients = (List<OAuthClientDetails>)clientDetailsRepository.findAll();
        for (OAuthClientDetails detail : clients) {
            existingClients.add(detail);
        }
        return existingClients;
    }
}