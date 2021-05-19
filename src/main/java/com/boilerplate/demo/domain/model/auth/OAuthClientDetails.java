package com.boilerplate.demo.domain.model.auth;

import com.boilerplate.demo.domain.converter.auth.AuthoritiesConverter;
import com.boilerplate.demo.domain.converter.auth.OAuth2ClientAdditionalDetailsConverter;
import com.boilerplate.demo.domain.converter.common.GenericSetConverter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "oauth_client_details")
public class OAuthClientDetails implements ClientDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "resource_ids")
    @Convert(converter = GenericSetConverter.class)
    private Set<String> resourceIds;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "scope")
    @Convert(converter = GenericSetConverter.class)
    private Set<String> scope;

    @Column(name = "auth_grant_types")
    @Convert(converter = GenericSetConverter.class)
    private Set<String> authorizedGrantTypes = new LinkedHashSet<>(0);

    @Column(name = "registered_redirect_uris")
    @Convert(converter = GenericSetConverter.class)
    private Set<String> registeredRedirectUris = new LinkedHashSet<>(0);

    @Column(name = "authorities")
    @Convert(converter = AuthoritiesConverter.class)
    private Collection<GrantedAuthority> authorities = new LinkedHashSet<>(0);

    @Column(name = "access_token_validity")
    private Integer accessTokenValiditySeconds;

    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValiditySeconds;

    @Column(name = "is_auto_approve")
    private boolean isAutoApprove;

    @Column(name = "additional_info")
    @Convert(converter = OAuth2ClientAdditionalDetailsConverter.class)
    private Map<String, Object> additionalInformation = new LinkedHashMap<>(0);

    public String getId() {
        return id;
    }

    public OAuthClientDetails setId(String id) {
        this.id = id;
        return this;
    }

    public OAuthClientDetails setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public OAuthClientDetails setResourceIds(Set<String> resourceIds) {
        this.resourceIds = resourceIds;
        return this;
    }

    public OAuthClientDetails setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public OAuthClientDetails setScope(Set<String> scope) {
        this.scope = scope;
        return this;
    }

    public OAuthClientDetails setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
        return this;
    }

    public OAuthClientDetails setRegisteredRedirectUri(Set<String> registeredRedirectUris) {
        this.registeredRedirectUris = registeredRedirectUris;
        return this;
    }

    public OAuthClientDetails setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
        return this;
    }

    public OAuthClientDetails setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
        return this;
    }

    public OAuthClientDetails setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
        return this;
    }

    public boolean isAutoApprove() {
        return isAutoApprove;
    }

    public OAuthClientDetails setAutoApprove(boolean isAutoApprove) {
        this.isAutoApprove = isAutoApprove;
        return this;
    }

    public OAuthClientDetails setAdditionalInformation(Map<String, Object> additionalInformation) {
        this.additionalInformation = additionalInformation;
        return this;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return resourceIds;
    }

    @Override
    public boolean isSecretRequired() {
        return clientSecret != null;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public boolean isScoped() {
        return this.scope != null && !this.scope.isEmpty();
    }

    @Override
    public Set<String> getScope() {
        return scope;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return registeredRedirectUris;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return isAutoApprove;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return additionalInformation;
    }

    @Override
    public String toString() {
        return "BaseClientDetails [clientId=" + this.clientId + ", scope=" + this.scope + ", resourceIds=" + this.resourceIds + ", authorizedGrantTypes=" + this.authorizedGrantTypes + ", registeredRedirectUris=" + this.registeredRedirectUris + ", authorities=" + this.authorities + ", accessTokenValiditySeconds=" + this.accessTokenValiditySeconds + ", refreshTokenValiditySeconds=" + this.refreshTokenValiditySeconds + ", additionalInformation=" + this.additionalInformation + "]";
    }
}