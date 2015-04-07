package com.zm.common.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zm.common.action.BaseController;
import com.zm.common.action.CommonJson;
import com.zm.common.exception.ZmException;
import com.zm.common.service.UploadService;

@Controller
@RequestMapping("/")
public class UploadController extends BaseController {
	@Autowired
	private UploadService uploadService;

	@RequestMapping("upload")
	@ResponseBody
	public CommonJson upload(
			@RequestParam(value = "file", required = false) MultipartFile file) {		
		CommonJson result = new CommonJson();
		result.setCode("success");
		try {
			String  uri = uploadService.saveFile(file);
			result.setMessage(uri);
		} catch (ZmException e) {	
			result.setCode("failure");
			result.setMessage(e.getMessage());
		}
		return result;
	}

}
