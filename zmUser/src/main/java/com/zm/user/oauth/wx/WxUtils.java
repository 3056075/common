package com.zm.user.oauth.wx;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zm.common.exception.ZmException;
import com.zm.common.utils.WebUtils;

public class WxUtils {
	protected static Logger logger = LoggerFactory.getLogger(WxUtils.class);

	public static TokenGetResponse getAccessToken(String appid, String secret,
			String code) throws ZmException {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appid + "&secret=" + secret + "&code=" + code
				+ "&grant_type=authorization_code";
		return execute(url, TokenGetResponse.class);
//		TokenGetResponse response = new TokenGetResponse();
//		response.setAccess_token("at" + code);
//		response.setExpires_in(10);
//		response.setOpenid("oid" + code);
//		response.setRefresh_token("atr" + code);
//		return response;
	}

	public static TokenGetResponse refreshAccessToken(String appid, String refreshToken)
			throws ZmException {
		String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="
				+ appid
				+ "&grant_type=refresh_token&refresh_token="
				+ refreshToken;
		return execute(url, TokenGetResponse.class);
	}

	public static TokenCheckResponse checkAccessToken(String accessToken, String openid)
			throws ZmException {
		String url = "https://api.weixin.qq.com/sns/auth?access_token="
				+ accessToken + "&openid=" + openid;
		return execute(url, TokenCheckResponse.class);
	}

	public static UserinfoGetResponse getUserinfoToken(String accessToken,
			String openid) throws ZmException {
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token="
				+ accessToken + "&openid=" + openid;
		return execute(url, UserinfoGetResponse.class);
//		UserinfoGetResponse response = new UserinfoGetResponse();
//		response.setHeadimgurl("http://headimg"+openid);
//		response.setNickname("nickname"+openid);
//		response.setOpenid(openid);
//		response.setSex((short)1);
//		response.setUnionid("uid"+openid);
//		return response;
	}
	
	public static <T> T execute(String url, Class<T> clazz) throws ZmException {
		long now = new Date().getTime();
		logger.info("wx " + now + " 请求url：" + url);
		String result = null;
		try {
			result = WebUtils.getUrlAsStr(url);
		} catch (Exception e) {
			logger.error("wx " + now + " 请求错误:" + e.getMessage(), e);
			throw new ZmException("访问微信接口！");
		}
		logger.info("wx " + now + " 请求结果：" + result);

		try {
			return new Gson().fromJson(result, clazz);
		} catch (JsonSyntaxException e) {
			logger.error("wx " + now + " json结果转换对象失败：" + e.getMessage(), e);
			throw new ZmException("json结果转换对象失败！");
		}
	}
}
