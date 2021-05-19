package com.boilerplate.demo.controller;

import com.boilerplate.demo.common.RestResources;
import com.boilerplate.demo.domain.model.auth.User;
import com.boilerplate.demo.helper.utils.AuthUtils;
import com.boilerplate.demo.model.common.CustomException;
import com.boilerplate.demo.model.common.CustomResponse;
import com.boilerplate.demo.model.user.CustomUserDetails;
import com.boilerplate.demo.model.user.NewUser;
import com.boilerplate.demo.model.user.UserProfile;
import com.boilerplate.demo.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(RestResources.USER_ROOT)
@Api(tags = "users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
	
    @Autowired private UserService userService;

    @Autowired private AuthUtils authUtils;

    @PostMapping(value = RestResources.USER_POST_REGISTER, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Register a new user")
    public ResponseEntity<?> create(@RequestBody NewUser newUserDetails) throws Exception {
        logger.info("Creating a new user");
        String referredRole = !newUserDetails.getRoles().isEmpty() ? newUserDetails.getRoles().get(0) : null;
        newUserDetails.setRoles(Arrays.asList(referredRole));
        UserProfile user = this.userService.createUser(newUserDetails);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping(value = RestResources.USER_GET_DETAILS, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @ApiOperation(value = "Returns User Details like Email, First Name, Last Name etc.", response = UserProfile.class)
    public ResponseEntity<?> getUserProfile(@RequestParam String clientId) throws CustomException{
        UserProfile profile = this.userService.searchUser(clientId);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @GetMapping(value = RestResources.USER_GET_ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Returns list of Users with Details like Email, First Name, Last Name etc.", response = List.class)
    public ResponseEntity<?> getUsers() throws CustomException{
        List<UserProfile> users = this.userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = RestResources.USER_GET_ME, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @ApiOperation(value = "Get User Details", response = UserDetails.class)
    public ResponseEntity<?> getOwnProfile(OAuth2Authentication authentication) throws CustomException{
        logger.info("Fetching User Details");
        CustomUserDetails loggedInUser = (CustomUserDetails) authentication.getPrincipal();
        UserProfile profile = this.userService.getUserProfile(loggedInUser.getUser().getId());
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PutMapping(value = "/password", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @ApiOperation(value = "Change Login Password", response = User.class)
    public ResponseEntity<?> changePassword(OAuth2Authentication authentication, @RequestBody Map<String, String> request) throws CustomException{
        logger.info("Fetching User Details");
        CustomUserDetails loggedInUser = (CustomUserDetails) authentication.getPrincipal();
        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");
        CustomResponse passwordChange = this.userService.changePassword(loggedInUser.getUser().getId(), oldPassword, newPassword);
        return new ResponseEntity<>(passwordChange, HttpStatus.OK);
    }


  
    @DeleteMapping(value = RestResources.USER_DELETE_LOGOUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiOperation(value = "Logout")
    public ResponseEntity<CustomResponse> logout(@NotNull OAuth2Authentication authentication,
                                                 @NotNull @RequestHeader(name = "Authorization") String authHeader){
        CustomUserDetails loggedInUser = (CustomUserDetails) authentication.getPrincipal();
        String token = authHeader.split("Bearer")[1].trim();
        this.userService.logout(loggedInUser.getUsername(), token);
        return new ResponseEntity<CustomResponse>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = RestResources.USER_DELETE_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiOperation(value = "Delete User")
    public ResponseEntity<CustomResponse> deleteUser(@PathVariable String id) throws CustomException{
        logger.info("Delete a user with username: " + id);
        CustomResponse response = this.userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
