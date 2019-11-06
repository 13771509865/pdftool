package com.neo.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.neo.commons.cons.IResult;
import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.constants.ConstantAdmin;
import com.neo.commons.cons.constants.ConstantCookie;
import com.neo.commons.cons.constants.RedisConsts;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.helper.HttpHelper;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.UserBO;
import com.neo.model.po.PtsAuthPO;
import com.neo.service.accessTimes.AccessTimesService;
import com.neo.service.auth.IAuthService;
import com.neo.service.cache.CacheManager;
import com.neo.service.cache.CacheService;
import com.neo.service.cache.impl.RedisCacheManager;

/**
 * 添加游客和会员的转换权限
 * @author xujun
 * 2019-07-12
 */
@Component
public class ConvertInterceptor implements HandlerInterceptor {

	@Autowired
	private IAuthService iAuthService;

	@Autowired
	private RedisCacheManager<String> redisCacheManager;


	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	/**
	 *  转换成功就统计进转换次数
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView arg3)
			throws Exception {
		Object convertResult = request.getAttribute(SysConstant.CONVERT_RESULT);

		if (convertResult != null && convertResult instanceof Integer) {
			if (EnumResultCode.E_SUCCES.getValue() == convertResult) {
				Long userID =HttpUtils.getSessionUserID(request);
				String key = RedisConsts.IP_CONVERT_TIME_KEY;
				String value = HttpUtils.getIpAddr(request); 
				if(userID !=null) {//登录用户
					key = RedisConsts.ID_CONVERT_TIME_KEY;
					value = userID.toString(); 
				}
				redisCacheManager.pushZSet(key, value);
			}
		}
	}

	
	
	/**
	 * 限制游客和登陆者的转换次数和转换类型
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String ipAddr = HttpUtils.getIpAddr(request);
		Long userID = HttpUtils.getSessionUserID(request);
		String body = HttpHelper.getBodyString(request);
		ConvertParameterBO convertParameterBO = JsonUtils.json2obj(body, ConvertParameterBO.class);
		IResult<EnumResultCode> result = iAuthService.checkUserAuth(convertParameterBO, userID,ipAddr);

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
