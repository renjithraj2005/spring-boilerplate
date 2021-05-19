package com.boilerplate.demo.domain.model.auth;

import com.boilerplate.demo.domain.converter.auth.OAuth2AuthenticationConverter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.persistence.*;

@Entity
@Table(name = "oauth_code")
public class AuthorizationCode {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "code")
    private String code;

    @Column(name = "authentication", columnDefinition="LONGBLOB")
    @Convert(converter = OAuth2AuthenticationConverter.class)
    private OAuth2Authentication authentication;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public OAuth2Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(OAuth2Authentication authentication) {
        this.authentication = authentication;;
    }
}