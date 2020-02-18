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
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.CookieUtils;
import com.neo.service.httpclient.HttpAPIService;
import com.yozosoft.auth.client.config.YozoCloudProperties;
import com.yozosoft.auth.client.security.JwtAuthenticator;
import com.yozosoft.auth.client.security.OAuth2AccessToken;
import com.yozosoft.auth.client.security.OAuth2RequestTokenHelper;
import com.yozosoft.auth.client.security.UaaToken;
import com.yozosoft.auth.client.security.refresh.UaaTokenRefreshClient;


@Service("uaaService")
public class UaaService {

	@Autowired
	private YozoCloudProperties yozoCloudProperties;

	@Autowired
	private HttpAPIService httpAPIService;

	@Autowired
	private PtsProperty ptsProperty;

	@Autowired
	private OAuth2RequestTokenHelper oAuth2RequestTokenHelper;

	@Autowired
	private UaaTokenRefreshClient uaaTokenRefreshClient;

	@Autowired
	private JwtAuthenticator jwtAuthenticator;


	private Map<String, Object> params;

	private Map<String, Object> headers =  new HashMap<>();


	/**
	 * uaa验证用户是否登录
	 * @param request
	 * @return
	 */
	public IResult<OAuth2AccessToken> checkSecurity(HttpServletRequest request) { 
		OAuth2AccessToken oAuth2AccessToken = null;
		try {
			request = oAuth2RequestTokenHelper.detectTokenInHeaderOrParams(request);
			oAuth2AccessToken = oAuth2RequestTokenHelper.extractToken(request);
			oAuth2AccessToken = uaaTokenRefreshClient.refreshAccessToken(oAuth2AccessToken, oAuth2AccessToken.getRefreshToken());
			UaaToken token = jwtAuthenticator.authenticate(oAuth2AccessToken.getValue());
			if (token == null) {
				return DefaultResult.failResult("无用户信息请重新登陆！");
			}
		} catch (Exception e) {
			return DefaultResult.failResult("用户信息错误");
		}
		return DefaultResult.successResult(oAuth2AccessToken);
	}




	/**
	 * 获取用户详细信息
	 * @param request
	 * @return
	 */
	public String getUserInfoUaa(HttpServletRequest request) {
		String userInfo;
		try {
			System.out.println(request.getHeader(UaaConsts.COOKIE));
			headers.put(UaaConsts.COOKIE, request.getHeader(UaaConsts.COOKIE));
			IResult<HttpResultEntity> result = httpAPIService.doGet(ptsProperty.getUaa_userinfo_url(),params, headers);
			userInfo = result.getData().getBody();
			System.out.println("单点用户信息："+userInfo);
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
		httpAPIService.doGet(ptsProperty.getUaa_logout_url(),params);
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
