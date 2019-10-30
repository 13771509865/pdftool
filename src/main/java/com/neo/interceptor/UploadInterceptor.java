package com.neo.interceptor;

import java.io.PrintWriter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.ConstantCookie;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.service.auth.IAuthService;

/**
 * 
 * 对用户上传权限做拦截
 * @author xujun
 * 2019-07-11
 *
 */
@Component
public class UploadInterceptor implements HandlerInterceptor {


	@Autowired
	private IAuthService iAuthService;
	

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception arg3) throws Exception {
	}


	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView arg3)
			throws Exception {
	}


	/**
	 * 检查用户的上传文件大小权限
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		Long userID = HttpUtils.getSessionUserID(request);
		ServletRequestContext ctx = new ServletRequestContext(request);
		Long uploadSize = ctx.contentLength();
		
		IResult<EnumResultCode> result = iAuthService.checkUploadSize(userID, uploadSize);
		if(!result.isSuccess()) {
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JsonResultUtils.buildFailJsonResultByResultCode(result.getData()));
			out.flush();
			out.close();
			return false;
		}
		return true;
	}



}
