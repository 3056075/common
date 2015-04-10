package com.zm.common.action.face;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zm.common.exception.ZmException;
import com.zm.common.face.BaseRequest;
import com.zm.common.face.BaseResponse;


public abstract class IProcessBase {
	public abstract BaseResponse useProcess(String data,HttpServletRequest request) throws ZmException;
	
	protected <T extends BaseRequest<? extends BaseResponse>> T convertData(String data,Class<T> clazz) throws ZmException {
		try {
			Gson gson = new Gson();
			T t = gson.fromJson(data, clazz);
			return t;
		} catch (JsonSyntaxException e) {
			throw new ZmException(BaseResponse.CODE_JSON_S);
		}
	}
}



