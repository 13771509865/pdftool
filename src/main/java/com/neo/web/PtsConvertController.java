package com.neo.web;

import java.util.Map;

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
import com.neo.service.convertParameterBO.ConvertParameterBOService;
import com.neo.service.manager.MqConvertManager;

/**
 * 转码控制器
 * @authore xujun
 * @create 2019-07-23
 */

@Controller
public class PtsConvertController{


	@Autowired
	private MqConvertManager mqConvertManager;

	@Autowired
	private PtsConvertService ptsConvertService;

	@Autowired
	private ConfigProperty ConfigProperty;

	@Autowired
	private ConvertParameterBOService convertParameterBOService;

	
	
	@RequestMapping(value = "/convert")
	@ResponseBody
	public Map<String, Object> convert(@RequestBody ConvertParameterBO convertBO)  {
		IResult<FcsFileInfoBO> result = ptsConvertService.dispatchConvert(convertBO, ConfigProperty.getConvertTicketWaitTime());
		FcsFileInfoBO fcsFileInfoBO = result.getData();
		if (result.isSuccess()) {
			return JsonResultUtils.successMapResult(fcsFileInfoBO);
		} else {
			return JsonResultUtils.buildMapResult(fcsFileInfoBO.getCode(), fcsFileInfoBO, result.getMessage());
		}
	}


	@RequestMapping(value = "/mqconvert")
	@ResponseBody
	public Map<String, Object> mqconvert(@RequestBody ConvertParameterBO convertBO)  {
		//        SysLog4JUtils.info(parameter.toString());
		mqConvertManager.Producer(convertBO);
		return JsonResultUtils.successMapResult();
	}


}
