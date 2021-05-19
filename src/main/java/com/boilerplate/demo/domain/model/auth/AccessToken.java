package com.boilerplate.demo.domain.model.auth;

import com.boilerplate.demo.domain.converter.auth.OAuth2AccessTokenConverter;
import com.boilerplate.demo.domain.converter.auth.OAuth2AuthenticationConverter;
import com.boilerplate.demo.domain.converter.auth.OAuth2RefreshTokenConverter;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "oauth_access_token")
public class AccessToken implements Serializable {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String tokenId;

    @Column(name = "token", columnDefinition="LONGBLOB")
    @Convert(converter = OAuth2AccessTokenConverter.class)
    private OAuth2AccessToken token;

    @Column(name = "auth_id")
    private String authenticationId;

    @Column(name = "user_name")
    private String username;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "authentication", columnDefinition="LONGBLOB")
    @Convert(converter = OAuth2AuthenticationConverter.class)
    private OAuth2Authentication authentication;

    @Column(name = "refresh_token", columnDefinition="LONGBLOB")
    @Convert(converter = OAuth2RefreshTokenConverter.class)
    private OAuth2RefreshToken refreshToken;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public OAuth2AccessToken getToken() {
        return token;
    }

    public void setToken(OAuth2AccessToken token) {
        this.token = token;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public OAuth2Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(OAuth2Authentication authentication) {
        this.authentication = authentication;
    }

    public OAuth2RefreshToken getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(OAuth2RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }
}