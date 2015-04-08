package com.zm.common.action.face;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.zm.common.exception.ZmException;
import com.zm.common.face.BaseRequest;
import com.zm.common.face.BaseResponse;
import com.zm.common.utils.EncryptionUtils;
import com.zm.common.utils.SpringContextUtils;
import com.zm.common.utils.StringUtils;
@Controller
@RequestMapping("/face")
public class FaceController {
	protected static Logger logger = LoggerFactory
			.getLogger(FaceController.class);
	
	@Autowired
	private FaceDebugController controller;
	
	@RequestMapping("rest")
	public @ResponseBody
	BaseResponse rest(String data, String code, String version, String type,String reqtime,
			String md5,HttpServletRequest request) {
		BaseResponse result = process(data, code, version, type,reqtime, md5, request);
		logger.info("---data is returning <<<<<<<<------");
		String resStr = new Gson().toJson(result);
		logger.info(resStr);
		controller.put(" return data:"+ resStr);
		return result;
	}

	/**
	 * 检查基本接口
	 */
	private BaseResponse process(String data, String code, String version,
			String type, String reqtime,String md5, HttpServletRequest request) {
		logger.info("---data is comming >>>>>>>>------");
		String commingStr = "face code:" + code + " data:" + data + " version:"
				+ version + " type:" + type + " reqtime:"+reqtime+" md5:" + md5;
		logger.info(commingStr);
		controller.put(" comming data:"+ commingStr);
		
		
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setSuccess(false);
		try {
			
			// check paramters
			if (!BaseRequest.VERSION.equalsIgnoreCase(version)
					|| !BaseRequest.TYPE_JSON.equalsIgnoreCase(type)|| reqtime==null
					|| null == md5 || "".equals(md5)) {
				throw new ZmException(BaseResponse.CODE_PARAMS);				
			}
			// check Md5 data+code+version+type+secretkey
			String total = data + code + version + type +reqtime+ BaseRequest.SECRET;
			String md5check = EncryptionUtils.md5Encode(total);
			if (!StringUtils.equalsIgnoreCase(md5, md5check)) {				
				throw new ZmException(BaseResponse.CODE_MD5);				
			}
			// check code is right
			IProcessBase process=null;
			try {
				process = SpringContextUtils.getContext().getBean(code,IProcessBase.class);
			} catch (Exception e) {
			}
			if(null == process ){
				throw new ZmException(BaseResponse.CODE_FACEERROR);					
			}
			//
			return process.process(data,request);
		}catch (ZmException e) {
			baseResponse.setCode(e.getOrgMessage());
			baseResponse.setMessage(e.getMessage());
			return baseResponse;
		}
		catch (Exception e) {
			logger.error("process face error!",e);
			baseResponse.setCode(BaseResponse.CODE_UNKNOW);
			baseResponse.setMessage("未知错误");
			return baseResponse;
		}

	}

}
