package com.neo.service.auth;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.po.PtsAuthPO;

public interface IAuthService {
	
	IResult<EnumResultCode> checkUserAuth(ConvertParameterBO convertParameterBO,Long userID);
	
	IResult<EnumResultCode> checkConvertTimes(Long userID,Integer maxConvertTimes);
	
	IResult<EnumResultCode> checkUploadSize(Long userID,Long uploadSize);
	
	Integer insertPtsAuthPO(PtsAuthPO ptsAuthPO);
	
	List<PtsAuthPO> selectAuthByUserid(Long userid);
	
	Integer updatePtsAuthPOByUserId(PtsAuthPO ptsAuthPO);
	
	Integer deletePtsAuth(PtsAuthPO ptsAuthPO);
	
}
