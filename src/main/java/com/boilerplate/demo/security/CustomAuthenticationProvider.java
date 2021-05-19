package com.boilerplate.demo.security;

import com.boilerplate.demo.model.user.CustomUserDetails;
import com.boilerplate.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("authenticationProvider")
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            Authentication authorizedUser = super.authenticate(authentication);
            CustomUserDetails userDetails = (CustomUserDetails)authorizedUser.getPrincipal();
            String userId = userDetails.getUser().getId();
            userService.resetFailAttempts(userId);
            return authorizedUser;
        } catch (BadCredentialsException exp) {
            userService.updateFailAttempts(authentication.getName());
            throw exp;
        }
    }
}
