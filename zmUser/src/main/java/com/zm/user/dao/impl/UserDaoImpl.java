package com.zm.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zm.common.dao.impl.BaseDaoImpl;
import com.zm.common.pagination.BasePagination;
import com.zm.common.utils.StringUtils;
import com.zm.user.dao.UserDao;
import com.zm.user.entity.User;
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Override
	public User findByUsername(String username) {		
		return this.get("select u from User u where u.username=?", username);
	}

	@Override
	public Long searchUserCount(BasePagination<User> page) {
		Map<String, Object> params = new HashMap<String,Object>();
		StringBuilder hql = new StringBuilder("select count(*)");
		searchUserBase(hql,params,page);
		return this.count(hql.toString(), params);
	}

	@Override
	public List<User> searchUser(BasePagination<User> page) {
		Map<String, Object> params = new HashMap<String,Object>();
		StringBuilder hql = new StringBuilder("select u");
		searchUserBase(hql,params,page);
		this.appendSort(hql, "u", page);
		return this.find(hql.toString(),params,page.getCurrentPage(),page.getLimit());	
	}
	
	private void searchUserBase(StringBuilder hql,Map<String, Object> params,BasePagination<User> page){
		hql.append(" from User u");
		String name=null;		
		if(null != page && null != page.getParams()){
			name = page.getParams().get("username");
		}
		if(StringUtils.isNotBlank(name)){
			hql.append(" where u.username like :username or u.name like :username");	
			params.put("username", "%"+name+"%");
		}
	}
	
//	private StringBuilder searchUserCondition(BasePagination<User> page,boolean isCount){
//		StringBuilder hql = new StringBuilder("select ");
//		hql.append(isCount?" count(*)":" u").append(" from User u");
//		return hql;
//	}

}
