package com.boilerplate.demo.model.user;

import com.boilerplate.demo.helper.serializer.RoleSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.boilerplate.demo.domain.model.auth.Role;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfile {

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "nom")
    private String lastName;

    @JsonProperty(value = "prenom")
    private String firstName;

    @JsonProperty(value = "phoneNumber")
    private String phoneNumber;

    @JsonProperty(value = "societe")
    private String company;

    @JsonProperty(value = "disabled")
    private boolean disabled = false; // by admin

    @JsonProperty(value = "roles")
    @JsonSerialize(using = RoleSerializer.class)
    private List<Role> roles;

    @JsonProperty(value = "inviteLink")
    private String referralLink;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    /**
     *
     * @return true if this user is disabled by admin
     */
    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getReferralLink() {
        return referralLink;
    }

    public void setReferralLink(String referralLink) {
        this.referralLink = referralLink;
    }


}
