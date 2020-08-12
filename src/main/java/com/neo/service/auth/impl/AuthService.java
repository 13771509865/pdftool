package com.neo.service.auth.impl;

import com.neo.commons.cons.*;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.DateViewUtils;
import com.neo.commons.util.HttpUtils;
import com.neo.dao.PtsAuthPOMapper;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.po.PtsAuthPO;
import com.neo.model.po.PtsConvertRecordPO;
import com.neo.model.po.PtsTotalConvertRecordPO;
import com.neo.model.qo.PtsAuthQO;
import com.neo.model.qo.PtsConvertRecordQO;
import com.neo.model.qo.PtsTotalConvertRecordQO;
import com.neo.service.auth.IAuthService;
import com.neo.service.cache.impl.RedisCacheManager;
import com.neo.service.convertRecord.IConvertRecordService;
import com.neo.service.convertRecord.ITotalConvertRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("authService")
public class AuthService implements IAuthService{

	@Autowired
	private PtsAuthPOMapper ptsAuthPOMapper;

	@Autowired
	private AuthManager authManager;

	@Autowired
	private IConvertRecordService iConvertRecordService;

	@Autowired
	private ConfigProperty configProperty;

	@Autowired
	private RedisCacheManager redisCacheManager;

	@Autowired
	private ITotalConvertRecordService iTotalConvertRecordService;



	/**
	 * 根据userid检查用户的转换权限
	 * @param convertParameterBO
	 * @param userID
	 * @return
	 */
	@Override
	public IResult<EnumResultCode> checkConvertNum(ConvertParameterBO convertParameterBO,Long userID){

		//根据convertType，拿authCode
		String authCode = authManager.getAuthCode(convertParameterBO);
		
		//获取用户权限
		IResult<Map<String,Object>> getPermissionResult = authManager.getPermission(userID,EnumAuthCode.getModuleNum(authCode));
		Map<String,Object> map = getPermissionResult.getData();

		String booleanAuth = String.valueOf(map.get(authCode));

		//检查转换类型的权限
		if(StringUtils.isBlank(booleanAuth) || !StringUtils.equals(booleanAuth, SysConstant.TRUE)) {
			return DefaultResult.failResult(EnumResultCode.E_PERMISSION);
		}

		//获取允许转换的次数
		Integer maxConvertTimes = Integer.valueOf(map.get(EnumAuthCode.getModuleNum(authCode)).toString());
		//资源包次数
		Integer rpConvertNum =map.get(EnumAuthCode.PTS_CONVERT_NUM.getAuthCode())==null?null:Integer.valueOf(map.get(EnumAuthCode.PTS_CONVERT_NUM.getAuthCode()).toString());

		//转换次数检查
		IResult<EnumResultCode> resultCheckConvertTimes = checkConvertTimes(userID,maxConvertTimes,EnumAuthCode.getValue(authCode),rpConvertNum);
		if(!resultCheckConvertTimes.isSuccess()) {
			return DefaultResult.failResult(resultCheckConvertTimes.getData());
		}
		return DefaultResult.successResult();
	}


	/**
	 * 检查用户的转换次数
	 * @param userID
	 * @param maxConvertTimes
	 * @param module
	 * @return
	 */
	@Override
	public IResult<EnumResultCode> checkConvertTimes(Long userID,Integer maxConvertTimes,Integer module,Integer rpConvertNum) {

		String nowDate = DateViewUtils.getNow();
		String nowTime = DateViewUtils.getNowTime();
		PtsConvertRecordPO ptsConvertRecordPO = PtsConvertRecordPO.builder()
						.createDate(DateViewUtils.parseSimple(nowDate))//时间搞一搞
						.createTime(DateViewUtils.parseSimpleTime(nowTime))
						.modifiedDate(DateViewUtils.parseSimple(nowDate))
						.modifiedTime(DateViewUtils.parseSimpleTime(nowTime))
						.module(module)
						.userID(userID)
						.convertNum(1)
						.status(EnumStatus.ENABLE.getValue()).build();
		
		PtsConvertRecordQO ptsConvertRecordQO = PtsConvertRecordQO.builder().convertNum(maxConvertTimes==-1?9999:maxConvertTimes).build();

		boolean isRPT = false;
		boolean insertOrUpdatePtsConvertRecord = iConvertRecordService.insertOrUpdatePtsConvertRecord(ptsConvertRecordPO, ptsConvertRecordQO)>0;

		//1,如果会员权益和默认权益都没有了
		//2,存在资源包次数
		//3,使用用资源包次数
		if(!insertOrUpdatePtsConvertRecord) {
			if(rpConvertNum==null || rpConvertNum <1){
				return DefaultResult.failResult(EnumResultCode.E_USER_CONVERT_NUM_ERROR);
			}
			PtsTotalConvertRecordPO ptsTotalConvertRecordPO = PtsTotalConvertRecordPO.builder()
					.authCode(EnumAuthCode.getAuthCode(module))
					.convertNum(1)
					.createDate(DateViewUtils.parseSimple(nowDate))
					.createTime(DateViewUtils.parseSimple(nowTime))
					.modifiedDate(DateViewUtils.parseSimple(nowDate))
					.modifiedTime(DateViewUtils.parseSimple(nowTime))
					.status(EnumStatus.ENABLE.getValue())
					.userID(userID).build();

			PtsTotalConvertRecordQO ptsTotalConvertRecordQO = PtsTotalConvertRecordQO.builder().convertNum(rpConvertNum).build();
			isRPT = iTotalConvertRecordService.insertOrUpdatePtsTotalConvertRecord(ptsTotalConvertRecordPO,ptsTotalConvertRecordQO)>0;
			if(!isRPT){
				return DefaultResult.failResult(EnumResultCode.E_USER_CONVERT_NUM_ERROR);
			}
		}
		HttpUtils.getRequest().setAttribute(SysConstant.IS_RPT, isRPT);//传递给控制层，是否使用了资源包
		return DefaultResult.successResult();
	}



	/**
	 * 检查用户的上传文件大小权限
	 * @param userID
	 * @return
	 */
	@Override
	public IResult<EnumResultCode> checkUploadSize(Long userID,Long uploadSize,Integer module){
		IResult<Map<String,Object>> getPermissionResult = authManager.getPermission(userID,EnumAuthCode.getModuleSize(module));
		Map<String,Object> map = getPermissionResult.getData();

		Integer maxUploadSize = Integer.valueOf(map.get(EnumAuthCode.getModuleSize(module)).toString());
		
		if(maxUploadSize !=-1 && uploadSize > (maxUploadSize*1024*1024)) {
			return DefaultResult.failResult(EnumResultCode.E_USER_UPLOAD_ERROR);
		}
		return DefaultResult.successResult(); 
	}


	/**
	 * 检查每日转换次数是否超出限流次数
	 * @param userID
	 * @return
	 */
	@Override
	public IResult<EnumResultCode> checkConvertNumByDay(Long userID){
		Long time = DateViewUtils.getSecondsNextEarlyMorning();
		Boolean isLimit = redisCacheManager.callConvertLimiter(String.valueOf(userID),configProperty.getConvertLimiter(),time);
		return isLimit?DefaultResult.failResult(EnumResultCode.E_CONVERT_LIMIT_ERROR):DefaultResult.successResult();
	}





	/**
	 * 插入用户auth信息
	 * @return
	 */
	public Integer insertPtsAuthPO(List<PtsAuthPO> list) {
		return ptsAuthPOMapper.insertPtsAuthPO(list);
	}
	
	

	/**
	 * 根据userid,authCode,status查询auth信息,过期时间大于当前时间
	 * @param ptsAuthQO
	 * @return
	 */
	@Override
	public List<PtsAuthPO> selectPtsAuthPO(PtsAuthQO ptsAuthQO){
		return ptsAuthPOMapper.selectPtsAuthPO(ptsAuthQO);
	}

	
	/**
	 * 删除用户权限
	 * @param userid
	 * @return
	 */
	public Integer deletePtsAuth(Long userid) {
		return ptsAuthPOMapper.deletePtsAuth(userid);
	}



	

}
