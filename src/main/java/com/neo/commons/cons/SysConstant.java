package com.neo.commons.cons;

public interface SysConstant {
	
	public static final String CHARSET = "UTF-8";
	
	/*拦截器遇到他就放行*/
	String SUPER_MAN = "SumNear";
	/**
	 * 缓存中的用户信息KEY
	 * USER_INFO+userId
	 */
	String USER_INFO  = "USER_";
	/**
	 * 用于Cookie
	 */
	int OUT_OF_DATE = 0;

	String SESSION_USERID = "_USERID"; //存用户名

	String SESSION_USERNAME = "_USERNAME"; //存用户名

	String SESSION_USER = "_USERKEY";  //存用户

	String ONLINE_USERS = "_USERMAP";  //application存 在线用户

	String EMAIL_CODE = "_EMAILCODE";  //发给用户邮箱的验证码

	String CHECK_CODE = "_CHECKCODE";  //session验证码
	
	String CONVERT_RESULT = "_CONVERTRESULT";//转换结果
	
	String UPLOAD_RESULT = "_UPLOADRESULT";//上传结果
	
	/**
	 * 用于业务
	 */
	String FILENAME = "fcs";//统一文件名
	
	String CONVERTPARAMETERFILENAME = "convertParameter.txt";
	
	String DCCJAR = "dccJar";
	
	String PDF2WORDJAR = "pad2wordJar";
	
	//mq的线程池的大小
	Integer MQPOOLSIZE = 5;
	/**
	 * 设置cookie名字
	 */

	String COOKIE_TOKEN = "_tk";  //给 client 的cookie字段
	/**
	 * 设置cookie有效期
	 */
	int COOKIE_TIME_DAY = 1000 * 60 * 60 * 24;
	int COOKIE_TIME_MONTH = COOKIE_TIME_DAY*30;
	int COOKIE_TIME_MAX = Integer.MAX_VALUE;
	/**
	 * 用于临时Cookie的MaxAge
	 */
	int TEMP        = -1;
}
