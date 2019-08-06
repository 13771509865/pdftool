package com.neo.commons.cons.constants;


/**
 * redis存储key常量
 * @author xujun
 *
 */
public class RedisConsts {
	
	public static final String FILE_INFO_KEY="fileInfo";//存储转换后filehash和文件对应关系的
	
	public static final String IP_CONVERT_TIME_KEY = "ipConvertTimes";//ip转换次数
	
	public static final String ID_CONVERT_TIME_KEY = "idConvertTimes";//用户id转换次数
	
	public static final String MQ_WAIT_CONVERT = "mqWaitConvert"; //待转换消息队列
	
	public static final String MQ_RESULT_CONVERT = "mqConvertResult"; //待转换消息队列
	
	public static final String UPLOAD_CONNT = "uploadCount";
	
	public static final String SUCCESS = "success";
	
	public static final String	FAIL = "fail";
	
	//redis二级缓存配置
    public static final String CACHE_DAY = "cacheDay";

    public static final String CACHE_QUARTER_DAY = "cacheQuarterDay";

    public static final String CACHE_HALF_DAY = "cacheHalfDay";

    public static final String DYNAMIC_DOMAIN_COUNT_CACHE = "dynamicDomainCountCache";
	
	
	
}
