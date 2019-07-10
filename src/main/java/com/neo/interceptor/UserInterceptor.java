package com.neo.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.neo.commons.cons.ResultCode;
import com.neo.commons.util.JsonResultUtils;
import com.neo.model.bo.FileInfoBO;

/**
 * 验证所有用户相关操作有没有权限
 * @author zhouf
 * @create 2018-12-20 10:05
 *
 */
@Component
public class UserInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean checkSecurity = checkSecurity(request);
		if(!checkSecurity){
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JsonResultUtils.buildFailJsonResultByResultCode(ResultCode.E_USEROPERATION_ILLEGAL));
			out.flush();
			out.close();
			return false;
		}
		return true;
	}

	private boolean checkSecurity(HttpServletRequest request) {
		String fileHash = request.getParameter("fileHash");
		if (StringUtils.isEmpty(fileHash)) {
			return false;
		}
		HttpSession httpSession = request.getSession();
		Object obj = httpSession.getAttribute(fileHash);
		if (obj == null || !(obj instanceof FileInfoBO)) { // 非法
			return false;
		} 
		return true;
	}
}
