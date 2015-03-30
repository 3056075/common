package com.zm.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidUtils {
	public static Boolean isMobile(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			return Boolean.FALSE;
		}
		String regExp = "^[1][0-9]{10}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(mobile);
		return m.find();// boolean
	}
	
}
