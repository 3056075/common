package com.zm.common.utils;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang.StringUtils {
	
	public static final String CANCEL_DOUBLE_FORMAT="###";
	public static String insertWbr(String str){
		if(null == str){
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<str.length();i++){
			sb.append(str.charAt(i)).append("<wbr />");
		}
		return sb.toString();
	}
	
	public static String getMidStr(String str, String startStr, String endStr) {
		if (str == null) {
			return null;
		}
		int a = str.indexOf(startStr);

		if (a != -1) {
			str = str.substring(a + startStr.length());
			int b = str.indexOf(endStr);
			if (b != -1) {
				str = str.substring(0, b);
				return str;
			}
		}
		return null;
	}

	/**
	 * 在数字前面添0，满足bits位数
	 * 
	 * @param num
	 * @param bits
	 * @return
	 */
	public static String numAddZeroBeforeString(Integer num, int bits) {
		if (num != null) {
			String numString = num.toString();
			int len = numString.length();
			if (len >= bits) {
				return num.toString();
			} else {
				int margin = bits - len;
				while (margin > 0) {
					numString = "0" + numString;
					margin--;
				}
			}
			return numString;
		}
		return null;
	}
	
	/**
	 * 按字节长度截取字符串，可以是中英文混排
	 * @param s 要截取的目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String multiSubStr(String s, int len) {
		if (s == null) {
			return "";
		}
		try {
			if (s.getBytes("Unicode").length <= len) {
				return s;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return multiSubstring2(s, len) + "...";
	}
	
	/**
	 * 按字节长度截取字符串
	 * @param s 要截取的目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String multiSubstring2(String s, int length) {
		String s1 = s;
		try{
		byte[] bytes = s.getBytes("Unicode");
		int n = 0; // 表示当前的字节数
		int i = 2; // 要截取的字节数，从第3个字节开始

		for (; i < bytes.length && n < length; i++) {
			// 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
			if (i % 2 == 1) {
				n++; // 在UCS2第二个字节时n加1
			} else {
				// 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
				if (bytes[i] != 0) {
					n++;
				}
			}
		}

		// 如果i为奇数时，处理成偶数
		if (i % 2 == 1) {
			// 该UCS2字符是汉字时，去掉这个截一半的汉字
			if (bytes[i - 1] != 0) {
				i = i - 1;
			}
			// 该UCS2字符是字母或数字，则保留该字符
			else {
				i = i + 1;
			}
		}
		
		s1 =  new String(bytes, 0, i, "Unicode");
		}catch(UnsupportedEncodingException u){
			u.printStackTrace();
		}
		return s1;
	}
	
	/**
	 * 按字节长度截取字符串，可以是中英文混排，同时过滤font标签
	 * @param s 要截取的目标字符串
	 * @param len 截取长度
	 * @return
	 */
	public static String filterSubStr(String s, int len) {
		if(s == null){
			return "";
		}
		if (s.length() <= len) {
			return s;
		} else{
			if (s.contains("<font color='red'>") && s.contains("</font>")) {
				int i=("<font color='red'></font>").length();
				String result = "";
				if(s.length() > (i+len)){
					result = s.substring(0, (i+len));
					return result + "...";
				}
				else{
					result = s;
					return result;
				}
			}
		}
		return s.substring(0, len) + "...";
	}
	
	/**
	 * 在纠错显示文本的时候，去除高亮显示时所添加的font标签
	 * @param s
	 * @return
	 */
	public static String removeFont(String s){
		if(s == null){
			return "";
		}
		String result = "";
		if (s.contains("<font color='red'>") && s.contains("</font>")) {
			String str = s.replace("<font color='red'>", "");
			result = str.replace("</font>", "");
		}else{
			return s;
		}
		return result;
	}
	
	/**
	 * 字符串是否在字符串数组中 忽略大小写
	 * 
	 * @param url
	 *            字符串
	 * @param allUrl
	 *            字符串数组
	 * @return
	 */
	public static boolean in(String url, String[] allUrl) {
	    if (StringUtils.isEmpty(url)) {
	        return false;
        }
	    if (allUrl==null||allUrl.length==0) {
	        return false;
        }
		for (int i = 0; i < allUrl.length; i++) {
			if (allUrl[i].equalsIgnoreCase(url)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 检查指定的字符串是否为空。
	 * <ul>
	 * <li>SysUtils.isEmpty(null) = true</li>
	 * <li>SysUtils.isEmpty("") = true</li>
	 * <li>SysUtils.isEmpty("   ") = true</li>
	 * <li>SysUtils.isEmpty("abc") = false</li>
	 * </ul>
	 * 
	 * @param value 待检查的字符串
	 * @return true/false
	 */
	public static boolean isEmpty(String value) {
		int strLen;
		if (value == null || (strLen = value.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(value.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查对象是否为数字型字符串。
	 */
	public static boolean isNumeric(Object obj) {
		if (obj == null) {
			return false;
		}
		String str = obj.toString();
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查指定的字符串列表是否不为空。
	 */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}

	/**
	 * 把通用字符编码的字符串转化为汉字编码。
	 */
	public static String unicodeToChinese(String unicode) {
		StringBuilder out = new StringBuilder();
		if (!isEmpty(unicode)) {
			for (int i = 0; i < unicode.length(); i++) {
				out.append(unicode.charAt(i));
			}
		}
		return out.toString();
	}
	public static boolean isSingleMail(String str){
		Pattern pattern = Pattern.compile("(0|1|2|3|4|5|6|7|8|9|E|D|F|G|V|W|e|d|f|g|v|w)[0-9]{9}");
		Matcher matcher = pattern.matcher(str.trim());
		if (matcher.matches()){
			return true;
		}
		return false;
	}
	/**
	 * 过滤不可见字符
	 */
	public static String stripNonValidXMLCharacters(String input) {
		if (input == null || ("".equals(input)))
			return "";
		StringBuilder out = new StringBuilder();
		char current;
		for (int i = 0; i < input.length(); i++) {
			current = input.charAt(i);
			if ((current == 0x9) || (current == 0xA) || (current == 0xD)
					|| ((current >= 0x20) && (current <= 0xD7FF))
					|| ((current >= 0xE000) && (current <= 0xFFFD))
					|| ((current >= 0x10000) && (current <= 0x10FFFF)))
				out.append(current);
		}
		return out.toString();
	}
	public static boolean isMailNo(String str){
	    if(StringUtils.isNotEmpty(str)){
	        if(str.contains("/") && str.length() > 1){
	            String [] tempArray = str.split("/");
	            for(String tempStr : tempArray){
	                if(!isSingleMail(tempStr)){
	                    return false;
	                }
	            }
	            return true;
	        }else if(str.indexOf(" ")>1){
	        	String[] tempArray = str.split(" ");
	        	for(String tempStr : tempArray){
	                if(!isSingleMail(tempStr)){
	                    return false;
	                }
	             }
	        	return true;
	        }else if(str.indexOf("\n")>1){
	        	String[] tempArray = str.split("\n");
	        	for(String tempStr : tempArray){
	                if(!isSingleMail(tempStr)){
	                    return false;
	                }
	             }
	        	return true;
	        }
	        else{
	            return isSingleMail(str);
	        }
	    }

	    return false;
	}
	
    /**
     * <p>Checks if a String is not empty ("") and not null.</p>
     *
     * <pre>
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = false
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is not empty and not null
     */
    public static boolean isNotEmptyTrim(String str) {
         if(!StringUtils.isEmpty(str)){
        	 if(!str.trim().equals("")){
        		 return true;
        	 }
         }
         return false;
    }
    
    
    /**
	 * 在数字前面添0，满足bits位数
	 * @param str
	 * @param n
	 * @return
	 */
	public static String addZero(String str, int n) {
		int len = str.length();
		if(len >= n) {
			return str;
		} 
		int margin = n - len;
		while(margin > 0) {
			str = "0" + str;
			margin--;
		}
		return str;
	}
	
	/**
	 * 科学计数法转换成普通计数 小数位将清空
	 * @param String str
	 * @return String
	 * */
	public static String cancelScience(String str) {
		Double d = Double.valueOf(str);
		return cancelScience(d);
	}
	
	/**
	 * 科学计数法转换成普通计数 小数位将清空
	 * @param Double d
	 * @return String
	 * */
	public static String cancelScience(Double d) {
		DecimalFormat df = new DecimalFormat(CANCEL_DOUBLE_FORMAT);
		return df.format(d);
	}

	/**
	 * 科学计数法转换成普通计数  指定格式 
	 * @param String str
	 * @return String
	 * */
	public static String cancelScience(String str ,String format) {
		Double d = Double.valueOf(str);
		return cancelScience(d,format);
	}
	
	/**
	 * 科学计数法转换成普通计数  指定格式
	 * @param Double d
	 * @return String
	 * */
	public static String cancelScience(Double d,String format) {
		DecimalFormat df = new DecimalFormat(format);
		return df.format(d);
	}
	
	/**
	 * 返回目标字符串的字节长度
	 * @param str
	 * @return
	 */
	public static int getLength(String str){
		try {
			return str.getBytes("Unicode").length;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 以字节截取中英文     
	 * @param s String字符串
	 * @param length  需要的长度
	 * @param appendStr 超过长度 补充字符串 如“...”
	 * */
    public static String byteSubString(String s, int length,String appendStr){
    	if(s==null || s.equals("")){
    		return "";
    	}
		String str = s.substring(0, s.length()>length?length:s.length());
		StringBuffer sb = new StringBuffer("");
		char[] cs = str.toCharArray();
		int count = 0;
		for(char c : cs){
			if(count>length){
				break;
			}
			if(c >= 0x0391 && c <= 0xFFE5){  //中文字符
					// || (c>=0x0000 && c<=0x00FF))   //英文字符
				count = count+2;
				if(count>length){
					break;
				}
				sb.append(c);
			}else{
				sb.append(c);
				count = count+1;
			}
		}
		if(count>=length){
			if(!sb.toString().equals(s)){
				sb.append(appendStr);
			}
		}
		return sb.toString();
	} 
	
}
