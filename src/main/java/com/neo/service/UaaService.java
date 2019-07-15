package com.neo.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.ConstantCookie;
import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.UaaSupportApp;
import com.neo.commons.cons.entity.HttpResultEntity;
import com.neo.commons.util.CookieUtils;
import com.neo.service.httpclient.HttpAPIService;
import com.yozosoft.auth.client.config.YozoCloudProperties;


@Service("uaaService")
public class UaaService {
	
	
//	@Autowired
//	private HttpUaaService httpUaaService;
	
	@Autowired
    private YozoCloudProperties yozoCloudProperties;
	
	@Autowired
	private HttpAPIService httpAPIService;
	
	private Map<String, Object> params;
	
	private Map<String, Object> headers =  new HashMap<>();
	
	private static final String COOKIE = "Cookie";
	
	private static final String ACCESS_TOKEN = "access_token";

    private static final String REFRESH_TOKEN = "refresh_token";

    private static final String SESSION_TOKEN = "session_token";

    private static final List<String> COOKIE_NAMES = Arrays.asList(ACCESS_TOKEN, REFRESH_TOKEN, SESSION_TOKEN);
	
	
	/**
	 * 获取用户信息
	 * @param request
	 * @return
	 */
	public String getUserInfoUaa(HttpServletRequest request) {
		String userInfo;
		try {
			headers.put(COOKIE, request.getHeader(COOKIE));
			IResult<HttpResultEntity> result = httpAPIService.doGet(UaaSupportApp.userInfoUrl,params, headers);
			userInfo = result.getData().getBody();
			return userInfo;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public IResult<String> uaaLogout(HttpServletRequest request, HttpServletResponse response) {
		 HttpSession session = request.getSession();
		 session.removeAttribute(ConstantCookie.SESSION_USER);
	        //清空cookie
	        for (String domain : yozoCloudProperties.getUaaCookieDomains()) {
	            for (String cookieName : COOKIE_NAMES) {
	            	CookieUtils.deleteCookie(request, response, cookieName, domain);
	            }
	        }
	        httpAPIService.doGet(UaaSupportApp.loginOutUrl,params);
	        return DefaultResult.successResult();
	}
	
	
	
    @PostConstruct
    private Map<String,Object> buildUaaLogoutParams(){
        params = new HashMap<>();
        params.put("app",UaaSupportApp.pdfToword);
        return params;
    }
	
	

}
