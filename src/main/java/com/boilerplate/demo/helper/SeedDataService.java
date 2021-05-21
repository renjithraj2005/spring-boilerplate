package com.boilerplate.demo.helper;

import com.boilerplate.demo.domain.model.auth.User;
import com.google.common.collect.Sets;
import com.boilerplate.demo.domain.model.auth.OAuthClientDetails;
import com.boilerplate.demo.domain.model.auth.Permission;
import com.boilerplate.demo.domain.model.auth.Role;
import com.boilerplate.demo.repository.auth.PermissionRepository;
import com.boilerplate.demo.repository.auth.RoleRepository;
import com.boilerplate.demo.repository.auth.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SeedDataService {


    private final Logger logger = LoggerFactory.getLogger(SeedDataService.class);

    @Autowired
    private ClientRegistrationService oauthClientDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Transactional
    public void createOAuth2Client(String client, String secret){
        OAuthClientDetails newOAuthClient = new OAuthClientDetails();
        newOAuthClient.setClientId(client);
        newOAuthClient.setClientSecret(secret);
        newOAuthClient.setScope(Sets.newHashSet("read","write"));
        newOAuthClient.setAuthorizedGrantTypes(Sets.newHashSet("authorization_code", "refresh_token", "password", "client_credentials"));
        newOAuthClient.setRegisteredRedirectUri(Sets.newHashSet("http://localhost:8080/api"));
        newOAuthClient.setAuthorities(AuthorityUtils.createAuthorityList("ROLE_USER"));
        try{
            oauthClientDetailsService.addClientDetails(newOAuthClient);
        } catch (Exception exp){
            logger.error(exp.getMessage());
        }
    }

    @Transactional
    public void createUser(String username, String password, String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName("Admin");
        user.setLastName("User");
        user.setCompanyName("Company");
        Optional<Role> existingRole = roleRepository.findByName(role);
        user.setRoles(Arrays.asList(existingRole.get()));
        try{
            if(!userRepository.existsByUsername(user.getUsername())){
                userRepository.save(user);
            }
        } catch (Exception exp){
            logger.error(exp.getMessage());
        }
    }

    @Transactional
    public void insertRoles() {
        Permission createDat = new Permission();
        createDat.setName("create:dat");
        createDat.setDefault(true);
        createDat.setActive(true);

        Permission readDat = new Permission();
        readDat.setName("read:dat");
        readDat.setDefault(true);
        readDat.setActive(true);

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        adminRole.setDefault(true);
        adminRole.setActive(true);
        adminRole.setPermissions(Arrays.asList(createDat, readDat));



        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        userRole.setDefault(true);
        userRole.setActive(true);
        userRole.setPermissions(Arrays.asList(createDat, readDat));

        List<Role> roles = Arrays.asList(adminRole, userRole);
        roles.forEach(role -> {
            if(!roleRepository.existsByName(role.getName())){
                roleRepository.save(role);
            }
        });
    }


}
