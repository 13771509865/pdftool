package com.neo.service.uaa;

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

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.ConstantCookie;
import com.neo.commons.cons.constants.UaaConsts;
import com.neo.commons.cons.entity.HttpResultEntity;
import com.neo.commons.util.CookieUtils;
import com.neo.service.httpclient.HttpAPIService;
import com.yozosoft.auth.client.config.YozoCloudProperties;


@Service("uaaService")
public class UaaService {

	@Autowired
	private YozoCloudProperties yozoCloudProperties;

	@Autowired
	private HttpAPIService httpAPIService;

	private Map<String, Object> params;

	private Map<String, Object> headers =  new HashMap<>();



	/**
	 * 获取用户详细信息
	 * @param request
	 * @return
	 */
	public String getUserInfoUaa(HttpServletRequest request) {
		String userInfo;
		try {
			headers.put(UaaConsts.COOKIE, request.getHeader(UaaConsts.COOKIE));
			IResult<HttpResultEntity> result = httpAPIService.doGet(UaaConsts.USER_INFO_URL,params, headers);
			userInfo = result.getData().getBody();
			return userInfo;
		} catch (Exception e) {
			return null;
		}
	}


	/**
	 * 登出并且删除cookie
	 * @param request
	 * @param response
	 * @return
	 */
	public IResult<String> uaaLogout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.removeAttribute(ConstantCookie.SESSION_USER);
		httpAPIService.doGet(UaaConsts.LOGIN_OUT_URL,params);
		//清空cookie
		for (String domain : yozoCloudProperties.getUaaCookieDomains()) {
			for (String cookieName : UaaConsts.COOKIE_NAMES) {
				CookieUtils.deleteCookie(request, response, cookieName, domain);
			}
		}
		return DefaultResult.successResult();
	}


	@PostConstruct
	private Map<String,Object> buildUaaLogoutParams(){
		params = new HashMap<>();
		params.put("app",UaaConsts.PDF);
		return params;
	}



}
