package com.neo.service.auth;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.entity.OrderSpecsEntity;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.po.PtsAuthPO;
import com.neo.model.qo.PtsAuthQO;

public interface IAuthService {
	
	IResult<EnumResultCode> checkConvertNum(ConvertParameterBO convertParameterBO,Long userID);
	
	IResult<EnumResultCode> checkConvertTimes(Long userID,Integer maxConvertTimes,Integer module);
	
	IResult<EnumResultCode> checkUploadSize(Long userID,Long uploadSize,Integer module);
	
	Integer insertPtsAuthPO(List<PtsAuthPO> list);
	
	List<PtsAuthPO> selectPtsAuthPO(PtsAuthQO ptsAuthQO);
	
	Integer deletePtsAuth(Long userid);
	
	
}
