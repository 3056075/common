package com.zm.user.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "u_oauth")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Oauth implements Serializable {
	public static final Short TYPE_WEIXIN = 1;
	public static final Short TYPE_QQ = 5;
	public static final Short TYPE_WEIBO = 10;
	public static final Short[] TYPES = new Short[] { TYPE_WEIXIN, TYPE_QQ,
			TYPE_WEIBO };
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Integer oauthId;
	protected Short type;
	protected String openid;
	protected String account;
	protected String code;
	protected String token;
	protected Integer expires;
	protected Date updateTime;
	protected Date tokenCreateTime;
	protected Date createTime;
	//
	protected User user;
	public Integer getOauthId() {
		return oauthId;
	}
	public void setOauthId(Integer oauthId) {
		this.oauthId = oauthId;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getExpires() {
		return expires;
	}
	public void setExpires(Integer expires) {
		this.expires = expires;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getTokenCreateTime() {
		return tokenCreateTime;
	}
	public void setTokenCreateTime(Date tokenCreateTime) {
		this.tokenCreateTime = tokenCreateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
