package com.neo.web.uaa;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.commons.cons.constants.ConstantCookie;
import com.neo.commons.util.JsonResultUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.model.bo.UserBO;
import com.neo.service.uaa.UaaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(value = "单点相关Controller", tags = {"单点相关Controller"})
@Controller
public class UaaController {
	
	@Autowired
	private  UaaService uaaService;

	
	/**
	 * 获取用户信息
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "获取用户信息")
	@GetMapping("/detail")
	@ResponseBody
	public Map<String,Object> getUserInfoUaa(HttpServletRequest request){
		UserBO userBO = null;
		HttpSession session = request.getSession();
		String userInfo = (String)session.getAttribute(ConstantCookie.SESSION_USER);
		if(StringUtils.isBlank(userInfo)) {
			return JsonResultUtils.failMapResult();
		}
		userBO = JsonUtils.json2obj(userInfo, UserBO.class);
		return JsonResultUtils.successMapResult(userBO);
	}
	
	
	/**
	 * 登出
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "登出")
	@GetMapping(value = "/logout")
	@ResponseBody
    public Map<String, Object> logout(HttpServletRequest request, HttpServletResponse response) {
		uaaService.uaaLogout(request, response);
		return JsonResultUtils.successMapResult(null,"用户登出成功");
    }
	
	
	
	
	
	
	
	
	
	
}
