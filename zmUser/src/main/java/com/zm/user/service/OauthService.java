package com.zm.user.service;

import com.zm.common.exception.ZmException;
import com.zm.user.entity.User;

public interface OauthService {
	public User wxOauth(String code) throws ZmException;
	
	public String getDefaultUsername(String oid);
	public String getDefaultPassword();
}
