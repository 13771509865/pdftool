package com.neo.interceptor;

import java.io.IOException;
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
import com.neo.service.accessTimes.AccessTimesService;
import com.neo.service.cache.CacheManager;
import com.neo.service.cache.CacheService;

/**
 * 添加游客和会员的转换权限
 * @author xujun
 * 2019-07-12
 */
@Component
public class ConvertInterceptor implements HandlerInterceptor {


	@Autowired
	private CacheService<String> cacheService;

	@Autowired
	private ConfigProperty config;

	private CacheManager<String> cacheManager;


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
				cacheManager.pushZSet(key, value);
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
		//检查转换次数
		IResult<EnumResultCode> checkConvertTimes = checkConvertTimes(ipAddr,userID,request,response);
		if(!checkConvertTimes.isSuccess()) {
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JsonResultUtils.buildFailJsonResultByResultCode(checkConvertTimes.getData()));
			out.flush();
			out.close();
			return false;
		}
		IResult<EnumResultCode> checkConvertType = checkConvertType(userID,request,response);
		if(!checkConvertType.isSuccess()) {
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JsonResultUtils.buildFailJsonResultByResultCode(checkConvertType.getData()));
			out.flush();
			out.close();
			return false;
		}
		return true;
	}


	/**
	 * 检查用户的转换次数
	 * @param ipAddr
	 * @param userID
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public IResult<EnumResultCode> checkConvertTimes(String ipAddr,Long userID,HttpServletRequest request, HttpServletResponse response) throws IOException {
		//默认为游客参数值
		Integer maxConvertTimes = config.getVConvertTimes();//游客5个文件
		String value = ipAddr; 
		String key = RedisConsts.IP_CONVERT_TIME_KEY;
		EnumResultCode resultCode = EnumResultCode.E_VISITOR_CONVERT_NUM_ERROR;

		if(userID != null){
			maxConvertTimes = config.getMConvertTimes();//会员20个文件
			key = RedisConsts.ID_CONVERT_TIME_KEY;
			value = userID.toString();
			resultCode = EnumResultCode.E_USER_CONVERT_NUM_ERROR;
		}
		int convertTimes = cacheManager.getScore(key,value).intValue();
		if (convertTimes >= maxConvertTimes) { // 超过每日最大转换次数
			return DefaultResult.failResult(resultCode);
		} 
		return DefaultResult.successResult();
	}



	/**
	 * 检查转换的类型
	 * 游客：文档、图片转pdf和pdf2word
	 * 登录用户：不限制
	 * @param ipAddr
	 * @param userID
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public IResult<EnumResultCode> checkConvertType(Long userID,HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		if(userID == null) {//没有登录
			String body = HttpHelper.getBodyString(request);
			ConvertParameterBO convertParameterBO = JsonUtils.json2obj(body, ConvertParameterBO.class);
			Integer convertType = convertParameterBO.getConvertType();
			if(convertType !=36 && convertType!= 32 && convertType!=3) {
				return DefaultResult.failResult(EnumResultCode.E_PERMISSION);
			}
		}
		return DefaultResult.successResult();
	}


	//redis初始化对象
	@PostConstruct
	private void initCache() {
		this.cacheManager = cacheService.getCacheManager();
	}




}
