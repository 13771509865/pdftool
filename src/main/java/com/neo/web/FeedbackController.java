package com.neo.web;

import com.neo.commons.cons.ConstantCookie;
import com.neo.commons.cons.EnumFeedbackType;
import com.neo.commons.util.JsonResultUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.commons.util.SysLog4JUtils;
import com.neo.model.bo.UserBO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户反馈controller
 *
 * @authore xujun
 * @create 2018-7-15
 */
@Controller
public class FeedbackController{



	@RequestMapping(value = "/feedback")
	@ResponseBody
	public Map<String, Object> convertPdf2word(Integer star,String type,String content,HttpServletRequest request)  {
		Logger feedback = LoggerFactory.getLogger("Feedback");
		if(StringUtils.isEmpty(star)) {
			return JsonResultUtils.failMapResult("请为我打分");
		}

		if(type.contains(",")) {//选了多个反馈标签
			String[] types = type.split(",");
			if(types.length>3) {
				return JsonResultUtils.failMapResult("最多只能选择3个标签");
			}
			for(String value : types) {
				if("10".equals(value) && StringUtils.isEmpty(content)) {
					return JsonResultUtils.failMapResult("请填写反馈内容");
				}
				if(!"10".equals(value)) {
					content += EnumFeedbackType.getStatusInfo(value)+"::";
				} 
			}
		}else {//只选择一个标签
			if("10".equals(type) && StringUtils.isEmpty(content) ) {
				return JsonResultUtils.failMapResult("请填写反馈内容");
			}
			if(!"10".equals(type)) {
				content = EnumFeedbackType.getStatusInfo(type);
			} 
		}

		HttpSession session = request.getSession();
		String userInfo = (String)session.getAttribute(ConstantCookie.SESSION_USER);
		String strInfo = "";
		String username = "游客";
		if(!StringUtils.isEmpty(userInfo)) {//获取登录者信息
			UserBO userBO = JsonUtils.json2obj(userInfo, UserBO.class);
			username = userBO.getName();
			strInfo =userBO.getPhone()+"::"+userBO.getAccount();
		}
		
		feedback.info(username+"::"+star+"颗星"+"::"+content+"::"+strInfo);
		return JsonResultUtils.successMapResult("反馈问题已记录");
	}

}
