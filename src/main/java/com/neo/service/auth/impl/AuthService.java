package com.neo.service.auth.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumAuthCode;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.RedisConsts;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.DateViewUtils;
import com.neo.commons.util.StrUtils;
import com.neo.dao.PtsAuthPOMapper;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.po.PtsAuthPO;
import com.neo.service.auth.IAuthService;
import com.neo.service.cache.impl.RedisCacheManager;

@Service
public class AuthService implements IAuthService{

	@Autowired
	private PtsAuthPOMapper ptsAuthPOMapper;

	@Autowired
	private AuthManager authManager;

	@Autowired
	private RedisCacheManager<String> redisCacheManager;


	/**
	 * 根据userid检查用户的转换权限
	 * @param convertParameterBO
	 * @param userID
	 * @return
	 */
	@Override
	public IResult<EnumResultCode> checkUserAuth(ConvertParameterBO convertParameterBO,Long userID,String ipAddr){

		//获取用户权限
		IResult<Map<String,Object>> getPermissionResult = authManager.getPermission(userID);
		Map<String,Object> map = getPermissionResult.getData();
		String booleanAuth = String.valueOf(map.get(authManager.getAuthCode(convertParameterBO)));

		//检查转换类型的权限
		if(StringUtils.isBlank(booleanAuth) && !StringUtils.equals(booleanAuth, SysConstant.TRUE)) {
			return DefaultResult.failResult(EnumResultCode.E_PERMISSION);
		}

		//获取允许转换的次数
		Integer maxConvertTimes = (Integer)map.get(EnumAuthCode.PTS_CONVERT_NUM.getAuthCode());

		//转换次数检查
		IResult<EnumResultCode> resultCheckConvertTimes = checkConvertTimes(ipAddr, userID,maxConvertTimes);
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
	public IResult<EnumResultCode> checkConvertTimes(String ipAddr,Long userID,Integer maxConvertTimes) {
		Integer convertTimes;
		EnumResultCode resultCode;
		if(userID != null){
			resultCode = EnumResultCode.E_USER_CONVERT_NUM_ERROR;
			convertTimes = redisCacheManager.getScore(RedisConsts.ID_CONVERT_TIME_KEY,String.valueOf(userID)).intValue();
		}else {
			resultCode = EnumResultCode.E_VISITOR_CONVERT_NUM_ERROR;
			convertTimes = redisCacheManager.getScore(RedisConsts.IP_CONVERT_TIME_KEY,String.valueOf(ipAddr)).intValue();
		}
		//是否超过每日最大转换次数
		if (convertTimes >= maxConvertTimes) { 
			return DefaultResult.failResult(resultCode);
		} 
		return DefaultResult.successResult();
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
		Integer maxUploadSize = (Integer)map.get(EnumAuthCode.PTS_UPLOAD_SIZE.getAuthCode());
		
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


	
	public static void main(String[] args) {
		PtsAuthPO ptsAuthPO = new PtsAuthPO();
		
		
	}
	
	


}
