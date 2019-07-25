package com.neo.service.convert;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.GetIpAddrUtils;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.model.po.FcsFileInfoPO;


@Service("ptsConvertParamService")
public class PtsConvertParamService {
	
	@Autowired
	private ConfigProperty configProperty;
	
	
	
	public Map<String, Object> buildFcsMapParam(ConvertParameterBO convertBO){
		Map<String, Object> convertParamMap = JsonUtils.parseJSON2Map(convertBO.toString());
		Map<String, Object> fcsParamMap = new HashMap<>();
		fcsParamMap.putAll(convertParamMap);
		return fcsParamMap;
	}
	
	
	
	public void buildConvertParameterBO(ConvertParameterBO convertBO) {
		if(convertBO.getConvertTimeOut() == null) {
			convertBO.setConvertTimeOut(configProperty.getConvertTimeout());
		}
	}
	
	
	
	
	public FcsFileInfoPO buildFcsFileInfoParameter(FcsFileInfoBO fcsFileInfoBO,HttpServletRequest request) {
		FcsFileInfoPO fcsFileInfoPO = new FcsFileInfoPO();
		fcsFileInfoPO.setIpaddress(GetIpAddrUtils.getIpAddr(request));
		fcsFileInfoPO.setUserID(HttpUtils.getSessionUserID(request));
		fcsFileInfoPO.setFileHash(fcsFileInfoBO.getFileHash());
		fcsFileInfoPO.setResultCode(fcsFileInfoBO.getCode());
		fcsFileInfoPO.setDestFileName(fcsFileInfoBO.getDestFileName());
		fcsFileInfoPO.setSrcFileName(fcsFileInfoBO.getSrcFileName());
		fcsFileInfoPO.setDestFileSize(fcsFileInfoBO.getDestFileSize());
		fcsFileInfoPO.setSrcFileSize(fcsFileInfoBO.getSrcFileSize());
		fcsFileInfoPO.setConvertType(fcsFileInfoBO.getConvertType());
		fcsFileInfoPO.setSrcStoragePath(fcsFileInfoBO.getSrcStoragePath());
		fcsFileInfoPO.setDestStoragePath(fcsFileInfoBO.getDestStoragePath());
		fcsFileInfoPO.setViewUrl(fcsFileInfoBO.getViewUrl());
		return fcsFileInfoPO;
		
	}
	
	
	

}
