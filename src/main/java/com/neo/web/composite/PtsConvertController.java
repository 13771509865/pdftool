package com.neo.web.composite;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.commons.cons.IResult;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.JsonResultUtils;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.service.convert.PtsConvertService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 转码控制器
 * @authore xujun
 * @create 2019-07-23
 */

@Api(value = "转换相关Controller", tags = {"转换相关Controller"})
@Controller
@RequestMapping(value = "/composite")
public class PtsConvertController{

//	@Autowired
//	private RedisMQConvertService redisMQConvertService;

	@Autowired
	private PtsConvertService ptsConvertService;

	@Autowired
	private ConfigProperty ConfigProperty;


	
	@ApiOperation(value = "同步转换")
	@PostMapping(value = "/convert")
	@ResponseBody
	public Map<String, Object> convert(@RequestBody ConvertParameterBO convertBO,HttpServletRequest request)  {
		if(convertBO.getSrcFileSize() == null) {
			return JsonResultUtils.failMapResult(EnumResultCode.E_NOTALL_PARAM.getInfo());
		}
		IResult<FcsFileInfoBO> result = ptsConvertService.dispatchConvert(convertBO, ConfigProperty.getConvertTicketWaitTime(),request);
		ptsConvertService.updatePtsSummay(result.getData(),convertBO,request);
		if (result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		} else {
			return JsonResultUtils.buildMapResult(result.getData().getCode(), result.getData(), result.getMessage());
		}
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
