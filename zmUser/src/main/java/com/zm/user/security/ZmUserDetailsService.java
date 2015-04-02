package com.zm.user.security;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zm.user.dao.UserDao;
import com.zm.user.entity.User;
@Service("zmUserDetailsService")
public class ZmUserDetailsService implements UserDetailsService{
	@Autowired
	private UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		if(null== user){
			throw new UsernameNotFoundException("找不到用户名："+username);
		}
		//
		ZmUserDetails userDetails =new ZmUserDetails(); 
		BeanUtils.copyProperties(user, userDetails);		
		return userDetails;
	}

}
