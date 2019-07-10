package com.neo.controller;

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
 * @authore sumnear
 * @create 2018-12-13 20:36
 */
@Controller
public class FeedbackController{
	
	private Logger feedback =  LoggerFactory.getLogger("Feedback");
	

    @RequestMapping(value = "/feedback")
    @ResponseBody
    public Map<String, Object> convertPdf2word(Integer star,Integer type,String content,HttpServletRequest request)  {
        
        if(StringUtils.isEmpty(star)) {
        	return JsonResultUtils.failMapResult("请为我打分");
        }
        if(StringUtils.isEmpty(type)) {
        	return JsonResultUtils.failMapResult("请选择反馈类型");
        }
        if(type == 10 && StringUtils.isEmpty(content)) {
        	return JsonResultUtils.failMapResult("请填写反馈内容");
        }
        if(type != 10) {
        	content = EnumFeedbackType.getStatusInfo(type);
        } 
        HttpSession session = request.getSession();
        String userInfo = (String)session.getAttribute(ConstantCookie.SESSION_USER);
        String strInfo = "";
        String username = "游客";
        if(!StringUtils.isEmpty(userInfo)) {
        	UserBO userBO = JsonUtils.json2obj(userInfo, UserBO.class);
        	username = userBO.getName();
        	strInfo =userBO.getPhone()+"::"+userBO.getAccount();
        }
        feedback.info(username+"::"+star+"颗星"+"::"+content+"::"+strInfo);
        return JsonResultUtils.successMapResult("操作成功！");
    }
    
    
}
