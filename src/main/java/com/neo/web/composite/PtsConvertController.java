package com.neo.web.composite;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.commons.cons.EnumAuthCode;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.cons.constants.UaaConsts;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.DateViewUtils;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.model.po.PtsConvertRecordPO;
import com.neo.service.auth.impl.AuthManager;
import com.neo.service.convert.PtsConvertService;
import com.yozosoft.auth.client.security.UaaToken;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 转码控制器
 *
 * @authore xujun
 * @create 2019-07-23
 */

@Api(value = "转换相关Controller", tags = {"转换相关Controller"})
@Controller
@RequestMapping(value = "/composite")
public class PtsConvertController {

	@Autowired
	private AuthManager authManager;

	@Autowired
	private PtsConvertService ptsConvertService;

	@Autowired
	private ConfigProperty ConfigProperty;

	@ApiOperation(value = "同步转换")
	@PostMapping(value = "/convert")
	@ResponseBody
	public Map<String, Object> convert(@RequestBody ConvertParameterBO convertBO, HttpServletRequest request) {
		if (convertBO.getSrcFileSize() == null) {
			return JsonResultUtils.failMapResult(EnumResultCode.E_NOTALL_PARAM.getInfo());
		}
		String cookie = request.getHeader(UaaConsts.COOKIE);//文件上传给优云要用到
		UaaToken uaaToken = HttpUtils.getUaaToken(request);
		Boolean isMember = (Boolean)request.getAttribute(SysConstant.MEMBER_SHIP);
		IResult<FcsFileInfoBO> result = ptsConvertService.dispatchConvert(convertBO, uaaToken, HttpUtils.getIpAddr(request), cookie,isMember);
		ptsConvertService.updatePtsSummay(result.getData(), convertBO, request);

		//转换失败记录一下，拦截器要用
		if (!result.isSuccess()) {
			String authCode = authManager.getAuthCode(convertBO);
			String nowDate = DateViewUtils.getNow();
			request.setAttribute(SysConstant.CONVERT_RESULT, new PtsConvertRecordPO(uaaToken.getUserId(), EnumAuthCode.getValue(authCode), DateViewUtils.parseSimple(nowDate)));
			return JsonResultUtils.buildMapResult(result.getData().getCode(), result.getData(), result.getMessage());
		}
		return JsonResultUtils.successMapResult(result.getData());
	}




	//	@ApiOperation(value = "MQ转换")
	//	@PostMapping(value = "/mqconvert")
	//	@ResponseBody
	//	public Map<String, Object> mqconvert(@RequestBody ConvertParameterBO convertBO)  {
	//		if(convertBO.getSrcFileSize() == null) {
	//			return JsonResultUtils.failMapResult(EnumResultCode.E_NOTALL_PARAM.getInfo());
	//		}
	//		IResult<String>  result =  redisMQConvertService.Producer(convertBO);
	//		if(result.isSuccess()) {
	//			return JsonResultUtils.successMapResult();
	//		}else {
	//			return JsonResultUtils.failMapResult(result.getMessage());
	//		}
	//		
	//	}
}
