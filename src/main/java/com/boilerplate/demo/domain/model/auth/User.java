package com.boilerplate.demo.domain.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.boilerplate.demo.helper.serializer.RoleSerializer;
import com.boilerplate.demo.helper.utils.CommonUtils;
import com.boilerplate.demo.security.Constants;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$")
    @Column(name = "user_name")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "disabled", nullable = false)
    private boolean disabled = false; // disabled by admin, this is not related to the retryCount

    @JsonIgnore
    @Column(name = "retry_count", nullable = false)
    private Integer retryCount = 0;

    @JsonIgnore
    @Column(name = "last_access_at")
    private Long lastAccessDate;

    @Column(name = "company_name")
    private String companyName;


    @JsonIgnore
    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private Long updatedAt;


    @JsonSerialize(using = RoleSerializer.class)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_user_roles_user")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_user_roles_role")
            )
    )
    private List<Role> roles = new ArrayList<>(0);

    @Transient
    public void onLoginFailedAttempt() {
        retryCount ++;
    }

    /**
     *
     * @return true if this account is disabled by admin or there are too many failed login attempts.
     */
    @Transient
    public boolean isLocked() {
        return disabled || isLoginFailedTooManyTimes();
    }

    @Transient
    public boolean isLoginFailedTooManyTimes() {
        return CommonUtils.nullSafe(retryCount, 0) >= Constants.MAX_LOGIN_RETRY_COUNT;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return this.lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    @JsonProperty
    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Is this user disabled by admin. This does not check the retryCount.
     * @return
     */
    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public Long getCreatedDate() {
        return createdAt;
    }

    public void setCreatedDate(Long createAt) {
        this.createdAt = createAt;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public Long getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(Long lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public Boolean hasRole(String roleName){
        return roles!= null && roles.stream().anyMatch(role -> role.getAuthority().equals(roleName));
    }

    public String getFullName(){
        StringBuilder nameBuilder = new StringBuilder();
        if( StringUtils.isNotBlank(this.getFirstName())){
            nameBuilder.append(this.getFirstName());
            nameBuilder.append(" ");
        }
        if( StringUtils.isNotBlank(this.getLastName())){
            nameBuilder.append(this.getLastName());
        }
        return nameBuilder.toString();
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' +
                ", email='" + this.username + '\'' +
                '}';
    }

    @PrePersist
    public void setCreateUpdateTime(){
        if(this.getId() != null){
            this.setUpdatedAt(System.currentTimeMillis());
            return;
        }
        Long currentTime = System.currentTimeMillis();
        this.setCreatedDate(currentTime);
        this.setUpdatedAt(currentTime);
    }

}
