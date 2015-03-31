package com.zm.user.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
@Entity
@Table(name="u_resource")
public class Resource implements Serializable{
	private static final long serialVersionUID = 5033199790856080856L;
	//static
	public static final Short STATUS_NORMAL = 1;
	public static final Short STATUS_INACTIVE = 0;
	public static final Short[] STATUSES = new Short[]{STATUS_NORMAL,STATUS_INACTIVE};
	
	public static final Short MENU_YES = 1;
	public static final Short MENU_NO = 0;
	public static final Short[] ENUES = new Short[]{MENU_YES,MENU_NO};
	//

	protected Integer resourceId;
	protected String resourceName;
	protected String shortName;
	protected String code;
	protected String url;
	protected Short status;
	protected Short menu;
	protected Integer rank;

//	protected Resource parent;
	protected String remark;
	//

	protected Set<Role> roles;
	//
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public Short getMenu() {
		return menu;
	}
	public void setMenu(Short menu) {
		this.menu = menu;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}

//	public Resource getParent() {
//		return parent;
//	}
//	public void setParent(Resource parent) {
//		this.parent = parent;
//	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@ManyToMany(mappedBy = "resources",fetch=FetchType.LAZY)
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
