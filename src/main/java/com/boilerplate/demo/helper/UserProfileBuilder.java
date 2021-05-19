package com.boilerplate.demo.helper;

import com.boilerplate.demo.domain.model.auth.User;
import com.boilerplate.demo.helper.utils.AuthUtils;
import com.boilerplate.demo.model.user.UserProfile;

public class UserProfileBuilder {

    public static UserProfile build(User user){
        if(user == null) return null;
        UserProfile userProfile = new UserProfile();
        userProfile.setId(user.getId());
        userProfile.setEmail(user.getUsername());
        userProfile.setFirstName(user.getFirstName());
        userProfile.setLastName(user.getLastName());
        userProfile.setCompany(user.getCompanyName());
        userProfile.setDisabled(user.isDisabled());
        userProfile.setRoles(user.getRoles());
        userProfile.setReferralLink(AuthUtils.getReferralLink(user));
        return userProfile;
    }
}
