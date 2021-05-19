package com.boilerplate.demo.service.oauth;

import com.boilerplate.demo.domain.model.auth.User;
import com.boilerplate.demo.model.user.CustomUserDetails;
import com.boilerplate.demo.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> existingUser = userRepository.findByUsername(username);
        if (!existingUser.isPresent()) {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }
        User user = existingUser.get();
        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUser(user);
        userDetails.setEnabled(!user.isLocked());
        userDetails.setAccountLocked(user.isLocked());
        return userDetails;
    }
}
