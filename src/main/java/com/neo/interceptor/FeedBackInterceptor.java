package com.neo.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.neo.commons.cons.EnumEventType;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.helper.MemberShipHelper;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonResultUtils;

/**
 * 用户反馈拦截器
 * @author xujun
 * @description
 * @create 2019年11月14日
 */
@Component
public class FeedBackInterceptor implements HandlerInterceptor{
	
	@Autowired
	private MemberShipHelper memberShipHelper;
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Long userID = HttpUtils.getSessionUserID(request);
		
		//登录后才能反馈
		if(userID == null) {
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JsonResultUtils.buildFailJsonResultByResultCode(EnumResultCode.E_UNLOGIN_ERROR));
			out.flush();
			out.close();
			return false;
		}
		
		return true;
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

	
	
	
	
	
	
}
