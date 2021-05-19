package com.boilerplate.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Autowired
	@Qualifier(value = "tokenService")
	private DefaultTokenServices tokenService;
	
	@Autowired private TokenStore tokenStore;

	@Autowired private CustomAccessDeniedHandler customAccessDeniedHandler;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.requestMatcher(new OAuthRequestedMatcher())
        	.anonymous().disable()
        	.authorizeRequests()
			.antMatchers(Constants.WEB_IGNORE).permitAll()
			.anyRequest().authenticated()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        	.and().exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);
	}
	
	@Override
    public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId("app-resource-1")
				 .resourceId("app-resource-2")
				 .tokenStore(this.tokenStore)
				 .tokenServices(this.tokenService);
    }
	
	private static class OAuthRequestedMatcher implements RequestMatcher {
        @Override
		public boolean matches(HttpServletRequest request) {
      		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
            boolean hasOauth2Token = (auth != null) && auth.startsWith("Bearer");
            boolean hasAccessToken = request.getParameter("access_token")!=null;
            return hasOauth2Token || hasAccessToken;
        }
    }
}