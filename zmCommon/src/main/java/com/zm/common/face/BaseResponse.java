package com.zm.common.face;

public class BaseResponse {	
	public static String CODE_UNKNOW = "F_100";
	public static String CODE_NETWORK_ERROR = "F_102";
	public static String CODE_50x = "F_103";
	public static String CODE_JSON_S = "F_104";
	public static String CODE_JSON_C = "F_105";
	public static String CODE_VERION = "F_106";
	public static String CODE_MD5 = "F_107";
	public static String CODE_PARAMS = "F_108";
	public static String CODE_FACEERROR = "F_109";
	public static String CODE_REQTIMEERROR = "F_110";
	//
	public static String CODE_40x = "F_201";
	public static String CODE_LOGIN = "F_300";
	
	
	private Boolean success = Boolean.TRUE;
	private String code;
	private String message;
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
