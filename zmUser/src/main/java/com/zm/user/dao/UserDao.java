package com.zm.user.dao;

import java.util.List;

import com.zm.common.dao.BaseDao;
import com.zm.common.pagination.BasePagination;
import com.zm.user.entity.User;

public interface UserDao extends BaseDao<User> {
	public User findByUsername(String username);
	public Long searchUserCount(BasePagination<User> page);
	public List<User> searchUser(BasePagination<User> page);
}
