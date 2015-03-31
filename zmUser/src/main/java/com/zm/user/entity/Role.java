package com.zm.user.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "u_role")
public class Role implements Serializable {

	private static final long serialVersionUID = 2763018414975436611L;

	public static final Short STATUS_NORMAL = 1;
	public static final Short STATUS_INACTIVE = 0;
	public static final Short[] STATUSES = new Short[] { STATUS_NORMAL,
			STATUS_INACTIVE };
	//


	protected Integer roleId;
	protected String roleName;
	protected Integer createBy;
	protected Short status;
	protected Date createTime;
	protected Date updateTime;
	//
	protected Set<User> users;
	protected Set<Resource> resources;

	//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
	@Column(updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(updatable = false)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@ManyToMany(mappedBy = "roles",fetch=FetchType.LAZY)
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@ManyToMany(targetEntity = Resource.class, cascade = { CascadeType.MERGE,
			CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "u_role_resource", joinColumns = { @JoinColumn(name = "roleId") }, inverseJoinColumns = { @JoinColumn(name = "resourceId") })
	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

}
