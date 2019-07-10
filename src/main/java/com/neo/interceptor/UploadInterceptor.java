package com.neo.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.neo.commons.cons.ConstantAdmin;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.ResultCode;
import com.neo.commons.cons.SysConstant;
import com.neo.commons.util.GetIpAddrUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.config.SysConfig;
import com.neo.service.accessTimes.AccessTimesService;

/**
 * 拦截上传操作
 * 
 * @author zhouf
 * @create 2018-12-17 09:26
 */
@Component
public class UploadInterceptor implements HandlerInterceptor {
	@Autowired
	private AccessTimesService accessTimesService;

	@Autowired
	private SysConfig config;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception arg3) throws Exception {
	}

	// 上传成功,上传次数加1
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView arg3)
			throws Exception {
		Object uploadResult = request.getAttribute(SysConstant.UPLOAD_RESULT);
		if (uploadResult != null && uploadResult instanceof Integer) {
			Integer fileCount = (Integer) uploadResult;
			String ipAddr = GetIpAddrUtils.getIpAddr(request);
			for (int i = 0; i < fileCount; i++) {
				boolean addUploadTimes = accessTimesService.addUploadTimes(ipAddr).getData() > 0 ? true : false;
			}
		}
	}

	// 判断是否到达当日上传上限
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String ipAddr = GetIpAddrUtils.getIpAddr(request);
		Integer maxUploadTimes;
		if(ConstantAdmin.ADMIN_IP.equals(ipAddr)){ //公司IP不限制
			maxUploadTimes = Integer.MAX_VALUE;
		}else{
			maxUploadTimes = config.getUploadTimes();
		}
		IResult<Integer> uploadTimesResult = accessTimesService.getIpUploadTimes(ipAddr);
		if (!uploadTimesResult.isSuccess()) {
			sendResult(response,ResultCode.E_REDIS_FAIL);
			return false;
		} else {
			Integer ipUploadTimes = uploadTimesResult.getData();
			if (ipUploadTimes >= maxUploadTimes) { // 超过上传次数
				sendResult(response,ResultCode.E_UPLOAD_OVER_TIME);
				return false;
			} else {
				return true;
			}
		}
	}
	
	
	public void sendResult( HttpServletResponse response,ResultCode resultCode) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(JsonResultUtils.buildFailJsonResultByResultCode(resultCode));
		out.flush();
		out.close();
	}
	
	
	
	
	
	
	
	
}
