package com.neo.interceptor;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.ConstantCookie;
import com.neo.commons.cons.constants.UaaConsts;
import com.neo.commons.util.CookieUtils;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.service.uaa.UaaService;
import com.yozosoft.auth.client.config.YozoCloudProperties;
import com.yozosoft.auth.client.security.JwtAuthenticator;
import com.yozosoft.auth.client.security.OAuth2AccessToken;
import com.yozosoft.auth.client.security.OAuth2RequestTokenHelper;
import com.yozosoft.auth.client.security.UaaToken;
import com.yozosoft.auth.client.security.refresh.UaaTokenRefreshClient;

@Component
public class UaaAuthInterceptor implements HandlerInterceptor{



	@Autowired
	private UaaService uaaService;	
	

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {

		IResult<OAuth2AccessToken> result = uaaService.checkSecurity(request);
		HttpSession session = request.getSession();
		if(!result.isSuccess()) {
			UaaToken token = (UaaToken)session.getAttribute(ConstantCookie.SESSION_USER);
			if(token != null) {//确保uaa登出后，同步登出
				 session.removeAttribute(ConstantCookie.SESSION_USER);
			}
			HttpUtils.sendResponse(request, response, JsonResultUtils.buildFailJsonResultByResultCode(EnumResultCode.E_UNLOGIN_ERROR));
			return false;
		}else {
			return true;
		}
	}


}	




