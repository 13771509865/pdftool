package com.neo.commons.cons.constants;

import java.util.Arrays;
import java.util.List;

public class UaaConsts {

	public static final String PDF = "pdf";

	public static final String USER_INFO_URL = "http://auth.yozocloud.cn/api/account/userinfo";

	public static final String LOGIN_OUT_URL = "http://auth.yozocloud.cn/api/account/logout";

//	public static final String userInfoUrl = "http://auth.yozodocs.com/api/account/userinfo";

//	public static final String loginOutUrl = "http://auth.yozodocs.com/api/account/logout";

	public static final String COOKIE = "Cookie";

	public static final String ACCESS_TOKEN = "access_token";

	public static final String REFRESH_TOKEN = "refresh_token";

	public static final String SESSION_TOKEN = "session_token";

	public static final List<String> COOKIE_NAMES = Arrays.asList(ACCESS_TOKEN, REFRESH_TOKEN, SESSION_TOKEN);



}
