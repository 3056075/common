package com.zm.common.face;

public abstract class BaseRequest<T extends BaseResponse> {
	public static final String VERSION = "1.0";
	public static final String TYPE_JSON = "json";
	public static final String SECRET = "meiui20150101";
	public static final String USERAGENT="mwmobile-android";
	 public abstract String faceCode();
}
