package com.zm.common.service;

import org.springframework.web.multipart.MultipartFile;

import com.zm.common.exception.ZmException;

public interface UploadService {
	public String saveFile( MultipartFile file) throws ZmException;
	public String saveFileBase64(String fileBase64) throws ZmException;
}
