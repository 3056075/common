package com.zm.user.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zm.common.exception.ZmException;
import com.zm.user.dao.OauthDao;
import com.zm.user.dao.UserDao;
import com.zm.user.dao.UserRoleDao;
import com.zm.user.entity.Oauth;
import com.zm.user.entity.Role;
import com.zm.user.entity.User;
import com.zm.user.entity.UserRole;
import com.zm.user.oauth.wx.TokenGetResponse;
import com.zm.user.oauth.wx.UserinfoGetResponse;
import com.zm.user.oauth.wx.WxUtils;
import com.zm.user.service.OauthService;
import com.zm.user.service.UserService;

@Service
public class OauthServiceImpl implements OauthService {
	@Value("${oauth.wx.appid}")
	private String wxAppid;
	@Value("${oauth.wx.secret}")
	private String wxSecret;
	@Autowired
	private OauthDao oauthDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private UserService userService;

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false)
	public User wxOauth(String code) throws ZmException {
		TokenGetResponse tokenGet = WxUtils.getAccessToken(wxAppid, wxSecret,
				code);
		User user = null;
		if (tokenGet != null
				&& (tokenGet.getErrcode() == null || tokenGet.getErrcode() == 0)) {
			// 正确返回
			UserinfoGetResponse userinfo = WxUtils.getUserinfoToken(
					tokenGet.getAccess_token(), tokenGet.getOpenid());
			if (userinfo != null
					&& (userinfo.getErrcode() == null || userinfo.getErrcode() == 0)) {
				// 得到用户信息
				Oauth oauth = oauthDao.getOne(null, Oauth.TYPE_WEIXIN,
						userinfo.getOpenid(), null, null, null);
				if (oauth == null) {
					// add
					String defaultUsername = getDefaultUsername(userinfo
							.getOpenid(),Oauth.TYPE_WEIXIN);
					String defaultPassword = getDefaultPassword();
					String md5Password = new Md5PasswordEncoder()
							.encodePassword(defaultPassword, defaultUsername);
					user = new User();
					user.setUsername(defaultUsername);
					user.setPassword(md5Password);
					user.setName(userinfo.getNickname());
					user.setNickName(userinfo.getNickname());
					user.setHeadImgurl(userinfo.getHeadimgurl());
					user.setSex(userinfo.getSex());
					user.setStatus(User.STATUS_NORMAL);
					userDao.save(user);
					UserRole userRole = new UserRole();
					userRole.setUser(user);
					Role commRole = new Role();
					commRole.setRoleId(Role.ROLEID_USER);
					userRole.setRole(commRole);
					userRoleDao.save(userRole);
					//
					oauth = new Oauth();
					oauth.setUser(user);
					oauth.setType(Oauth.TYPE_WEIXIN);
					oauth.setCode(code);
					oauth.setToken(tokenGet.getAccess_token());
					oauth.setExpires(tokenGet.getExpires_in());
					oauth.setRefreshToken(tokenGet.getRefresh_token());
					oauth.setUnionid(userinfo.getUnionid());
					oauth.setOpenid(userinfo.getOpenid());
					oauth.setTokenCreateTime(new Date());
					oauthDao.save(oauth);	
				} else {
					// refresh nickname and headurl
					user = oauth.getUser();
					user.setName(userinfo.getNickname());
					user.setNickName(userinfo.getNickname());
					user.setHeadImgurl(userinfo.getHeadimgurl());
					userDao.save(user);
					oauth.setCode(code);
					oauth.setToken(tokenGet.getAccess_token());
					oauth.setExpires(tokenGet.getExpires_in());
					oauth.setRefreshToken(tokenGet.getRefresh_token());
					oauth.setUnionid(userinfo.getUnionid());
					oauth.setOpenid(userinfo.getOpenid());
					oauth.setTokenCreateTime(new Date());
					oauthDao.save(oauth);	
				}
			}
		}
		if(user!=null){
			return user;
		}else{
			throw new ZmException("认证失败！");
		}
		
	}

	@Override
	public String getDefaultUsername(String oid,Short oauthType) {
		return "DEF_"+oauthType+"_" + oid;
	}

	@Override
	public String getDefaultPassword() {
		return "defaultPassword";
	}

}
