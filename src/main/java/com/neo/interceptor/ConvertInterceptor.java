package com.neo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.neo.commons.cons.EnumEventType;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.helper.HttpHelper;
import com.neo.commons.helper.MemberShipHelper;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.po.PtsConvertRecordPO;
import com.neo.service.auth.IAuthService;
import com.neo.service.convertRecord.IConvertRecordService;

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

	/**
	 *  转换失败就把次数还给用户
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView arg3)
			throws Exception {
		Object convertResult = request.getAttribute(SysConstant.CONVERT_RESULT);
		
		//如果失败就要把次数还给用户
		if (convertResult != null && convertResult instanceof PtsConvertRecordPO) {
			PtsConvertRecordPO ptsConvertRecordPO = (PtsConvertRecordPO)convertResult;
			boolean updateConvertNum = iConvertRecordService.updateConvertNum(ptsConvertRecordPO) > 0;
			if(!updateConvertNum) {
				SysLogUtils.info("归还用户转换次数失败："+ ptsConvertRecordPO.toString());
			}
		}
	}

	
	
	/**
	 * 限制游客和登陆者的转换次数和转换类型
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Long userID = HttpUtils.getSessionUserID(request);
		String body = HttpHelper.getBodyString(request);
		ConvertParameterBO convertParameterBO = JsonUtils.json2obj(body, ConvertParameterBO.class);
		IResult<EnumResultCode> result = iAuthService.checkUserAuth(convertParameterBO, userID);

		if(!result.isSuccess()) {
			HttpUtils.sendResponse(request, response, JsonResultUtils.buildFailJsonResultByResultCode(result.getData()));
			return false;
		}
		if(userID!=null){
			//发送积分事件
			memberShipHelper.addMemberEvent(userID, EnumEventType.CONVERT_EVENT);
		}
		return true;
	}






}
