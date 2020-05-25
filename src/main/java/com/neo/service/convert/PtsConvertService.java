package com.neo.service.convert;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumAuthCode;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.cons.entity.ConvertEntity;
import com.neo.commons.cons.entity.HttpResultEntity;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.DateViewUtils;
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
import com.neo.model.qo.FcsFileInfoQO;
import com.neo.service.auth.impl.AuthManager;
import com.neo.service.cache.impl.RedisCacheManager;
import com.neo.service.httpclient.HttpAPIService;
import com.neo.service.ticket.RedisTicketManager;
import com.yozosoft.auth.client.security.UaaToken;


/**
 * 调用fcs转码服务
 * @author xujun
 * 2019-07-23
 */
@Service("ptsConvertService")
public class PtsConvertService {


	@Autowired 
	private RedisTicketManager redisTicketManager;

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

	@Autowired
	private RedisCacheManager<String> redisCacheManager;

	@Autowired
	private AuthManager authManager;
	
	@Autowired
	private ConfigProperty configProperty;

	/**
	 * 调用fcs进行真实转换
	 * @param convertBO
	 * @param waitTime
	 * @return
	 */ 
	public IResult<FcsFileInfoBO> dispatchConvert(ConvertParameterBO convertBO,UaaToken uaaToken,ConvertEntity convertEntity){
		FcsFileInfoBO fcsFileInfoBO = new FcsFileInfoBO();
		
		//判断是否是重复失败文件
		if(EnumAuthCode.existReconvertModule(authManager.getAuthCode(convertBO), configProperty.getReConvertModule())) {
			if(StringUtils.isNotBlank(redisCacheManager.getHashValue(DateViewUtils.getNow(), convertEntity.getFileHash()))) {
				fcsFileInfoBO.setCode(EnumResultCode.E_CONVERT_FAIL.getValue());
				fcsFileInfoBO.setFileHash(convertEntity.getFileHash());
				return DefaultResult.failResult(EnumResultCode.E_CONVERT_FAIL.getInfo(),fcsFileInfoBO);
			}
		}
		
		//取超时时间
		String ticket = redisTicketManager.getConverTicket(convertEntity.getIsMember());

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

			//转换失败
			if(errorCode != 0) {
				//code=24，message做特殊处理
				String message = fcsFileInfoBO.getCode() == 24?EnumResultCode.E_MERGE_FILE_NAME_ERROR.getInfo():fcsMap.get(SysConstant.FCS_MESSAGE).toString();
				return DefaultResult.failResult(message,fcsFileInfoBO);
			}

			Long userId = uaaToken==null?null:uaaToken.getUserId();
			updateFcsFileInfo(convertBO,fcsFileInfoBO,userId,convertEntity.getIpAddress());
			return DefaultResult.successResult(fcsFileInfoBO);

		} catch (Exception e) {
			fcsFileInfoBO.setCode(EnumResultCode.E_SERVER_UNKNOW_ERROR.getValue());
			SysLogUtils.error(convertBO.getSrcPath() + "文件转换未知错误", e);
			return DefaultResult.failResult(EnumResultCode.E_SERVER_UNKNOW_ERROR.getInfo(), fcsFileInfoBO);
		}finally {
			redisTicketManager.returnPriorityTicket(ticket);
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
			int count = updatePtsConvert(fcsFileInfoPO);
			if(count < 1) {
				int a = insertPtsConvert(fcsFileInfoPO);
			}
			return DefaultResult.successResult();
		} catch (Exception e) {
			e.printStackTrace();
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
	@Async("updatePtsSummayExecutor")
	public IResult<String> updatePtsSummay(FcsFileInfoBO fcsFileInfoBO, ConvertParameterBO convertBO,ConvertEntity convertEntity){
		try {
			//不允许重复的文件，转换失败不统计
			if(EnumAuthCode.existReconvertModule(authManager.getAuthCode(convertBO), configProperty.getReConvertModule())
			   && StringUtils.isNotBlank(fcsFileInfoBO.getFileHash())
			   &&StringUtils.isNotBlank(redisCacheManager.getHashValue(DateViewUtils.getNow(), fcsFileInfoBO.getFileHash()))) {
				return DefaultResult.successResult();
			}
			
			PtsSummaryPO ptsSummaryPO = ptsConvertParamService.buildPtsSummaryPOParameter(fcsFileInfoBO,convertBO,convertEntity);
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




	/**
	 * 删除用户转换记录
	 * @param fcsFileInfoQO
	 * @return
	 */
	public int deletePtsConvert(FcsFileInfoQO fcsFileInfoQO){
		return fcsFileInfoBOMapper.deletePtsConvert(fcsFileInfoQO);
	}


	/**
	 * 修改转换记录状态（假删除）
	 * @param fcsFileInfoPO
	 * @return
	 */
	public int updatePtsConvert(FcsFileInfoPO fcsFileInfoPO){
		return fcsFileInfoBOMapper.updatePtsConvert(fcsFileInfoPO);
	}



	public int insertPtsConvert(FcsFileInfoPO fcsFileInfoPO){
		return fcsFileInfoBOMapper.insertPtsConvert(fcsFileInfoPO);
	}



}
