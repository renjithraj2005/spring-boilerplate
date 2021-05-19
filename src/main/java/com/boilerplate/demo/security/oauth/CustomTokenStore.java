package com.boilerplate.demo.security.oauth;


import com.boilerplate.demo.domain.model.auth.AccessToken;
import com.boilerplate.demo.domain.model.auth.RefreshToken;
import com.boilerplate.demo.repository.auth.AccessTokenRepository;
import com.boilerplate.demo.repository.auth.RefreshTokenRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomTokenStore extends JwtTokenStore {
	
	@Autowired
	private AuthenticationKeyGenerator authenticationKeyGenerator;

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public CustomTokenStore(JwtAccessTokenConverter jwtTokenEnhancer) {
		super(jwtTokenEnhancer);
	}

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken accessToken) {
        return readAuthentication(accessToken.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        Optional<AccessToken> accessToken = accessTokenRepository.findById(extractTokenKey(token));
        return accessToken.isPresent() ? accessToken.get().getAuthentication() : null;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        OAuth2RefreshToken refreshToken = accessToken.getRefreshToken() != null ? accessToken.getRefreshToken() : null;
        if (readAccessToken(accessToken.getValue()) != null) {
            this.removeAccessToken(accessToken);
        }
        AccessToken newAccessToken = new AccessToken();
        newAccessToken.setTokenId(extractTokenKey(accessToken.getValue()));
        newAccessToken.setToken(accessToken);
        newAccessToken.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
        newAccessToken.setUsername(authentication.isClientOnly() ? null : authentication.getName());
        newAccessToken.setClientId(authentication.getOAuth2Request().getClientId());
        newAccessToken.setAuthentication(authentication);
        newAccessToken.setRefreshToken(refreshToken);
        accessTokenRepository.save(newAccessToken);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        Optional<AccessToken> accessToken = accessTokenRepository.findById(extractTokenKey(tokenValue));
        return accessToken.isPresent() ? accessToken.get().getToken() : null;
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken oAuth2AccessToken) {
        accessTokenRepository.deleteById(extractTokenKey(oAuth2AccessToken.getValue()));
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        RefreshToken newRefreshToken = new RefreshToken();
        newRefreshToken.setTokenId(extractTokenKey(refreshToken.getValue()));
        newRefreshToken.setToken(refreshToken);
        newRefreshToken.setAuthentication(authentication);
        refreshTokenRepository.save(newRefreshToken);
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findById(extractTokenKey(tokenValue));
        return refreshToken.isPresent() ? refreshToken.get().getToken() : null;
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken refreshToken) {
        Optional<RefreshToken> existingRefreshToken = refreshTokenRepository.findById(extractTokenKey(extractTokenKey(refreshToken.getValue())));
        return existingRefreshToken.isPresent() ? existingRefreshToken.get().getAuthentication() : null;
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken refreshToken) {
        refreshTokenRepository.deleteById(extractTokenKey(refreshToken.getValue()));
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        accessTokenRepository.deleteById(extractTokenKey(refreshToken.getValue()));
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        OAuth2AccessToken accessToken = null;
        String authenticationId = authenticationKeyGenerator.extractKey(authentication);
        Optional<AccessToken> existingAccessToken = accessTokenRepository.findById(authenticationId);
        if (existingAccessToken.isPresent()) {
            accessToken = existingAccessToken.get().getToken();
            if(accessToken != null && !authenticationId.equals(this.authenticationKeyGenerator.extractKey(this.readAuthentication(accessToken)))) {
                this.removeAccessToken(accessToken);
                this.storeAccessToken(accessToken, authentication);
            }
        }
        return accessToken;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String username) {
        List<AccessToken> accessTokens = accessTokenRepository.findByClientIdAndUsername(clientId, username);
        Collection<OAuth2AccessToken> tokens = accessTokens.stream().map(accessToken -> accessToken.getToken()).collect(Collectors.toList());
        return tokens;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        List<AccessToken> accessTokens = accessTokenRepository.findByClientId(clientId);
        Collection<OAuth2AccessToken> tokens = accessTokens.stream().map(accessToken -> accessToken.getToken()).collect(Collectors.toList());
        return tokens;
    }

    private String extractTokenKey(String value) {
        if(StringUtils.isBlank(value)) {
            return null;
        }
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var5) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
        }
        try {
            byte[] e = digest.digest(value.getBytes("UTF-8"));
            return String.format("%032x", new Object[]{new BigInteger(1, e)});
        } catch (UnsupportedEncodingException var4) {
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
        }
    }
}