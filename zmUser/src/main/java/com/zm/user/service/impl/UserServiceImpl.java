package com.zm.user.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mb.mbface.ientity.BaseResponse;
import com.zm.mbweb.common.CommonConstant;
import com.zm.mbweb.dao.UserDao;
import com.zm.mbweb.dao.UserRoleDao;
import com.zm.mbweb.entity.Role;
import com.zm.mbweb.entity.User;
import com.zm.mbweb.entity.UserRole;
import com.zm.mbweb.security.ZmUserDetails;
import com.zm.mbweb.service.UserService;
import com.zm.mbweb.utils.MBDateUtils;
import com.zm.zmcore.exception.ZmException;
import com.zm.zmcore.pagination.BasePagination;
import com.zm.zmcore.utils.StringUtils;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRoleDao userRoleDao;

	@Override
	public User findByUsername(String username) {
		User user = userDao.findByUsername(username);
		return user;
	}

	@Override
	public void updatePassword(Integer userId, String password) {
		User user = userDao.get(userId);
		String md5Password = new Md5PasswordEncoder().encodePassword(password,
				user.getUsername());
		user.setPassword(md5Password);
		userDao.saveOrUpdate(user);
	}

	@Override
	public User getCurrentUser() throws ZmException {
		ZmUserDetails userDetails = null;
		try {
			userDetails = (ZmUserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new ZmException(BaseResponse.CODE_LOGIN);
		}
		return userDetails;
	}

	@Override
	public void searchUser(BasePagination<User> page) {
		if (page.isNeedSetTotal()) {
			Long count = userDao.searchUserCount(page);
			page.setTotal(count.intValue());
		}
		List<User> result = userDao.searchUser(page);
		page.setResult(result);
	}

	@Override
	public User findByUserId(Integer userId) {
		return userDao.get(userId);
	}

	/**
	 * 修改当前用户密码
	 */
	@Override
	public void updatePassword(String oldpass, String newpass, String repnewpass)
			throws ZmException {
		User currentUser = getCurrentUser();
		String oldPasswordMd5 = new Md5PasswordEncoder().encodePassword(
				oldpass, currentUser.getUsername());
		if (!oldPasswordMd5.equals(currentUser.getPassword())) {
			throw new ZmException("原密码不正确");
		}
		if (StringUtils.isEmpty(newpass) || newpass.length() < 6) {
			throw new ZmException("新密码不能为空，并且要大于6位");
		}
		if (!StringUtils.equals(newpass, repnewpass)) {
			throw new ZmException("两次密码输入不一致");
		}
		updatePassword(currentUser.getUserId(), newpass);
	}

	@Override
	public void saveUser(String username, String password, String name) {
		String md5Password = new Md5PasswordEncoder().encodePassword(password,
				username);
		User user = new User();
		user.setUsername(username);
		user.setPassword(md5Password);
		user.setName(name);
		user.setStatus(User.STATUS_NORMAL);
		userDao.save(user);
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		Role commRole = new Role();
		commRole.setRoleId(CommonConstant.ROLE_USER);
		userRole.setRole(commRole);
		userRoleDao.save(userRole);
	}

	@Override
	public String sendValidcode() {
		return RandomStringUtils.randomNumeric(4);
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public Boolean getFreeStatus(Integer userId) {
		User currentUser = this.findByUserId(userId);
		Date lastFree=currentUser.getLastFree();
		if(lastFree==null){
			return true;
		}else{
			Date now = new Date();
			Date lastMon = MBDateUtils.getLastMon(now);
			Date LastFreeMon = MBDateUtils.getLastMon(lastFree);
			return !DateUtils.isSameDay(lastMon, LastFreeMon);
		}
	}
}
