package com.neo.service.convert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.neo.model.qo.FcsFileInfoQO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.EnumUaaRoleType;
import com.neo.commons.cons.IResult;
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
import com.neo.model.bo.UserBO;
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
	public IResult<FcsFileInfoBO> dispatchConvert(ConvertParameterBO convertBO,Integer waitTime,UserBO userBO,String ipAddress,String cookie){
		FcsFileInfoBO fcsFileInfoBO = new FcsFileInfoBO();
		//取超时时间
		String ticket;
		if (waitTime >= 0) { //超过waitTime时间返回null
			ticket = ticketManager.poll(waitTime);
		} else { //waitTime小于0时表示等待直到取到票
			ticket = ticketManager.take();
		}
		if (StringUtils.isEmpty(ticket)) {
			fcsFileInfoBO.setCode(EnumResultCode.E_SERVER_BUSY.getValue());
			return DefaultResult.failResult(EnumResultCode.E_SERVER_BUSY.getInfo(), fcsFileInfoBO);
		}
		try {
			
			ConvertParameterPO convertPO = ptsConvertParamService.buildConvertParameterPO(convertBO);//暂只有给个超时时间
			//调用fcs进行转码
			IResult<HttpResultEntity> httpResult = httpAPIService.doPost(ptsProperty.getFcs_convert_url(), ptsConvertParamService.buildFcsMapParamPO(convertPO));
			
			if (!HttpUtils.isHttpSuccess(httpResult)) {
				fcsFileInfoBO.setCode(EnumResultCode.E_FCS_CONVERT_FAIL.getValue());
				return DefaultResult.failResult(EnumResultCode.E_FCS_CONVERT_FAIL.getInfo(),fcsFileInfoBO);
			}
			Map<String, Object> fcsMap= JsonUtils.parseJSON2Map(httpResult.getData().getBody());
			Integer errorCode = Integer.valueOf((fcsMap.get(SysConstant.FCS_ERRORCODE).toString()));
			
			fcsFileInfoBO = JsonUtils.json2obj(fcsMap.get(SysConstant.FCS_DATA), FcsFileInfoBO.class);
			SysLogUtils.info("ConvertType："+convertBO.getConvertType()+"==源文件相对路径:"+convertBO.getSrcRelativePath()+"==fcs转码结果："+ fcsFileInfoBO.getCode());
			if(errorCode == 0) {//转换成功
				Long userId = userBO==null?null:userBO.getUserId();
				updateFcsFileInfo(convertBO,fcsFileInfoBO,userId,ipAddress);
				return DefaultResult.successResult(fcsFileInfoBO);
			}else {
				return DefaultResult.failResult(fcsMap.get(SysConstant.FCS_MESSAGE).toString(),fcsFileInfoBO);
			}
			
		} catch (Exception e) {
			fcsFileInfoBO.setCode(EnumResultCode.E_SERVER_UNKNOW_ERROR.getValue());
            SysLogUtils.error(convertBO.getSrcPath() + "文件转换未知错误", e);
            return DefaultResult.failResult(EnumResultCode.E_SERVER_UNKNOW_ERROR.getInfo(), fcsFileInfoBO);
		}finally {
			 ticketManager.put(ticket);
		}

	}
	
	/**
	 * pts_convert插入成功的转换数据，只更新登录用户并且转换成功的
	 * @param fcsFileInfoBO
	 * @param convertBO
	 * @return
	 */
	public IResult<String> updateFcsFileInfo(ConvertParameterBO convertBO,FcsFileInfoBO fcsFileInfoBO,Long userId,String ipAddress) {
		try {
			//只记录登录用户的
			if(userId == null) {
				return DefaultResult.failResult();
			}
					
			FcsFileInfoPO fcsFileInfoPO = ptsConvertParamService.buildFcsFileInfoParameter(convertBO,fcsFileInfoBO, userId,ipAddress);
			
			//根据userId和fileHash去update
			int count = fcsFileInfoBOMapper.updatePtsConvert(fcsFileInfoPO);
			if(count < 1) {
				int a = fcsFileInfoBOMapper.insertPtsConvert(fcsFileInfoPO);
			}
			return DefaultResult.successResult();
		} catch (Exception e) {
			SysLogUtils.error("fcsFileInfo插入数据库失败，失败原因："+e.getMessage());
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
	@Async("asynConvertExecutor")
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

	/**
	 * 根据fileHash查询fcsFile信息
	 * @param fcsFileInfoQO
	 * @return
	 */
	public IResult<String> selectFcsFileInfoPOByFileHash(String fileHash,Long userId){
		if(userId == null) {
			return DefaultResult.failResult("请登录后，再执行此操作");
		}
		FcsFileInfoQO fcsFileInfoQO = new FcsFileInfoQO();
		fcsFileInfoQO.setFileHash(fileHash);
		fcsFileInfoQO.setUserID(userId);
		
		List<FcsFileInfoPO> list = fcsFileInfoBOMapper.selectFcsFileInfoPOByFileHash(fcsFileInfoQO);
		if(list.size() < 1 || list.isEmpty() ||StringUtils.isBlank(list.get(0).getUCloudFileId())){
			return DefaultResult.failResult(EnumResultCode.E_UCLOUDFILEID_NULL.getInfo());
		}
		return DefaultResult.successResult(list.get(0).getUCloudFileId());
	}
}
