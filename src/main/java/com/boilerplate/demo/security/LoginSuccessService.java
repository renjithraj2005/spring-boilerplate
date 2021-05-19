package com.boilerplate.demo.security;

import com.boilerplate.demo.model.user.CustomUserDetails;
import com.boilerplate.demo.repository.auth.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessService implements ApplicationListener<AuthenticationSuccessEvent> {

    private static final Logger logger = LoggerFactory.getLogger(LoginSuccessService.class);


    @Autowired
    private UserRepository userRepository;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        logger.info("User have been logged in successfully.");
        if (event.getSource() instanceof UsernamePasswordAuthenticationToken &&
                event.getAuthentication().getPrincipal() instanceof CustomUserDetails) {
            //https://github.com/spring-projects/spring-security/issues/6115
            CustomUserDetails loggedInUser = (CustomUserDetails) event.getAuthentication().getPrincipal();

        }


    }
}
