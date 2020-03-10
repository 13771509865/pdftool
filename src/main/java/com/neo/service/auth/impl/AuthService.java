package com.neo.service.auth.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumAuthCode;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.EnumStatus;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.util.DateViewUtils;
import com.neo.dao.PtsAuthPOMapper;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.po.PtsAuthPO;
import com.neo.model.po.PtsConvertRecordPO;
import com.neo.model.qo.PtsConvertRecordQO;
import com.neo.service.auth.IAuthService;
import com.neo.service.convertRecord.IConvertRecordService;

@Service("authService")
public class AuthService implements IAuthService{

	@Autowired
	private PtsAuthPOMapper ptsAuthPOMapper;

	@Autowired
	private AuthManager authManager;

	@Autowired
	private IConvertRecordService iConvertRecordService;


	/**
	 * 根据userid检查用户的转换权限
	 * @param convertParameterBO
	 * @param userID
	 * @return
	 */
	@Override
	public IResult<EnumResultCode> checkUserAuth(ConvertParameterBO convertParameterBO,Long userID){

		//获取用户权限
		IResult<Map<String,Object>> getPermissionResult = authManager.getPermission(userID);
		Map<String,Object> map = getPermissionResult.getData();
		
		//根据convertType，拿authCode
		String authCode = authManager.getAuthCode(convertParameterBO);
		String booleanAuth = String.valueOf(map.get(authCode));

		//检查转换类型的权限
		if(StringUtils.isBlank(booleanAuth) || !StringUtils.equals(booleanAuth, SysConstant.TRUE)) {
			return DefaultResult.failResult(EnumResultCode.E_PERMISSION);
		}

		//获取允许转换的次数
		Integer maxConvertTimes = Integer.valueOf(map.get(EnumAuthCode.getModuleNum(authCode)).toString());

		//转换次数检查
		IResult<EnumResultCode> resultCheckConvertTimes = checkConvertTimes(userID,maxConvertTimes,EnumAuthCode.getValue(authCode));
		if(!resultCheckConvertTimes.isSuccess()) {
			return DefaultResult.failResult(resultCheckConvertTimes.getData());
		}
		return DefaultResult.successResult();
	}




	/**
	 * 检查用户的转换次数
	 * @param ipAddr
	 * @param userID
	 * @return
	 */
	@Override
	public IResult<EnumResultCode> checkConvertTimes(Long userID,Integer maxConvertTimes,Integer module) {

		//次数为-1，直接返回true
		if(maxConvertTimes == -1) {
			return DefaultResult.successResult();
		}
		PtsConvertRecordPO ptsConvertRecordPO = new PtsConvertRecordPO();
		String nowDate = DateViewUtils.getNow();
		String nowTime = DateViewUtils.getNowTime();
		ptsConvertRecordPO.setCreateDate(DateViewUtils.parseSimple(nowDate));//时间搞一搞
		ptsConvertRecordPO.setCreateTime(DateViewUtils.parseSimpleTime(nowTime));
		ptsConvertRecordPO.setModifiedDate(DateViewUtils.parseSimple(nowDate));
		ptsConvertRecordPO.setModifiedTime(DateViewUtils.parseSimpleTime(nowTime));
		ptsConvertRecordPO.setModule(module);
		ptsConvertRecordPO.setUserID(userID);
		ptsConvertRecordPO.setConvertNum(1);
		ptsConvertRecordPO.setStatus(EnumStatus.ENABLE.getValue());
		
		PtsConvertRecordQO ptsConvertRecordQO = new PtsConvertRecordQO();
		ptsConvertRecordQO.setConvertNum(maxConvertTimes);
		
		boolean insertOrUpdatePtsConvertRecord = iConvertRecordService.insertOrUpdatePtsConvertRecord(ptsConvertRecordPO, ptsConvertRecordQO)>0;
		if(insertOrUpdatePtsConvertRecord) {
			return DefaultResult.successResult();
		}
		
		return DefaultResult.failResult(EnumResultCode.E_USER_CONVERT_NUM_ERROR);
	}



	/**
	 * 检查用户的上传文件大小权限
	 * @param userid
	 * @return
	 */
	@Override
	public IResult<EnumResultCode> checkUploadSize(Long userID,Long uploadSize){
		IResult<Map<String,Object>> getPermissionResult = authManager.getPermission(userID);
		Map<String,Object> map = getPermissionResult.getData();
		Integer maxUploadSize = Integer.valueOf(map.get(EnumAuthCode.PTS_UPLOAD_SIZE.getAuthCode()).toString());

		if(uploadSize > (maxUploadSize*1024*1024)) {
			if(userID == null) {
				return DefaultResult.failResult(EnumResultCode.E_VISITOR_UPLOAD_ERROR);
			}else {
				return DefaultResult.failResult(EnumResultCode.E_USER_UPLOAD_ERROR);
			}
		}
		return DefaultResult.successResult(); 
	}




	/**
	 * 插入用户auth信息
	 * @param ptsAuthPO
	 * @return
	 */
	@Override
	public Integer insertPtsAuthPO(PtsAuthPO ptsAuthPO) {
		return ptsAuthPOMapper.insertPtsAuthPO(ptsAuthPO);
	}


	/**
	 * 根据userid查询auth信息
	 * @param userid
	 * @return
	 */
	@Override
	public List<PtsAuthPO> selectAuthByUserid(Long userid){
		return ptsAuthPOMapper.selectAuthByUserid(userid);
	}


	/**
	 * 根据userid修改auth信息
	 */
	public Integer updatePtsAuthPOByUserId(PtsAuthPO ptsAuthPO) {
		return ptsAuthPOMapper.updatePtsAuthPOByUserId(ptsAuthPO);
	}
	
	
	/**
	 * 删除用户权限
	 * @param ptsAuthPO
	 * @return
	 */
	public Integer deletePtsAuth(PtsAuthPO ptsAuthPO) {
		return ptsAuthPOMapper.deletePtsAuth(ptsAuthPO);
	}





}
