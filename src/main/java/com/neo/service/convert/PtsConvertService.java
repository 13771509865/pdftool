package com.neo.service.convert;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.neo.dao.FcsFileInfoPOMapper;
import com.neo.dao.PtsSummaryPOMapper;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.model.po.ConvertParameterPO;
import com.neo.model.po.FcsFileInfoPO;
import com.neo.model.po.PtsSummaryPO;
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
	
	@Autowired
	private FcsFileInfoPOMapper fcsFileInfoBOMapper;

	@Autowired
	private PtsSummaryPOMapper ptsSummaryPOMapper;
	
	/**
	 * 调用fcs进行真实转换
	 * @param convertBO
	 * @param waitTime
	 * @return
	 */
	public IResult<FcsFileInfoBO> dispatchConvert(ConvertParameterBO convertBO,Integer waitTime,HttpServletRequest request){
		
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
			
			ConvertParameterPO convertPO = ptsConvertParamService.buildConvertParameterPO(convertBO);//暂只有给个超时时间
			//调用fcs进行转码
			IResult<HttpResultEntity> httpResult = httpAPIService.doPost(ptsProperty.getFcs_convert_url(), ptsConvertParamService.buildFcsMapParamPO(convertPO));
			
			if (!HttpUtils.isHttpSuccess(httpResult)) {
				fcsFileInfoBO.setCode(ResultCode.E_FCS_CONVERT_FAIL.getValue());
				return DefaultResult.failResult(ResultCode.E_FCS_CONVERT_FAIL.getInfo(),fcsFileInfoBO);
			}
			Map<String, Object> fcsMap= JsonUtils.parseJSON2Map(httpResult.getData().getBody());
			SysLogUtils.info("fcs转码结果："+fcsMap.toString());
			
			fcsFileInfoBO = JsonUtils.json2obj(fcsMap.get(SysConstant.FCS_DATA), FcsFileInfoBO.class);
			if(fcsFileInfoBO.getCode() == 0) {//转换成功
				
				updateFcsFileInfo(fcsFileInfoBO,request);//这里只记录转换成功的pts_convert
				
				//转换成功记录一下，拦截器要用
				request.setAttribute(SysConstant.CONVERT_RESULT, ResultCode.E_SUCCES.getValue());
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
	
	/**
	 * pts_convert插入成功的转换数据，只更新登录用户并且转换成功的
	 * @param fcsFileInfoBO
	 * @param request
	 * @return
	 */
	public IResult<String> updateFcsFileInfo(FcsFileInfoBO fcsFileInfoBO,HttpServletRequest request) {
		try {
			if(HttpUtils.getSessionUserID(request) == null) {//看有没有登录
				return DefaultResult.failResult();
			}
			FcsFileInfoPO fcsFileInfoPO = ptsConvertParamService.buildFcsFileInfoParameter(fcsFileInfoBO, request);
			
			//根据userId和fileHash去update
			int count = fcsFileInfoBOMapper.updatePtsConvert(fcsFileInfoPO);
			if(count < 1) {
				int a = fcsFileInfoBOMapper.insertPtsConvert(fcsFileInfoPO);
			}
			return DefaultResult.successResult();
		} catch (Exception e) {
			SysLogUtils.error("fcsFileInfo插入数据库失败，失败原因："+e);
			return DefaultResult.failResult();
		}
	}
	

	/**
	 * 每次转换，更新转换的记录,不区分登录转态
	 * @param fcsFileInfoBO
	 * @param convertBO
	 * @param request
	 * @return
	 */
	public IResult<String> updatePtsSummay(FcsFileInfoBO fcsFileInfoBO, ConvertParameterBO convertBO,HttpServletRequest request){
		try {
			PtsSummaryPO ptsSummaryPO = ptsConvertParamService.buildPtsSummaryPOParameter(fcsFileInfoBO,convertBO, request);
			int upCount = ptsSummaryPOMapper.updatePtsSumm(ptsSummaryPO);
			if(upCount < 1) {
				int a = ptsSummaryPOMapper.insertPtsSumm(ptsSummaryPO);
			}
		} catch (Exception e) {
			SysLogUtils.error("更新转换的记录失败，失败原因："+e);
		}
		return DefaultResult.successResult();
	}
	

}
