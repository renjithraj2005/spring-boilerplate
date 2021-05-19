package com.boilerplate.demo.domain.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

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

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "role_permissions",
			joinColumns = @JoinColumn(
					name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_role_permissions_role")
			),
			inverseJoinColumns = @JoinColumn(
					name = "permission_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_role_permissions_permission")
			)
	)
	private List<Permission> permissions = new ArrayList<>(0);

	@ManyToMany(mappedBy = "roles")
	private List<User> users = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public Role setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Role setName(String name) {
		this.name = name;
		return this;
	}

	public Boolean getDefault() {
		return isDefault;
	}

	public Role setDefault(Boolean aDefault) {
		isDefault = aDefault;
		return this;
	}

	public Boolean getActive() {
		return isActive;
	}

	public Role setActive(Boolean active) {
		isActive = active;
		return this;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String getAuthority() {
		return name;
	}
}
