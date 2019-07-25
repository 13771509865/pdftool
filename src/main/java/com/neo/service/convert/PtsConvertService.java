package com.neo.service.convert;

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
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.model.po.FcsFileInfoPO;
import com.neo.service.httpclient.HttpAPIService;
import com.neo.service.ticket.TicketManager;


/**
 * 调用fcs转码服务
 * @author xujun
 * 2019-07-23
 */
@Service("ptsConvertService")
public class PtsConvertService {

	@Autowired
	private TicketManager ticketManager;
	
	@Autowired
	private HttpAPIService httpAPIService;
	
	@Autowired
	private PtsProperty ptsProperty;
	
	@Autowired
	private PtsConvertParamService ptsConvertParamService;

	
	/**
	 * 调用fcs进行真实转换
	 * @param convertBO
	 * @param waitTime
	 * @return
	 */
	public IResult<FcsFileInfoBO> dispatchConvert(ConvertParameterBO convertBO,Integer waitTime){
		
		FcsFileInfoBO fcsFileInfoBO = new FcsFileInfoBO();
		//取超时时间
		String ticket;
		if (waitTime >= 0) { //超过waitTime时间返回null
			ticket = ticketManager.poll(waitTime);
		} else { //waitTime小于0时表示等待直到取到票
			ticket = ticketManager.take();
		}
		if (StringUtils.isEmpty(ticket)) {
			fcsFileInfoBO.setCode(ResultCode.E_SERVER_BUSY.getValue());
			return DefaultResult.failResult(ResultCode.E_SERVER_BUSY.getInfo(), fcsFileInfoBO);
		}
		try {
			//调用fcs进行转码
			IResult<HttpResultEntity> httpResult = httpAPIService.doPost(ptsProperty.getFcs_convert_url(), ptsConvertParamService.buildFcsMapParam(convertBO));
			
			if (!HttpUtils.isHttpSuccess(httpResult)) {
				fcsFileInfoBO.setCode(ResultCode.E_FCS_CONVERT_FAIL.getValue());
				return DefaultResult.failResult(ResultCode.E_FCS_CONVERT_FAIL.getInfo(),fcsFileInfoBO);
			}
			Map<String, Object> fcsMap= JsonUtils.parseJSON2Map(httpResult.getData().getBody());
			SysLogUtils.info("fcs转码结果："+fcsMap.toString());
			
			fcsFileInfoBO = (FcsFileInfoBO)fcsMap.get(SysConstant.FCS_DATA);
			if(fcsFileInfoBO.getCode() == 0) {
				//成功的话在request里面给个标记，别忘了
				
				return DefaultResult.successResult(fcsFileInfoBO);
			}else {
				return DefaultResult.failResult(fcsMap.get(SysConstant.FCS_MESSAGE).toString(),fcsFileInfoBO);
			}
			
		} catch (Exception e) {
			fcsFileInfoBO.setCode(ResultCode.E_SERVER_UNKNOW_ERROR.getValue());
            SysLogUtils.error(convertBO.getSrcPath() + "文件转换未知错误", e);
            return DefaultResult.failResult(ResultCode.E_SERVER_UNKNOW_ERROR.getInfo(), fcsFileInfoBO);
		}finally {
			 ticketManager.put(ticket);
		}

	}
	
	


}
