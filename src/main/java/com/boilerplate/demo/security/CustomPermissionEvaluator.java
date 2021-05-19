package com.boilerplate.demo.security;

import com.boilerplate.demo.domain.model.auth.User;
import com.boilerplate.demo.model.user.CustomUserDetails;
import com.boilerplate.demo.domain.model.auth.Permission;
import com.boilerplate.demo.repository.auth.PermissionRepository;
import com.boilerplate.demo.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object authorities) {
        if ((authentication == null) || (authorities == null)){
            return false;
        }
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        List<Permission> methodAuthorities = permissionRepository.findByIsActiveAndIsDefaultAndNameIn(true, true, (List<String>)authorities);
        List<Permission> validAuthorities = this.getValidPermissions(userDetails.getUser(), methodAuthorities);
        return !validAuthorities.isEmpty();
    }

    private List<Permission> getValidPermissions(User user, List<Permission> methodPermissions) {
        List<Permission> userPermissions = new ArrayList<>(0);
        user.getRoles().forEach(role -> {
            userPermissions.addAll(role.getPermissions());
        });
        List<Permission> validPermissions = methodPermissions
                                            .stream()
                                            .filter(
                                                    methodPermission -> userPermissions.stream().anyMatch(
                                                            userPermission -> userPermission.equals(methodPermission)
                                                    )
                                            )
                                            .collect(Collectors.toList());
        return validPermissions;
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
        return true;
    }
}

/*
Exemplos:
OR -> @PreAuthorize("hasPermission(returnObject, {'user_create', 'user_update', 'abcd_create', 'abcd_read', 'user_read'})")
AND -> @PreAuthorize("hasPermission(returnObject, {'user_read'}) AND hasPermission(returnObject, {'user_update'})")
@PreAuthorize("hasPermission(#id, 'Foo', 'read')")
 */
