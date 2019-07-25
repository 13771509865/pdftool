package com.neo.commons.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class CheckMobileUtils {
	
	private static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"  
	        +"|windows (phone|ce)|blackberry"  
	        +"|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"  
	        +"|laystation portable)|nokia|fennec|htc[-_]"  
	        +"|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";  
	private static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser"  
	        +"|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";  
	
	//移动设备正则匹配：手机端、平板
	private static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);  
	private static Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);  
	  
	
	
	
	/**
	 * 检查是不是移动端用户
	 * @param request
	 * @return
	 */
	public static boolean checkIsMobile(HttpServletRequest request){
		boolean isFromMobile=false;
		String userAgent = request.getHeader( "USER-AGENT" ).toLowerCase();  
		isFromMobile=CheckMobileUtils.check(userAgent);
		return isFromMobile;
	}

	
	
	/**
	 * 检测是否是移动设备访问
	 * @Title: check
	 * @param userAgent 浏览器标识
	 * @return true:移动设备接入，false:pc端接入
	 */
	private  static boolean check(String userAgent){  
	    if(null == userAgent){  
	        userAgent = "";  
	    }  
	    // 匹配  
	    Matcher matcherPhone = phonePat.matcher(userAgent);  
	    Matcher matcherTable = tablePat.matcher(userAgent);  
	    if(matcherPhone.find() || matcherTable.find()){  
	        return true;  
	    } else {  
	        return false;  
	    }  
	}

	
	
	
	
	

	
	
	
	

}
