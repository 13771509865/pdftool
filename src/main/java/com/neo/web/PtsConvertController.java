package com.neo.web;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.commons.cons.IResult;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.JsonResultUtils;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.service.convert.PtsConvertService;
import com.neo.service.convert.redisMQ.RedisMQConvertService;

/**
 * 转码控制器
 * @authore xujun
 * @create 2019-07-23
 */

@Controller
public class PtsConvertController{


	@Autowired
	private RedisMQConvertService redisMQConvertService;

	@Autowired
	private PtsConvertService ptsConvertService;

	@Autowired
	private ConfigProperty ConfigProperty;


	
	
	@RequestMapping(value = "/convert")
	@ResponseBody
	public Map<String, Object> convert(@RequestBody ConvertParameterBO convertBO,HttpServletRequest request)  {
		IResult<FcsFileInfoBO> result = ptsConvertService.dispatchConvert(convertBO, ConfigProperty.getConvertTicketWaitTime(),request);
		if (result.isSuccess()) {
			return JsonResultUtils.successMapResult(result.getData());
		} else {
			return JsonResultUtils.buildMapResult(result.getData().getCode(), result.getData(), result.getMessage());
		}
	}

	
	

	@RequestMapping(value = "/mqconvert")
	@ResponseBody
	public Map<String, Object> mqconvert(@RequestBody ConvertParameterBO convertBO)  {
		
		IResult<String>  result =  redisMQConvertService.Producer(convertBO);
		if(result.isSuccess()) {
			return JsonResultUtils.successMapResult();
		}else {
			return JsonResultUtils.failMapResult(result.getMessage());
		}
		
	}
	



}
