package com.zm.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.zm.common.dao.impl.BaseDaoImpl;
import com.zm.user.dao.OauthDao;
import com.zm.user.entity.Oauth;

@Repository
public class OauthDaoImpl extends BaseDaoImpl<Oauth> implements OauthDao {

	@Override
	public Oauth getOne(Integer userId, Short type, String openid,
			String token, String refreshToken, String code) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder hql = new StringBuilder("from Oauth o where 1=1");
		if (null != userId) {
			hql.append(" and o.userId= :userId");
			params.put("userId", userId);
		}
		if (null != type) {
			hql.append(" and o.type= :type");
			params.put("type", type);
		}
		if (StringUtils.isNotBlank(openid)) {
			hql.append(" and o.openid= :openid");
			params.put("openid", openid);
		}
		if (StringUtils.isNotBlank(token)) {
			hql.append(" and o.token= :token");
			params.put("token", token);
		}
		if (StringUtils.isNotBlank(refreshToken)) {
			hql.append(" and o.refreshToken= :refreshToken");
			params.put("refreshToken", refreshToken);
		}
		if (StringUtils.isNotBlank(code)) {
			hql.append(" and o.code= :code");
			params.put("code", code);
		}
		List<Oauth> result = this.find(hql.toString(), params);
		if (null != result && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}

}
