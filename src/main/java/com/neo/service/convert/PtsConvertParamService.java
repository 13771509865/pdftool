package com.neo.service.convert;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.neo.commons.util.JsonUtils;
import com.neo.model.bo.ConvertParameterBO;


@Service("ptsConvertParamService")
public class PtsConvertParamService {
	
	public Map<String, Object> buildFcsMapParam(ConvertParameterBO convertBO){
		Map<String, Object> convertParamMap = JsonUtils.parseJSON2Map(convertBO.toString());
		Map<String, Object> fcsParamMap = new HashMap<>();
		fcsParamMap.putAll(convertParamMap);
		return fcsParamMap;
	}

}
