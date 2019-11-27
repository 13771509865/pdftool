package com.neo.commons.cons.constants;

/**
 * @author sumnear
 */
public final class ConstantCookie {

    /**
     * 用于临时Cookie的MaxAge
     */
    public static final int TEMP = -1;

    /**
     * 用于删除Cookie
     */
    public static final int OUT_OF_DATE = 0;

    public static final String SESSION_USERID = "_USERID"; //存用户名

    public static final String SESSION_USERNAME = "_USERNAME"; //存用户名

    public static final String SESSION_USER = "_USERKEY";  //存用户

    public static final String ONLINE_USERS = "_USERMAP";  //application存 在线用户

    public static final String EMAIL_CODE = "_EMAILCODE";  //发给用户邮箱的验证码

    public static final String CHECK_CODE = "_CHECKCODE";  //session验证码
    
    public static final String ACCESS_TOKEN = "access_token";
    
    public static final String REFRESH_TOKEN = "refresh_token";
    
    
    /**
     * 设置cookie名字
     */

    public final static String COOKIE_TOKEN = "_tk";  //给 client 的cookie字段
    /**
     * 设置cookie有效期
     */
    public final static int COOKIE_TIME_DAY = 1000 * 60 * 60 * 24;
    public final static int COOKIE_TIME_MONTH = COOKIE_TIME_DAY * 30;
    public final static int COOKIE_TIME_MAX = Integer.MAX_VALUE;


}
