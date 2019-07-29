package com.neo.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SysLogUtils {
	 private static final Logger logger = LoggerFactory.getLogger("N");
	 private static final Logger elogger = LoggerFactory.getLogger("R");

	 
	public static void error(Throwable t){
		elogger.error("", t);
	}
	public static void error(String message,Exception e) {
		elogger.error(message, e);
	}
	public static void error(String message){
		elogger.error(message);
	}
	
	
    public static void info(String message) {
        logger.info("[{}]", message);
    }
	public static void warn(String message, Exception e) {
		logger.warn(message,e);
	}
	public static void warn(String value) {
		logger.warn(value);
		
	}

}
