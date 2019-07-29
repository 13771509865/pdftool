package com.neo.service.convert;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.constants.SizeConsts;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.CheckMobileUtils;
import com.neo.commons.util.DateViewUtils;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.model.po.FcsFileInfoPO;
import com.neo.model.po.PtsSummaryPO;


@Service("ptsConvertParamService")
public class PtsConvertParamService {
	
	@Autowired
	private ConfigProperty configProperty;
	
	
	/**
	 * convertBO放入map中
	 * @param convertBO
	 * @return
	 */
	public Map<String, Object> buildFcsMapParam(ConvertParameterBO convertBO){
		Map<String, Object> convertParamMap = JsonUtils.parseJSON2Map(convertBO.toString());
		Map<String, Object> fcsParamMap = new HashMap<>();
		fcsParamMap.putAll(convertParamMap);
		return fcsParamMap;
	}
	
	
	/**
	 * 添加转换超时时间
	 * @param convertBO
	 */
	public void buildConvertParameterBO(ConvertParameterBO convertBO) {
		if(convertBO.getConvertTimeOut() == null) {
			convertBO.setConvertTimeOut(configProperty.getConvertTimeout());
		}
	}
	
	
	
	/**
	 * 构建FcsFileInfoPO对象，用于insert
	 * @param fcsFileInfoBO
	 * @param request
	 * @return
	 */
	public FcsFileInfoPO buildFcsFileInfoParameter(FcsFileInfoBO fcsFileInfoBO,HttpServletRequest request) {
		FcsFileInfoPO fcsFileInfoPO = new FcsFileInfoPO();
		Long userID = HttpUtils.getSessionUserID(request);
		if(userID != null) {//只记录登录的用户
			fcsFileInfoPO.setIpAddress(HttpUtils.getIpAddr(request));
			fcsFileInfoPO.setUserID(userID);
			fcsFileInfoPO.setFileHash(fcsFileInfoBO.getFileHash());
			fcsFileInfoPO.setResultCode(fcsFileInfoBO.getCode());
			fcsFileInfoPO.setDestFileName(fcsFileInfoBO.getDestFileName());
			fcsFileInfoPO.setSrcFileName(fcsFileInfoBO.getSrcFileName());
			fcsFileInfoPO.setDestFileSize(fcsFileInfoBO.getDestFileSize());
			fcsFileInfoPO.setSrcFileSize(fcsFileInfoBO.getSrcFileSize());
			fcsFileInfoPO.setConvertType(fcsFileInfoBO.getConvertType());
			fcsFileInfoPO.setSrcStoragePath(fcsFileInfoBO.getSrcStoragePath());
			fcsFileInfoPO.setDestStoragePath(fcsFileInfoBO.getDestStoragePath());
			fcsFileInfoPO.setViewUrl(fcsFileInfoBO.getViewUrl());
		}
		return fcsFileInfoPO;
	}
	
	
	/**
	 * 构建PtsSummaryPO对象，用于数据统计
	 * @param fcsFileInfoBO
	 * @param request
	 * @return
	 */
	public PtsSummaryPO buildPtsSummaryPOParameter(FcsFileInfoBO fcsFileInfoBO,ConvertParameterBO convertBO,HttpServletRequest request) {
		PtsSummaryPO ptsSummaryPO = new PtsSummaryPO();
		Long srcFileSize = convertBO.getSrcFileSize();
		if(0>srcFileSize && srcFileSize <=SizeConsts.PTS_THREE_SIZE) {
			ptsSummaryPO.setZeroToThree(1);
		}
		if(SizeConsts.PTS_THREE_SIZE >srcFileSize && srcFileSize <=SizeConsts.PTS_FIFTY_SIZE) {
			ptsSummaryPO.setThreeToFive(1);
		}
		if(SizeConsts.PTS_FIFTY_SIZE >srcFileSize && srcFileSize <=SizeConsts.PTS_TEN_SIZE) {
			ptsSummaryPO.setFiveToTen(1);
		}
		if(SizeConsts.PTS_TEN_SIZE >srcFileSize && srcFileSize <=SizeConsts.PTS_FIFTEEN_SIZE) {
			ptsSummaryPO.setTenToFifteen(1);
		}
		if(SizeConsts.PTS_FIFTEEN_SIZE >srcFileSize && srcFileSize <=SizeConsts.PTS_TWENTY_SIZE) {
			ptsSummaryPO.setFifteenToTwenty(1);
		}
		if(SizeConsts.PTS_TWENTY_SIZE >srcFileSize && srcFileSize <=SizeConsts.PTS_THIRTY_SIZE) {
			ptsSummaryPO.setTwentyToThirty(1);
		}
		if(SizeConsts.PTS_THIRTY_SIZE >srcFileSize && srcFileSize <=SizeConsts.PTS_FOURTY_SIZE) {
			ptsSummaryPO.setThirtyToFourty(1);
		}
		if(SizeConsts.PTS_FOURTY_SIZE >srcFileSize && srcFileSize <=SizeConsts.PTS_FIFTY_SIZE) {
			ptsSummaryPO.setFourtyToFifty(1);
		}
		if(SizeConsts.PTS_FIFTY_SIZE >srcFileSize ) {
			ptsSummaryPO.setFiftyMore(1);
		}
		
		if(fcsFileInfoBO.getCode() == 0) {//判断转换是否成功
			ptsSummaryPO.setIsSuccess(0);
		}else {
			ptsSummaryPO.setIsSuccess(1);
		}
		
		
		if(CheckMobileUtils.checkIsMobile(request)) {//判断是手机端，还是PC端
			ptsSummaryPO.setAppType(0);
		}else {
			ptsSummaryPO.setAppType(1);
		}
		
		String nowDate = DateViewUtils.getNow();
		String nowTime = DateViewUtils.getNowTime();
		
		ptsSummaryPO.setIpAddress(HttpUtils.getIpAddr(request));//ip地址
		
		ptsSummaryPO.setCreateDate(DateViewUtils.parseSimple(nowDate));//时间搞一搞
		ptsSummaryPO.setCreateTime(DateViewUtils.parseSimpleTime(nowTime));
		ptsSummaryPO.setModifiedDate(DateViewUtils.parseSimple(nowDate));
		ptsSummaryPO.setModifiedTime(DateViewUtils.parseSimpleTime(nowTime));
		System.out.println("ptsSummaryPO对象构建完成："+ptsSummaryPO.toString());
		return ptsSummaryPO;
	}
	
	
	

}
