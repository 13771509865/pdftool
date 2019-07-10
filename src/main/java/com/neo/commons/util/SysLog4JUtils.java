package com.neo.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SysLog4JUtils {
	 private static final Logger logger = LoggerFactory.getLogger("N");

	public static void error(Throwable t){
		logger.error("", t);
	}
	public static void error(String message,Exception e) {
//		String sOut = e.toString()+"\r\n";
//		StackTraceElement[] trace = new Exception().getStackTrace();
//        for (StackTraceElement s : trace) {
//            sOut += "\tat " + s + "\r\n";
//        }
//		logger.error(sOut);
		logger.error(message, e);
	}
	public static void error(String message){
		logger.error(message);
	}
	public static void info(String message){
		logger.info("["+message +"]");
	}
	public static void warn(String message, Exception e) {
		logger.warn(message,e);
	}
	public static void warn(String value) {
		logger.warn(value);
		
	}

}
