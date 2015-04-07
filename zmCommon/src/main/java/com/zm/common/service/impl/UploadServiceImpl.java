package com.zm.common.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zm.common.constant.StringConstant;
import com.zm.common.exception.ZmException;
import com.zm.common.service.UploadService;

@Service
public class UploadServiceImpl implements UploadService {
	protected static Logger logger = LoggerFactory
			.getLogger(UploadServiceImpl.class);
	@Value("${uploadbasePath}")
	private String uploadbasePath;
	private static final String UPLOAD_PATH = "media";

	@Override
	public String saveFile(MultipartFile file) {
		Date now = new Date();
		String path = uploadbasePath + StringConstant.SLASH + UPLOAD_PATH;
		SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
		String datepath = sdf.format(now);
		path = path + StringConstant.SLASH + datepath;

		// String fileName = file.getOriginalFilename();
		String fileName = now.getTime() + ".jpg";
		File targetFile = new File(path, fileName);
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {

		}
		return UPLOAD_PATH + StringConstant.SLASH + datepath
				+ StringConstant.SLASH + fileName;
	}

	@Override
	public String saveFileBase64(String fileBase64) throws ZmException {
		byte[] decodedBytes = null;
		try {
			decodedBytes = Base64.decode(fileBase64.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("base64图片转换失败",e);
			throw new ZmException("base64图片转换失败");
		}
		return saveFile(new BASE64DecodedMultipartFile(decodedBytes));
		
	}

}
class BASE64DecodedMultipartFile implements MultipartFile{
	
	private final byte[] imgContent;
	public BASE64DecodedMultipartFile(byte[] imgContent)
    {
       this.imgContent = imgContent;
        }
	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getOriginalFilename() {
		return null;
	}

	@Override
	public String getContentType() {
		return null;
	}

	@Override
	public boolean isEmpty() {		
		 return imgContent != null && imgContent.length > 0;
	}

	@Override
	public long getSize() {
		return  imgContent.length;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return imgContent;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		 return new ByteArrayInputStream(imgContent);
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		 FileOutputStream os = new FileOutputStream(dest);
		os.write(imgContent);
		os.close();
	}
	
}