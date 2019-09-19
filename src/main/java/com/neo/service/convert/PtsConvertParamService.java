package com.neo.service.convert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.constants.FcsParmConsts;
import com.neo.commons.cons.constants.PathConsts;
import com.neo.commons.cons.constants.SizeConsts;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.CheckMobileUtils;
import com.neo.commons.util.DateViewUtils;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.model.po.ConvertParameterPO;
import com.neo.model.po.FcsFileInfoPO;
import com.neo.model.po.PtsSummaryPO;


@Service("ptsConvertParamService")
public class PtsConvertParamService {
	
	@Autowired
	private ConfigProperty configProperty;
	
	
	
	/**
	 * 构建传递给fcs的参数条件
	 * @param convertPO
	 * @return
	 */
	public Map<String, Object> buildFcsMapParamPO(ConvertParameterPO convertPO){
		Map<String, Object> convertParamMap = JsonUtils.parseJSON2Map(convertPO.toString());
		Map<String, Object> fcsParamMap = new HashMap<>();
		fcsParamMap.putAll(convertParamMap);
		return fcsParamMap;
	}
	
	
	/**
	 * 构建ConvertParameterPO对象
	 * @param convertBO
	 */
	public ConvertParameterPO buildConvertParameterPO(ConvertParameterBO convertBO) {
		if(convertBO.getConvertTimeOut() == null) {
			convertBO.setConvertTimeOut(configProperty.getConvertTimeout());
		} 
		Map<String,Object> map = JsonUtils.parseJSON2Map(convertBO.toString());
		for(String param : FcsParmConsts.FCS_PARMS) {
			if(map.containsKey(param)) {
				List<String> list = (List<String>)map.get(param);
				if(!list.isEmpty() && list !=null) {
					map.put(param, StringUtils.join(list, ","));
				}
			}
		}
		return JsonUtils.map2obj(map, ConvertParameterPO.class);
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
		if(srcFileSize > 0 && srcFileSize <=SizeConsts.PTS_THREE_SIZE) {
			ptsSummaryPO.setZeroToThree(1);
		}
		if(srcFileSize >SizeConsts.PTS_THREE_SIZE  && srcFileSize <=SizeConsts.PTS_FIFTY_SIZE) {
			ptsSummaryPO.setThreeToFive(1);
		}
		if(srcFileSize >SizeConsts.PTS_FIFTY_SIZE  && srcFileSize <=SizeConsts.PTS_TEN_SIZE) {
			ptsSummaryPO.setFiveToTen(1);
		}
		if(srcFileSize >SizeConsts.PTS_TEN_SIZE  && srcFileSize <=SizeConsts.PTS_FIFTEEN_SIZE) {
			ptsSummaryPO.setTenToFifteen(1);
		}
		if(srcFileSize >SizeConsts.PTS_FIFTEEN_SIZE  && srcFileSize <=SizeConsts.PTS_TWENTY_SIZE) {
			ptsSummaryPO.setFifteenToTwenty(1);
		}
		if(srcFileSize >SizeConsts.PTS_TWENTY_SIZE  && srcFileSize <=SizeConsts.PTS_THIRTY_SIZE) {
			ptsSummaryPO.setTwentyToThirty(1);
		}
		if(srcFileSize >SizeConsts.PTS_THIRTY_SIZE  && srcFileSize <=SizeConsts.PTS_FOURTY_SIZE) {
			ptsSummaryPO.setThirtyToFourty(1);
		}
		if(srcFileSize >SizeConsts.PTS_FOURTY_SIZE  && srcFileSize <=SizeConsts.PTS_FIFTY_SIZE) {
			ptsSummaryPO.setFourtyToFifty(1);
		}
		if(srcFileSize >SizeConsts.PTS_FIFTY_SIZE  ) {
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
		
		
		Long userId = HttpUtils.getSessionUserID(request);
		if(userId ==null) {//如果登录ip_address就记录userId，没有登录就记录ip地址
			ptsSummaryPO.setIpAddress(HttpUtils.getIpAddr(request));
		}else {
			ptsSummaryPO.setIpAddress(String.valueOf(userId));
		}
		
		System.out.println(request.getParameter(PathConsts.MODULE));
		ptsSummaryPO.setModule(Integer.valueOf(request.getParameter(PathConsts.MODULE)));//区分模块
		
		ptsSummaryPO.setCreateDate(DateViewUtils.parseSimple(nowDate));//时间搞一搞
		ptsSummaryPO.setCreateTime(DateViewUtils.parseSimpleTime(nowTime));
		ptsSummaryPO.setModifiedDate(DateViewUtils.parseSimple(nowDate));
		ptsSummaryPO.setModifiedTime(DateViewUtils.parseSimpleTime(nowTime));
		
		return ptsSummaryPO;
	}
	

	
	
}
