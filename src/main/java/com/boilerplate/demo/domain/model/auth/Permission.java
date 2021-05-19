package com.boilerplate.demo.domain.model.auth;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "permissions")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_default")
    private Boolean isDefault;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Role> roles = new ArrayList<>(0);

    @Override
    public boolean equals(Object another) {
        if (another == null || !(another instanceof Permission))
            return false;
        Permission anotherPermission = (Permission) another;
        return (anotherPermission.id != null && this.id != null && anotherPermission.id.equals(this.id));
    }

    public Long getId() {
        return id;
    }

    public Permission setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Permission setName(String name) {
        this.name = name;
        return this;
    }

    public Boolean isDefault() {
        return isDefault;
    }

    public Permission setDefault(Boolean aDefault) {
        isDefault = aDefault;
        return this;
    }

    public Boolean isActive() {
        return isActive;
    }

    public Permission setActive(Boolean active) {
        isActive = active;
        return this;
    }
}
