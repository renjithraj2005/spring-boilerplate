package com.boilerplate.demo.service.oauth;

import com.boilerplate.demo.domain.model.auth.AuthorizationCode;
import com.boilerplate.demo.repository.auth.AuthorizationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

public class CustomAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

    @Autowired
    private AuthorizationCodeRepository authorizationCodeRepository;

    @Override
    protected void store(String code, OAuth2Authentication authentication) {
    	AuthorizationCode authorizationCode = new AuthorizationCode();
        authorizationCode.setCode(code);
        authorizationCode.setAuthentication(authentication);
        authorizationCodeRepository.save(authorizationCode);
    }

    @Override
    protected OAuth2Authentication remove(String code) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where(Constants.AUTHORIZATION_CODE.getValue()).is(code));
        AuthorizationCode authorizationCode = authorizationCodeRepository.findByCode(code);
        OAuth2Authentication authentication = authorizationCode != null ? authorizationCode.getAuthentication() : null;
        return authentication;
    }
}