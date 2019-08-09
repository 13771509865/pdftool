package com.neo.web;

import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.commons.cons.ResultCode;
import com.neo.commons.cons.entity.FeedbackEntity;
import com.neo.commons.util.BindingResultUtils;
import com.neo.commons.util.JsonResultUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户反馈controller
 *
 * @authore xujun
 * @create 2018-7-15
 */
@Api(value = "用户反馈Controller", tags = {"用户反馈Controller"})
@Controller
public class FeedbackController{
	
	
	
	
	
	@ApiOperation(value = "用户反馈")
	@PostMapping(value = "/feedback")
	@ResponseBody
	public Map<String, Object> convertPdf2word(@RequestBody @Valid FeedbackEntity feedbackEntity ,BindingResult bindingResult){
		String erroMessage = BindingResultUtils.getMessage(bindingResult);
		if(StringUtils.isNotBlank(erroMessage)) {
			return JsonResultUtils.failMapResult(erroMessage);
		}
		Logger feedback = LoggerFactory.getLogger("Feedback");
		String str  = "反馈内容："+feedbackEntity.getContent()+ "\r\n";
		str += "联系方式："+feedbackEntity.getContactMode()+ "\r\n";
		str += "===============================================================\r\n";
		feedback.info(str);
		
		return JsonResultUtils.successMapResult(ResultCode.E_SUCCES.getValue());
	}
	
	
	
	
	
	
	
	
	



	/**
	 * 意见反馈
	 * @param star
	 * @param type
	 * @param content
	 * @param request
	 * @return
	 */
//	@ApiOperation(value = "用户反馈")
//	@PostMapping(value = "/feedback")
//	@ResponseBody
//	public Map<String, Object> convertPdf2word(Integer star,String type,String content,HttpServletRequest request)  {
//		Logger feedback = LoggerFactory.getLogger("Feedback");
//		if(StringUtils.isEmpty(star)) {
//			return JsonResultUtils.failMapResult("请为我打分");
//		}
//
//		if(type.contains(",")) {//选了多个反馈标签
//			String[] types = type.split(",");
//			if(types.length>3) {
//				return JsonResultUtils.failMapResult("最多只能选择3个标签");
//			}
//			for(String value : types) {
//				if("10".equals(value) && StringUtils.isEmpty(content)) {
//					return JsonResultUtils.failMapResult("请填写反馈内容");
//				}
//				if(!"10".equals(value)) {
//					content += EnumFeedbackType.getStatusInfo(value)+"::";
//				} 
//			}
//		}else {//只选择一个标签
//			if("10".equals(type) && StringUtils.isEmpty(content) ) {
//				return JsonResultUtils.failMapResult("请填写反馈内容");
//			}
//			if(!"10".equals(type)) {
//				content = EnumFeedbackType.getStatusInfo(type);
//			} 
//		}
//
//		HttpSession session = request.getSession();
//		String userInfo = (String)session.getAttribute(ConstantCookie.SESSION_USER);
//		String strInfo = "";
//		String username = "游客";
//		if(!StringUtils.isEmpty(userInfo)) {//获取登录者信息
//			UserBO userBO = JsonUtils.json2obj(userInfo, UserBO.class);
//			username = userBO.getName();
//			strInfo =userBO.getPhone()+"::"+userBO.getAccount();
//		}
//
//		feedback.info(username+"::"+star+"颗星"+"::"+content+"::"+strInfo);
//		return JsonResultUtils.successMapResult(ResultCode.E_SUCCES.getValue());
//	}

}
