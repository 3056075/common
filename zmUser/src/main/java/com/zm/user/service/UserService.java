package com.zm.user.service;

import com.zm.common.exception.ZmException;
import com.zm.common.pagination.BasePagination;
import com.zm.user.entity.User;

public interface UserService {
	public User findByUsername(String username);
	public User findByUserId(Integer userId);
	public void updatePassword(Integer userId,String password);
	public User getCurrentUser() throws ZmException;
	public void searchUser(BasePagination<User> page);
	
	public void updatePassword(String oldpass,String newpass,String repnewpass) throws ZmException;
	
	public void saveUser(String username,String password,String name);
	public void update(User user);
}
