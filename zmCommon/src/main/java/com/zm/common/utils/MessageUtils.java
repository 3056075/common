package com.zm.common.utils;

import java.util.Locale;

public class MessageUtils {
	public static String getMessage(String key,String... errorValues){
		
		String message = SpringContextUtils.getContext().getMessage(key, errorValues, Locale.SIMPLIFIED_CHINESE);
		return message;
	}
}
