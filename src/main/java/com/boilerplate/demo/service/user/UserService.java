package com.boilerplate.demo.service.user;

import com.boilerplate.demo.domain.model.auth.User;
import com.boilerplate.demo.model.common.CustomResponse;
import com.boilerplate.demo.domain.converter.common.StringEncryptionAttributeConverter;
import com.boilerplate.demo.domain.model.auth.Role;
import com.boilerplate.demo.helper.UserProfileBuilder;
import com.boilerplate.demo.helper.utils.AuthUtils;
import com.boilerplate.demo.model.common.CustomException;
import com.boilerplate.demo.model.user.NewUser;
import com.boilerplate.demo.model.user.UserProfile;
import com.boilerplate.demo.repository.auth.PermissionRepository;
import com.boilerplate.demo.repository.auth.RoleRepository;
import com.boilerplate.demo.repository.auth.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private AuthUtils authUtils;

    @Autowired
    @Qualifier(value = "tokenService")
    private DefaultTokenServices tokenService;


    @Autowired
    private StringEncryptionAttributeConverter converter;


    public UserProfile createUser(NewUser newUserDetails) throws Exception {
        this.verifyFormDetails(newUserDetails);
        try {
            User newUser = new User();
            newUser.setUsername(newUserDetails.getUsername());
            newUser.setPassword(passwordEncoder.encode(newUserDetails.getPassword()));
            newUser.setFirstName(newUserDetails.getFirstName());
            newUser.setLastName(newUserDetails.getLastName());
            newUser.setCompanyName(newUserDetails.getCompany());
            newUser.setCreatedDate(System.currentTimeMillis());
            newUser.setUpdatedAt(System.currentTimeMillis());
            List<Role> existingRoles = roleRepository.findByIsActiveAndIsDefaultAndNameIn(true, true, newUserDetails.getRoles());
            if(existingRoles.isEmpty()){
                throw new CustomException(HttpStatus.BAD_REQUEST).message("Given Role not allowed");
            }
            newUser.setRoles(existingRoles);
            newUser = userRepository.save(newUser);
            logger.info(String.format("User with username %s successfully created having id %s", newUser.getUsername(), newUser.getId()));
            UserProfile userProfile = UserProfileBuilder.build(newUser);

            return userProfile;
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Error while creating user with username " + newUserDetails.getUsername());
        }
    }

    public void verifyFormDetails(NewUser newUser) throws Exception{
        if (newUser == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST).message(String.format("User object must have valid data."));
        }
        if (StringUtils.isBlank(newUser.getUsername()) || StringUtils.isBlank(newUser.getPassword())) {
            throw new CustomException(HttpStatus.BAD_REQUEST).message(String.format("Username or password is empty"));
        }
        if (StringUtils.isBlank(newUser.getCompany())) {
            throw new CustomException(HttpStatus.BAD_REQUEST).message(String.format("Company name is emtpy"));
        }
        if (userRepository.existsByUsername(newUser.getUsername())) {
            throw new CustomException(HttpStatus.BAD_REQUEST)
                    .message(String.format("User with username %s already exists", newUser.getUsername()));
        }
    }

    public CustomResponse deleteUser(String userId) throws CustomException {
        try {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
                logger.info("User " + userId + " successfully deleted.");
                return CustomResponse.builder();
            } else {
                throw new CustomException(HttpStatus.NOT_FOUND).message("Username does not exist");
            }
        } catch (Exception expObj) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message("Exception occurred while attempting to delete user:  " + userId);
        }
    }



    public List<UserProfile> getUserProfiles(Collection<String> ids) {
    	return userRepository.findByIdIn(ids).stream().map(UserProfileBuilder::build).collect(Collectors.toList());
    }

    public UserProfile getUserProfile(String id) throws CustomException {
        Optional<User> existingUser = userRepository.findById(id);
        if (!existingUser.isPresent()) {
            throw new CustomException(HttpStatus.NOT_FOUND).message(String.format("User with id %s not found", id));
        }
        User user = existingUser.get();
        return UserProfileBuilder.build(user);
    }

    public UserProfile searchUser(String id) throws CustomException {
        Optional<User> existingUser = userRepository.findById(id);
        if (!existingUser.isPresent()) {
            throw new CustomException(HttpStatus.NOT_FOUND).message(String.format("User with id %s not found", id));
        }
        User user = existingUser.get();
        return UserProfileBuilder.build(user);
    }

    public CustomResponse changePassword(String id, String oldPassword, String newPassword) throws CustomException {
        Optional<User> existingUser = userRepository.findById(id);
        if (!existingUser.isPresent()) {
            throw new CustomException(HttpStatus.NOT_FOUND).message(String.format("User with id %s not found", id));
        }
        User user = existingUser.get();
        if(!passwordEncoder.matches(oldPassword, user.getPassword())){
            throw new CustomException(HttpStatus.BAD_REQUEST).message("Old Password is not correct");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        try{
            userRepository.save(user);
        }catch (Exception exp){
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR).message("Error while updating password. Please try again.");
        }
        return CustomResponse.builder().message("Password changed successfully");
    }

    public void logout(String id, String token)  {
        tokenService.revokeToken(token);
        logger.info("Access Token and refresh token removed successfully for user: " + id);
    }

    public void resetFailAttempts(String userId) {
        Optional<User> existingUser = userRepository.findById(userId);
        if(!existingUser.isPresent() || existingUser.get().getRetryCount() == 0){
            return;
        }
        User user = existingUser.get();
        user.setRetryCount(0);
        userRepository.save(user);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void updateFailAttempts(String username) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if(!existingUser.isPresent() || existingUser.get().isLoginFailedTooManyTimes()){
            return;
        }
        User user = existingUser.get();
        user.onLoginFailedAttempt();
        userRepository.save(user);
    }


    public List<UserProfile> getAllUsers(){
        List<User> users = userRepository.findAll();
        List<UserProfile> profiles = users.stream().map(user -> UserProfileBuilder.build(user)).collect(Collectors.toList());
        return profiles;
    }

    public Optional<User> getUserActualDetails(final String userId){
        return userRepository.findById(userId);
    }

}
