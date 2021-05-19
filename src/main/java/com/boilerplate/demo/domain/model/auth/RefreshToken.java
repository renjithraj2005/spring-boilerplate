package com.boilerplate.demo.domain.model.auth;

import com.boilerplate.demo.domain.converter.auth.OAuth2AuthenticationConverter;
import com.boilerplate.demo.domain.converter.auth.OAuth2RefreshTokenConverter;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "oauth_refresh_token")
public class RefreshToken implements Serializable {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String tokenId;

    @Column(name = "token", columnDefinition="LONGBLOB")
    @Convert(converter = OAuth2RefreshTokenConverter.class)
    private OAuth2RefreshToken token;

    @Column(name = "authentication", columnDefinition="LONGBLOB")
    @Convert(converter = OAuth2AuthenticationConverter.class)
    private OAuth2Authentication authentication;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public OAuth2RefreshToken getToken() {
        return token;
    }

    public void setToken(OAuth2RefreshToken token) {
        this.token = token;
    }

    public OAuth2Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(OAuth2Authentication authentication) {
        this.authentication = authentication;
    }
}