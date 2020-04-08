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
	
	IResult<EnumResultCode> checkUserAuth(ConvertParameterBO convertParameterBO,Long userID);
	
	IResult<EnumResultCode> checkConvertTimes(Long userID,Integer maxConvertTimes,Integer module);
	
	IResult<EnumResultCode> checkUploadSize(Long userID,Long uploadSize);
	
	Boolean insertPtsAuthPO(OrderSpecsEntity orderSpecsEntity ,Long userId,String productId,Integer priority );
	
	Boolean updatePtsAuthPO(String auth,Long userId,String productId,Date newExpireDate,Integer status,Integer priority);
	
	Integer insertPtsAuthPO(PtsAuthPO ptsAuthPO);
	
	List<PtsAuthPO> selectPtsAuthPO(PtsAuthQO ptsAuthQO);
	
	Integer updatePtsAuthPO(PtsAuthPO ptsAuthPO);
	
	Integer deletePtsAuth(PtsAuthPO ptsAuthPO);
	
	Integer updatePtsAuthPOByPriority(Integer validityTime,String unitType,Long userid,Integer priority);
	
	List<PtsAuthPO> selectPtsAuthPOByPriority(Long userid, Integer status);
	
}
