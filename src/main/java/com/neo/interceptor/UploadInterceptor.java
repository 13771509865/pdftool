package com.neo.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

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

import com.neo.commons.cons.ConstantAdmin;
import com.neo.commons.cons.ConstantCookie;
import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.ResultCode;
import com.neo.commons.cons.SysConstant;
import com.neo.commons.util.GetIpAddrUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.config.SysConfig;
import com.neo.service.accessTimes.AccessTimesService;

/**
 * 
 * 对用户上传权限做拦截
 * 游客上传10M
 * 会员上传30M
 * @author xujun
 * 2019-07-11
 *
 */
@Component
public class UploadInterceptor implements HandlerInterceptor {

	@Autowired
	private SysConfig config;
	

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception arg3) throws Exception {
	}

	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView arg3)
			throws Exception {

	}

	
	//游客仅可上传10M,会员可上传30M
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		String userInfo = (String)session.getAttribute(ConstantCookie.SESSION_USER);
		
		//参数默认为游客，文件大小为5M
		long maxSize = config.getVUploadSize()*1024;
		ResultCode resultCode = ResultCode.E_VISITOR_UPLOAD_ERROR;
		
		//登录用户，30M
		if(StringUtils.isNotBlank(userInfo)) {
			maxSize = config.getMUploadSize()*1024;
			resultCode = ResultCode.E_USER_UPLOAD_ERROR;
		}
		
		IResult<String> result = checkFile(request,maxSize);
		if(!result.isSuccess()) {
			sendResult(response,resultCode);
			return false;
		}
		 return true;
	}
	
	//检查上传的文件是否超过maxSize
	private IResult<String> checkFile(HttpServletRequest request, long maxSize){
		if(request!=null && ServletFileUpload.isMultipartContent(request)) {
            ServletRequestContext ctx = new ServletRequestContext(request);
            long requestSize = ctx.contentLength();
            if (requestSize > maxSize) {
            	return DefaultResult.failResult();
            }
        }
		return DefaultResult.successResult();
	}
	
	//发送消息
	private void sendResult( HttpServletResponse response,ResultCode resultCode) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(JsonResultUtils.buildFailJsonResultByResultCode(resultCode));
		out.flush();
		out.close();
	}
	
	
	
	
	
	
	
	
}
