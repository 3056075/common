package com.zm.user.dao;

import java.util.List;

import com.zm.common.dao.BaseDao;
import com.zm.common.pagination.BasePagination;
import com.zm.user.entity.Oauth;

public interface OauthDao extends BaseDao<Oauth> {
	public Oauth getOne(Integer userId,Short type,String openid,String token,String refreshToken,String code);

	public Long searchOauthCount(BasePagination<Oauth> page) ;
	public List<Oauth> searchOauth(BasePagination<Oauth> page) ;	
	
}
