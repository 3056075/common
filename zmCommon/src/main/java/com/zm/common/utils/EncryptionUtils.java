package com.zm.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class EncryptionUtils {
	public static String MD5Base64Encode(String psw, String charset)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest messagedigest = MessageDigest.getInstance("MD5"); // 创建消息摘要
		messagedigest.update(psw.getBytes(charset)); // 用明文字符串计算消息摘要。
		byte[] abyte = messagedigest.digest(); // 读取消息摘要。
		String result = new String(Base64.getEncoder().encode(abyte));
		return result;
	}

	public static String md5Encode(String str) {
		return new Md5PasswordEncoder().encodePassword(str, null);
	}

}
