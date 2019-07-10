package com.neo.commons.cons;

public class RedisConsts {
	public static final String FileInfoKey = "fileInfo"; //存储转换后filehash和文件对应关系的
	
	public static final String TotalConvertTimesKey = "totalConvertTimes";
	public static final String IpKey = "ip";
	public static final String IpUploadTimesKey = "uploadTimes";
	public static final String IpconvertTimesKey = "convertTimes";
	
	public static final String MqWaitConvert = "mqWaitConvert"; //待转换消息队列
	public static final String MqConvertResult = "mqConvertResult"; //转换完成队列
}
