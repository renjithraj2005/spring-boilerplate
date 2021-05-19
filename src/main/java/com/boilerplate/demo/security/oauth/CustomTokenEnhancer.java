package com.boilerplate.demo.security.oauth;

import com.boilerplate.demo.model.user.CustomUserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;


public class CustomTokenEnhancer extends JwtAccessTokenConverter {
  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
      final Map<String, Object> additionalInfo = new HashMap<>();
      CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
      additionalInfo.put("id", userDetails.getUser().getId());
      ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
      accessToken = super.enhance(accessToken, authentication);
      ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(new HashMap<>());
      return accessToken;
  }
}
