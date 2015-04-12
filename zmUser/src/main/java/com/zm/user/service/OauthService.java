package com.zm.user.service;

import com.zm.common.exception.ZmException;
import com.zm.common.pagination.BasePagination;
import com.zm.user.entity.Oauth;
import com.zm.user.entity.User;

public interface OauthService {
	public User wxOauth(String code) throws ZmException;
	
	public void searchOauth(BasePagination<Oauth> page);
	public String getDefaultUsername(String oid,Short oauthType);
	public String getDefaultPassword();
}
