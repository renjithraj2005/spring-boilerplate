package com.boilerplate.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@PropertySources({
        @PropertySource(value= "classpath:api.properties"),
        @PropertySource(value="classpath:application.properties")
})

@Configuration
public class ConfigProperties {

    @Value("${security.jwt.access.token.expire.length}")
    private int accessTokenExpireTime;

    @Value("${security.jwt.refresh.token.expire.length}")
    private int refreshTokenExpireTime;

    @Value("${security.jwt.token.secret-key}")
    private String tokenSecretKey;

    @Value("${security.oauth2.client.id}")
    private String defaultOAuth2Client;

    @Value("${security.oauth2.client.secret}")
    private String defaultOAuth2ClientSecret;

    @Value("${swagger.api.key.name}")
    private String swaggerApiKeyName;

    @Value("${demo.admin.id}")
    private String adminId;

    @Value("${demo.admin.password}")
    private String adminPassword;


    public int getAccessTokenExpireTime() {
        return accessTokenExpireTime;
    }

    public void setAccessTokenExpireTime(int accessTokenExpireTime) {
        this.accessTokenExpireTime = accessTokenExpireTime;
    }

    public int getRefreshTokenExpireTime() {
        return refreshTokenExpireTime;
    }

    public void setRefreshTokenExpireTime(int refreshTokenExpireTime) {
        this.refreshTokenExpireTime = refreshTokenExpireTime;
    }

    public String getTokenSecretKey() {
        return tokenSecretKey;
    }

    public void setTokenSecretKey(String tokenSecretKey) {
        this.tokenSecretKey = tokenSecretKey;
    }

    public String getDefaultOAuth2Client() {
        return defaultOAuth2Client;
    }

    public void setDefaultOAuth2Client(String defaultOAuth2Client) {
        this.defaultOAuth2Client = defaultOAuth2Client;
    }

    public String getDefaultOAuth2ClientSecret() {
        return defaultOAuth2ClientSecret;
    }

    public void setDefaultOAuth2ClientSecret(String defaultOAuth2ClientSecret) {
        this.defaultOAuth2ClientSecret = defaultOAuth2ClientSecret;
    }

    public String getSwaggerApiKeyName() {
        return swaggerApiKeyName;
    }

    public void setSwaggerApiKeyName(String swaggerApiKeyName) {
        this.swaggerApiKeyName = swaggerApiKeyName;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}
