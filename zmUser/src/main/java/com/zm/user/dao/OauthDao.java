package com.zm.user.dao;

import com.zm.common.dao.BaseDao;
import com.zm.user.entity.Oauth;

public interface OauthDao extends BaseDao<Oauth> {
	public Oauth getOne(Integer userId,Short type,String openid,String token,String refreshToken,String code);

}
