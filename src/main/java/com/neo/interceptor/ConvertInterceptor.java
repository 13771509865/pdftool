package com.neo.interceptor;

import com.neo.commons.cons.EnumEventType;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.helper.HttpHelper;
import com.neo.commons.helper.MemberShipHelper;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.service.auth.IAuthService;
import com.neo.service.convertRecord.IConvertRecordService;
import com.yozosoft.auth.client.security.UaaToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	private MemberShipHelper memberShipHelper;
	
	@Autowired
	private IConvertRecordService iConvertRecordService;


	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}


	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView arg3)
			throws Exception {
	}

	
	
	/**
	 * 限制游客和登陆者的转换次数和转换类型
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		UaaToken uaaToken = HttpUtils.getUaaToken(request);
		String body = HttpHelper.getBodyString(request);
		ConvertParameterBO convertParameterBO = JsonUtils.json2obj(body, ConvertParameterBO.class);

		//每日最高转换次数限制
		IResult<EnumResultCode> limitResult = iAuthService.checkConvertNumByDay(uaaToken.getUserId());
		if(!limitResult.isSuccess()) {
			HttpUtils.sendResponse(request, response, JsonResultUtils.buildFailJsonResultByResultCode(limitResult.getData()));
			return false;
		}

		//权限验证
		IResult<EnumResultCode> result = iAuthService.checkConvertNum(convertParameterBO, uaaToken);
		if(!result.isSuccess()) {
			HttpUtils.sendResponse(request, response, JsonResultUtils.buildFailJsonResultByResultCode(result.getData()));
			return false;
		}

		//发送积分事件
		if(uaaToken.getUserId()!=null){
			memberShipHelper.addMemberEvent(uaaToken.getUserId(), EnumEventType.CONVERT_EVENT);
		}
		return true;
	}






}
