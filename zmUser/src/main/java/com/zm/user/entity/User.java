package com.zm.user.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "u_user")
@Cacheable  
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)  
public class User implements Serializable {

	private static final long serialVersionUID = 1862965270594515842L;
	// static
	/**
	 * 匿名用户ID
	 */
	public static final Integer ANONYMOUS_USER_ID = -1;

	public static final Short STATUS_NORMAL = 1;
	public static final Short STATUS_INACTIVE = 0;
	public static final Short[] STATUSES = new Short[] { STATUS_NORMAL,
			STATUS_INACTIVE };
	//

	protected Integer userId;
	protected String username;
	protected String password;
	protected String email;
	protected String mobile;
	protected String telephone;
	protected Short status;
	protected String name;
	protected Date lastLoginTime;
	protected String lastLoginIp;
	protected Integer createBy;
	protected Date updateTime;
	protected Date createTime;
	protected Date lastFree;//最后一次享受过免费洗车时间
	//
	protected Set<Role> roles;
	protected Set<Car> cars;
	protected Set<CarAddr> carAddrs;
	protected Set<Porder> porders;
	protected Set<Remark> remarks;
	//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(updatable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	@Column(updatable = false)
	public Integer getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}
	@Column(updatable = false)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastFree() {
		return lastFree;
	}

	public void setLastFree(Date lastFree) {
		this.lastFree = lastFree;
	}

	//
	@ManyToMany(targetEntity = Role.class,fetch = FetchType.LAZY)
	@JoinTable(name = "u_user_role", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = { @JoinColumn(name = "roleId") })
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	@OrderBy("carId")
	public Set<Car> getCars() {
		return cars;
	}

	public void setCars(Set<Car> cars) {
		this.cars = cars;
	}
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	public Set<CarAddr> getCarAddrs() {
		return carAddrs;
	}

	public void setCarAddrs(Set<CarAddr> carAddrs) {
		this.carAddrs = carAddrs;
	}
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	public Set<Porder> getPorders() {
		return porders;
	}

	public void setPorders(Set<Porder> porders) {
		this.porders = porders;
	}
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	public Set<Remark> getRemarks() {
		return remarks;
	}

	public void setRemarks(Set<Remark> remarks) {
		this.remarks = remarks;
	}

}
