package com.neo.service.auth;

import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.po.PtsAuthPO;
import com.neo.model.qo.PtsAuthQO;

import java.util.List;

public interface IAuthService {
	
	IResult<EnumResultCode> checkConvertNum(ConvertParameterBO convertParameterBO,Long userID);
	
	IResult<EnumResultCode> checkConvertTimes(Long userID,Integer maxConvertTimes,Integer module,Integer rpConvertNum);
	
	IResult<EnumResultCode> checkUploadSize(Long userID,Long uploadSize,Integer module);

	IResult<EnumResultCode> checkConvertNumByDay(Long userID);
	
	Integer insertPtsAuthPO(List<PtsAuthPO> list);
	
	List<PtsAuthPO> selectPtsAuthPO(PtsAuthQO ptsAuthQO);
	
	Integer deletePtsAuth(Long userid);
	
}
