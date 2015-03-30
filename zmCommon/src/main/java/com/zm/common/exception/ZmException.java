package com.zm.common.exception;

import java.util.Locale;

import org.springframework.context.NoSuchMessageException;

import com.zm.common.utils.SpringContextUtils;
import com.zm.common.utils.StringUtils;



public class ZmException extends Exception {
	private final static long serialVersionUID = 6476473452739657628L;

    public static final String INFO_CODE_PRE      = "I_";                 // 页面显示
    public static final String EXCEPTION_CODE_PRE = "F_";                 // 程序中使用

	private String[] errorValues;
	private Object data;
	
	public ZmException() {
		super();
	}

	public ZmException(String messageOrCode) {
		super(messageOrCode);
	}

	public ZmException(String messageOrCode, String... errorValues) {
		super(messageOrCode);
		this.errorValues = errorValues;
	}
	
	public ZmException(String messageOrCode, Throwable cause) {
		super(messageOrCode, cause);
	}

	public ZmException(Throwable cause) {
		super(cause);
	}

	public ZmException(String messageOrCode, 
			Throwable cause,String... errorValues) {
		super(messageOrCode, cause);
		this.errorValues=errorValues;
	}
	
	public ZmException(String messageOrCode, String errorValue,
			Throwable cause) {
		super(messageOrCode, cause);
		this.errorValues=(null == errorValue ? null : new String[] { errorValue });
	}
	@Override
	public String getMessage() {
		return formatMessage(super.getMessage(),this.errorValues);
	}
	@Override
	public String getLocalizedMessage() {
		return getMessage();
	}
	private String formatMessage(String messageOrCode,String[] errorValues){
		if(StringUtils.indexOf(messageOrCode, EXCEPTION_CODE_PRE)==0){
			String result = null;
			try {
				result = SpringContextUtils.getContext().getMessage(messageOrCode, errorValues, Locale.SIMPLIFIED_CHINESE);
			} catch (NoSuchMessageException e) {
			}				
			if(StringUtils.isNotEmpty(result)){
				return result;
			}
		}
		return messageOrCode;
	}

	public Object getData() {
		return data;
	}

	public ZmException setData(Object data) {
		this.data = data;
		return this;
	}

	public String getOrgMessage() {
		return super.getMessage();
	}
}
