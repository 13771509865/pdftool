package com.neo.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.neo.commons.SysConfig;
import com.neo.commons.cons.ConstantAdmin;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.ResultCode;
import com.neo.commons.cons.SysConstant;
import com.neo.commons.util.GetIpAddrUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.service.accessTimes.AccessTimesService;

/**
 * 拦截转换操作
 * 
 * @author zhouf
 * @create 2018-12-17 10:05
 */
@Component
public class ConvertInterceptor implements HandlerInterceptor {
	@Autowired
	private AccessTimesService accessTimesService;

	@Autowired
	private SysConfig config;

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	// 转换成功就统计进转换次数
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView arg3)
			throws Exception {
		Object convertResult = request.getAttribute(SysConstant.CONVERT_RESULT);
		if (convertResult != null && convertResult instanceof Integer) {
			Integer result = (Integer) convertResult;
			if (ResultCode.E_SUCCES.getValue() == result) {
				String ipAddr = GetIpAddrUtils.getIpAddr(request);
				boolean addConvertTimes = accessTimesService.addConvertTimes(ipAddr).getData() > 0 ? true : false;
			}
		}
	}

	// 如果转换次数到上限就拦截
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Integer maxConvertTimes;
		String ipAddr = GetIpAddrUtils.getIpAddr(request);
		if(ConstantAdmin.ADMIN_IP.equals(ipAddr)){ //公司IP不限制
			maxConvertTimes = Integer.MAX_VALUE;
		}else{
			maxConvertTimes = config.getConvertTimes();
		}
		IResult<Integer> convertTimesResult = accessTimesService.getIpConvertTimes(ipAddr);
		if (!convertTimesResult.isSuccess()) {
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JsonResultUtils.buildFailJsonResultByResultCode(ResultCode.E_REDIS_FAIL));
			out.flush();
			out.close();
			return false;
		} else {
			Integer ipConvertTimes = convertTimesResult.getData();
			if (ipConvertTimes >= maxConvertTimes) { // 超过上传次数
				response.setContentType("text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				out.write(JsonResultUtils.buildFailJsonResultByResultCode(ResultCode.E_ACCESS_OVER_TIME));
				out.flush();
				out.close();
				return false;
			} else {
				return true;
			}
		}
	}

}
