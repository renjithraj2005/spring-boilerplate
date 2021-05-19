package com.boilerplate.demo.config;

import com.boilerplate.demo.security.Constants;
import com.boilerplate.demo.security.CustomRequestLoggingFilter;
import com.boilerplate.demo.security.oauth.CustomTokenEnhancer;
import com.boilerplate.demo.security.oauth.CustomTokenStore;
import com.boilerplate.demo.service.oauth.CustomAuthorizationCodeServices;
import com.boilerplate.demo.service.oauth.CustomUserDetailsService;
import com.boilerplate.demo.service.oauth.OAuthClientDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class BeanConfig {
	
	@Bean(name = "appConfigProperties")
 	public ConfigProperties configProperties() {
 		return new ConfigProperties();
 	}

	@Bean
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}

 	@Bean
 	public PasswordEncoder passwordEncoder() {
 		return new BCryptPasswordEncoder(12);
 	}
 
 	@Bean
 	public PropertySourcesPlaceholderConfigurer getConfigProperties() {
 		return new PropertySourcesPlaceholderConfigurer();
 	}

	@Bean
	public CommonsRequestLoggingFilter logFilter() {
		CommonsRequestLoggingFilter filter = new CustomRequestLoggingFilter();
		filter.setIncludeClientInfo(true);
		filter.setIncludeQueryString(true);
		filter.setIncludePayload(true);
		filter.setMaxPayloadLength(Constants.MAX_REQUEST_DATA_LENGTH_TO_LOG);
		filter.setIncludeHeaders(true);
		return filter;
	}

 	@Bean
 	public AuthorizationCodeServices authorizationCodeServices() {
 		return new CustomAuthorizationCodeServices();
 	}
	 
 	@Bean(name = "tokenService")
 	@Primary
 	public DefaultTokenServices tokenServices() {
 		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
 		defaultTokenServices.setTokenStore(this.tokenStore());
 		defaultTokenServices.setSupportRefreshToken(true);
 		defaultTokenServices.setClientDetailsService(this.clientDetailsService());
 		defaultTokenServices.setTokenEnhancer(accessTokenConverter());
 		defaultTokenServices.setAccessTokenValiditySeconds(this.configProperties().getAccessTokenExpireTime());
 		defaultTokenServices.setRefreshTokenValiditySeconds(this.configProperties().getRefreshTokenExpireTime());
 		return defaultTokenServices;
 	}
	 
 	@Bean
 	public TokenStore tokenStore() {
 		return new CustomTokenStore(accessTokenConverter());
 	}

 	@Bean
 	public AuthenticationKeyGenerator authKeyGenerator() {
 		return new DefaultAuthenticationKeyGenerator();
 	}
	 
 	@Bean
 	public JwtAccessTokenConverter accessTokenConverter() {
 		CustomTokenEnhancer tokenConverter = new CustomTokenEnhancer();
 		tokenConverter.setSigningKey(this.configProperties().getTokenSecretKey());
 		return tokenConverter;
 	}
	     
 	@Bean
 	public TokenEnhancer tokenEnhancer() {
 		return new CustomTokenEnhancer();
 	}
	 
 	@Bean(name = "customClientDetailsService")
 	@Primary
 	public ClientDetailsService clientDetailsService() {
 		return new OAuthClientDetailsService();
 	}

 	@Bean
	@Primary
	public UserDetailsService userDetailsService(){
		return new CustomUserDetailsService();
	}

	@Bean
	public RestTemplate buildRestTemplate() {
		return new RestTemplateBuilder().build();
	}

}
