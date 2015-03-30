package com.zm.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
/**
 * 压缩工具类
 * @author michael
 *
 */
public class ZipUtils {

	/**
	 * 压缩byte数组
	 * @param bytes
	 * @return
	 * @throws IOException
	 */
	public static byte[] zipBytes(byte[] bytes) throws IOException {
		if (bytes == null || bytes.length == 0) {
			return bytes;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(bytes);
		gzip.close();
		return out.toByteArray();
	}
	

	/**
	 * 解压缩byte数据
	 * @param bytes
	 * @return
	 * @throws IOException
	 */
	public static byte[] unzipBytes(byte[] bytes) throws IOException {
		if (bytes == null || bytes.length == 0) {
			return bytes;
		}		
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		return unzipBytes(is);
	}
	
	/**
	 * 解压缩数据流
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static byte[] unzipBytes(InputStream is) throws IOException {	
		ByteArrayOutputStream out = new ByteArrayOutputStream();		
		GZIPInputStream gunzip = new GZIPInputStream(is);
		byte[] buffer = new byte[1024];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		return out.toByteArray();
	}
	
	
}
