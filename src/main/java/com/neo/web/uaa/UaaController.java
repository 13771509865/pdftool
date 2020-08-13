package com.neo.web.uaa;

import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.util.CookieUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.service.uaa.UaaService;
import com.yozosoft.auth.client.security.OAuth2AccessToken;
import com.yozosoft.auth.client.security.OAuth2CookieHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


@Api(value = "单点相关Controller", tags = {"单点相关Controller"})
@Controller
@RequestMapping(value = "/uaa")
public class UaaController {
	
	@Autowired
	private  UaaService uaaService;
	
	@Autowired
	private OAuth2CookieHelper oAuth2CookieHelper;

	
	/**
	 * 获取用户信息
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "判断用户是否在PDF登录")
	@ApiResponses({
			@ApiResponse(code=200 ,response=Map.class, message="固定返回模型，json字符串表现形式,data:主要字段内容，code：返回结果码，message：返回结果信息")})
	@GetMapping("/detail")
	@ResponseBody
	public Map<String,Object> getUserInfoUaa(HttpServletRequest request){
		return JsonResultUtils.successMapResult();
	}
	
	
	
	
	/**
	 * 设置cookie
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "设置cookie")
	@ApiImplicitParams({
			@ApiImplicitParam(name="auth" ,value="单点获取auth" ,required=true ,dataType="String",paramType="query"),
			@ApiImplicitParam(name="rememberMe" ,value="是否记住用户名密码" ,required=false ,dataType="Boolean",paramType="query")})
	@ApiResponses({
			@ApiResponse(code=200 ,response=Map.class, message="固定返回模型，json字符串表现形式,data:主要字段内容，code：返回结果码，message：返回结果信息")})
	@GetMapping("/setCookie")
	@ResponseBody
	public Map<String,Object> setCookie(@RequestParam String auth,
			@RequestParam(required = false, defaultValue = "false") Boolean rememberMe,
			HttpServletRequest request,HttpServletResponse response){
		IResult<OAuth2AccessToken> result = uaaService.checkSecurity(request);
		if(!result.isSuccess()) {
			return JsonResultUtils.failMapResult(EnumResultCode.E_UNLOGIN_ERROR.getInfo());
		}
		List<Cookie> list = oAuth2CookieHelper.createCookies(request, result.getData(), rememberMe);
		CookieUtils.setCookie(response, list);
		return JsonResultUtils.successMapResult();
	}
	
	
	/**
	 * 登出
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "登出")
	@ApiResponses({
			@ApiResponse(code=200 ,response=Map.class, message="固定返回模型，json字符串表现形式,data:主要字段内容，code：返回结果码，message：返回结果信息")})
	@GetMapping(value = "/logout")
	@ResponseBody
    public Map<String, Object> logout(HttpServletRequest request, HttpServletResponse response) {
		uaaService.uaaLogout(request, response);
		return JsonResultUtils.successMapResult(null,"用户登出成功");
    }
	
	
	
	
	
	
	
	
	
	
}
