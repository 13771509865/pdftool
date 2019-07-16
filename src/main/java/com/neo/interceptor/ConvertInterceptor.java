package com.neo.interceptor;

import java.io.PrintWriter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.neo.commons.cons.ConstantAdmin;
import com.neo.commons.cons.ConstantCookie;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.RedisConsts;
import com.neo.commons.cons.ResultCode;
import com.neo.commons.cons.SysConstant;
import com.neo.commons.util.GetIpAddrUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.commons.util.SysLog4JUtils;
import com.neo.config.SysConfig;
import com.neo.model.bo.UserBO;
import com.neo.service.accessTimes.AccessTimesService;
import com.neo.service.cache.CacheManager;
import com.neo.service.cache.CacheService;

/**
 * 添加游客和会员的转换权限
 * @author xujun
 * 2019-07-12
 *
 */
@Component
public class ConvertInterceptor implements HandlerInterceptor {


	@Autowired
	private CacheService<String> cacheService;

	@Autowired
	private SysConfig config;

	private CacheManager<String> cacheManager;


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
				String userID = getSessionUserID(request);
				String ipAddr = GetIpAddrUtils.getIpAddr(request);
				String key = RedisConsts.IpConvertTimesKey;
				String value = ipAddr; 
				if(StringUtils.isNotBlank(userID)) {//登录用户
					key = RedisConsts.IdConvertTimesKey;
					value = userID; 
				}
				boolean addConvertTimes = cacheManager.pushZSet(key, value);
				SysLog4JUtils.info("转换次数插入是否成功："+addConvertTimes);
			}
		}
	}

	// 如果转换次数到上限就拦截
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String ipAddr = GetIpAddrUtils.getIpAddr(request);
		String userID = getSessionUserID(request);

		//默认为游客参数值
		Integer maxConvertTimes = config.getVConvertTimes();//游客5个文件
		String value = ipAddr; 
		String key = RedisConsts.IpConvertTimesKey;
		ResultCode resultCode = ResultCode.E_VISITOR_CONVERT_NUM_ERROR;

		if(!ConstantAdmin.ADMIN_IP.equals(ipAddr)){//公司ip不拦截
			if(StringUtils.isNotBlank(userID)){//会员20个文件
				maxConvertTimes = config.getMConvertTimes();
				key = RedisConsts.IdConvertTimesKey;
				value = userID;
				resultCode = ResultCode.E_USER_CONVERT_NUM_ERROR;
			}

			int convertTimes = cacheManager.getScore(key,value).intValue();
			SysLog4JUtils.info("转换次数为："+convertTimes);
			if (convertTimes >= maxConvertTimes) { // 超过每日最大转换次数
				response.setContentType("text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				out.write(JsonResultUtils.buildFailJsonResultByResultCode(resultCode));
				out.flush();
				out.close();
				return false;
			} 
		}
		return true;
	}


	//获取session中的userID
	private String getSessionUserID(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userInfo = (String)session.getAttribute(ConstantCookie.SESSION_USER);
		if(StringUtils.isNotBlank(userInfo)) {
			UserBO userBO = JsonUtils.json2obj(userInfo, UserBO.class);
			return userBO.getUserId();
		}
		return null;
	}



	//redis初始化对象
	@PostConstruct
	private void initCache() {
		this.cacheManager = cacheService.getCacheManager();
	}




}
