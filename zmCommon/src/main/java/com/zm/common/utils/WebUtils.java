package com.zm.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class WebUtils {
	public static final String METHOD_GET = "1";
	public static final String METHOD_POST = "2";

	public static HttpClient cloneHC(HttpClient hc) {
		HttpClient newHc = new HttpClient(hc.getParams());
		newHc.setHostConfiguration(hc.getHostConfiguration());
		newHc.getState().addCookies(hc.getState().getCookies());
		return newHc;
	}

	public static String getUrlAsStr(String urlStr) throws HttpException,
			IOException {
		return getUrlAsStr(urlStr, null, null);
	}

	public static String getUrlAsStr(String urlStr, String charset)
			throws HttpException, IOException {
		return getUrlAsStr(urlStr, charset, null);
	}

	public static String getUrlAsStr(String urlStr, String charset,
			HttpClient hc) throws HttpException, IOException {
		return getUrl(urlStr, null, charset, METHOD_GET, hc);
	}

	public static String getUrlAsStrPost(String urlStr,
			Map<String, String> params) throws HttpException, IOException {
		return getUrlAsStrPost(urlStr, null, null, null);
	}

	public static String getUrlAsStrPost(String urlStr,
			Map<String, String> params, String charset) throws HttpException,
			IOException {
		return getUrlAsStrPost(urlStr, params, charset, null);
	}

	public static String getUrlAsStrPost(String urlStr,
			Map<String, String> params, String charset, HttpClient hc)
			throws HttpException, IOException {
		return getUrl(urlStr, params, charset, METHOD_POST, hc);

	}

	private static String getUrl(String urlStr, Map<String, String> params,
			String charset, String type, HttpClient hc) throws HttpException,
			IOException {
		if (!urlStr.toLowerCase().startsWith("http://")
				&& !urlStr.toLowerCase().startsWith("https://") && null != hc) {
			urlStr = hc.getHostConfiguration().getHostURL() + urlStr;
		}
		URL url = new URL(urlStr);
		if (null == hc) {
			hc = getNewClient(url);
		}
		if (null != charset) {
			hc.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
					charset);
		}

		HttpMethodBase method = null;
		if (METHOD_GET.equals(type)) {
			method = new GetMethod(url.getPath());
			method.setQueryString(url.getQuery());
		}
		if (METHOD_POST.equals(type)) {
			PostMethod method1 = new PostMethod(url.getPath());
			method1.setQueryString(url.getQuery());
			if (null != params && !params.isEmpty()) {
				Iterator<String> keyit = params.keySet().iterator();
				while (keyit.hasNext()) {
					String key = keyit.next();
					String value = params.get(key);
					method1.addParameter(key, value);
				}
			}
			method = method1;
		}
		addHead(method);
		int code = hc.executeMethod(method);
		if ((code == HttpStatus.SC_MOVED_TEMPORARILY)
				|| (code == HttpStatus.SC_MOVED_PERMANENTLY)
				|| (code == HttpStatus.SC_SEE_OTHER)
				|| (code == HttpStatus.SC_TEMPORARY_REDIRECT)) {
			Header header = method.getResponseHeader("location");
			if (header != null) {
				String newuri = header.getValue();
				return getUrl(newuri, null, charset, METHOD_GET, hc);
			}
		}

		String encode = getEncoding(method);
		byte[] bytes = null;
		if ("gzip".equals(encode)) {
			bytes = ZipUtils.unzipBytes(method.getResponseBodyAsStream());
		} else {
			bytes = method.getResponseBody();
		}
		return bytes == null ? null : new String(bytes,
				method.getResponseCharSet());
	}

	// --------------------------------//
	private static String getEncoding(HttpMethod get) {
		if (null != get) {
			Header header = get.getResponseHeader("Content-Encoding");
			if (null != header) {
				return header.getValue();
			}
		}
		return null;
	}

	public static HttpClient getNewClient(URL url) {
		HttpClient hc = new HttpClient();
		hc.getHostConfiguration().setHost(url.getHost(), url.getPort(),
				url.getProtocol());
		hc.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		hc.getHttpConnectionManager().getParams().setSoTimeout(10000);
		hc.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(0, false));
		hc.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		return hc;
	}

	public static void addHead(HttpMethod method) {
		method.addRequestHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0");
		method.addRequestHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		method.addRequestHeader("Accept-Encoding", "gzip");
		method.addRequestHeader("Accept-Language",
				"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		// method.addRequestHeader("x-forwarded-for", StrUtils.getRandomIp());
		// method.addRequestHeader("Referer",
		// "https://00000/app/user/user_gen.php");
		method.addRequestHeader("Pragma", "no-cache");
		method.addRequestHeader("Cache-Contro", "no-cache");
	}

	// --------------------------------//
	public static InputStream getUrlAsInputStream(String urlStr)
			throws HttpException, IOException {
		return getUrlAsInputStream(urlStr, null, null);
	}

	public static InputStream getUrlAsInputStream(String urlStr, String charset)
			throws HttpException, IOException {
		return getUrlAsInputStream(urlStr, charset, null);
	}

	public static InputStream getUrlAsInputStream(String urlStr,
			String charset, HttpClient hc) throws HttpException, IOException {
		HttpMethodBase get = getUrlAsGetMethod(urlStr, charset, hc);
		String encode = getEncoding(get);
		if ("gzip".equals(encode)) {
			GZIPInputStream gunzip = new GZIPInputStream(
					get.getResponseBodyAsStream());
			return gunzip;
		}
		return get.getResponseBodyAsStream();
	}

	private static HttpMethodBase getUrlAsGetMethod(String urlStr,
			String charset, HttpClient hc) throws HttpException, IOException {
		if (!urlStr.toLowerCase().startsWith("http://")
				&& !urlStr.toLowerCase().startsWith("https://") && null != hc) {
			urlStr = hc.getHostConfiguration().getHostURL() + urlStr;
		}
		URL url = new URL(urlStr);
		if (null == hc) {
			hc = getNewClient(url);
		}
		if (null != charset) {
			hc.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
					charset);
		}
		GetMethod get = new GetMethod(url.getPath());
		get.setQueryString(url.getQuery());
		addHead(get);
		int code = hc.executeMethod(get);
		return get;
	}
}
