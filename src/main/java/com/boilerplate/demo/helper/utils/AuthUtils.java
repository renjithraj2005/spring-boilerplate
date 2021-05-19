package com.boilerplate.demo.helper.utils;

import com.boilerplate.demo.domain.model.auth.User;
import com.boilerplate.demo.model.user.CustomUserDetails;
import com.boilerplate.demo.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AuthUtils {

    @Autowired private UserRepository userRepository;

    public static String getReferralLink(User user){
        StringBuilder referralLinkBuilder = new StringBuilder("/register?referralId=");
        referralLinkBuilder.append(user.getId());
        referralLinkBuilder.append("&type=");
        if(user.getRoles().stream().anyMatch(role -> "ROLE_USER".equals(role.getName()))){
            referralLinkBuilder.append("A");
        }
        return referralLinkBuilder.toString();
    }

    public User getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            CustomUserDetails loggedInUser = (CustomUserDetails) principal;
            Optional<User> user = userRepository.findById(loggedInUser.getUser().getId()); //This step is to avoid lazy initialization error
            return user.get();
        }
        return null;
    }

    public CustomUserDetails loggedInUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails loggedInUser = (CustomUserDetails) authentication.getPrincipal();
        return loggedInUser;
    }




}
