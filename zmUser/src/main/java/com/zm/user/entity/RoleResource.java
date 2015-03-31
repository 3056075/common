package com.zm.user.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "u_role_resource")
public class RoleResource implements Serializable {
	private static final long serialVersionUID = -2463125257600797809L;
	//

	protected Integer roleResourceId;
	protected Role role;
	protected Resource resource;

	//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getRoleResourceId() {
		return roleResourceId;
	}

	public void setRoleResourceId(Integer roleResourceId) {
		this.roleResourceId = roleResourceId;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleId")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resourceId")
	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
}
