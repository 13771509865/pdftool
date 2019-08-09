package com.neo.service.file;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.ResultCode;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.cons.entity.HttpResultEntity;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.bo.ViewTokenBO;
import com.neo.service.httpclient.HttpAPIService;

@Service("downLoadService")
public class DownLoadService {
	
	@Autowired
	private PtsProperty ptsProperty;
	
	@Autowired
	private HttpAPIService httpAPIService;
	
	
	
	/**
	 * 根据filehash获取vToken
	 * @param viewTokenBO
	 * @return
	 */
	public IResult<String> buildVToken(ViewTokenBO viewTokenBO){
		try {
			IResult<HttpResultEntity> httpResult = httpAPIService.doPostByJson(ptsProperty.getFcs_vToken_url(), viewTokenBO.toString());
			if (!HttpUtils.isHttpSuccess(httpResult)) {
				return DefaultResult.failResult(ResultCode.E_FCS_CONVERT_FAIL.getInfo());
			}
			Map<String, Object> fcsMap= JsonUtils.parseJSON2Map(httpResult.getData().getBody());
			
			Map<String, Object> tokenMap = JsonUtils.parseJSON2Map(fcsMap.get(SysConstant.FCS_DATA));
			String vToken =(String)tokenMap.get(SysConstant.FCS_VTOKEN);
			if(StringUtils.isBlank(vToken)) {
				return DefaultResult.failResult("获取vToken失败");
			}
			return DefaultResult.successResult(vToken);
		} catch (Exception e) {
			SysLogUtils.error(ResultCode.E_FCS_VTOKEN_FAIL.getInfo(), e);
			return DefaultResult.failResult(ResultCode.E_FCS_VTOKEN_FAIL.getInfo());
		}
	}

	
	
	
	
	
	
	
}
