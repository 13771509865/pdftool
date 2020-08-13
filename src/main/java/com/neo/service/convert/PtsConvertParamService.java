package com.neo.service.convert;

import com.neo.commons.cons.EnumConvertType;
import com.neo.commons.cons.EnumRPTCode;
import com.neo.commons.cons.EnumStatus;
import com.neo.commons.cons.constants.*;
import com.neo.commons.cons.entity.ConvertEntity;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.*;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.model.po.ConvertParameterPO;
import com.neo.model.po.FcsFileInfoPO;
import com.neo.model.po.PtsSummaryPO;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("ptsConvertParamService")
public class PtsConvertParamService {

	@Autowired
	private ConfigProperty configProperty;

	@Autowired
	private PtsProperty ptsProperty;


	/**
	 * 构建ConvertEntity对象
	 * @param convertBO
	 * @param request
	 * @return
	 */
	public ConvertEntity buildConvertEntity(ConvertParameterBO convertBO,HttpServletRequest request) {
		ConvertEntity convertEntity = new ConvertEntity();
		String cookie = request.getHeader(UaaConsts.COOKIE);//文件上传给优云要用到
		Boolean isMember = request.getAttribute(SysConstant.MEMBER_SHIP)==null?false:(Boolean)request.getAttribute(SysConstant.MEMBER_SHIP);
		String fileHash = UUIDHelper.generateUUID();
		Long userId = HttpUtils.getSessionUserID(request);
		Integer module =request.getParameter(PtsConsts.SECTION)==null?null:Integer.valueOf(request.getParameter(PtsConsts.SECTION));
		Boolean isMobile = CheckMobileUtils.checkIsMobile(request);
		Boolean isRPT = (Boolean) request.getAttribute(SysConstant.IS_RPT);
		
		convertEntity.setCookie(cookie);
		convertEntity.setFileHash(fileHash);
		convertEntity.setIpAddress(HttpUtils.getIpAddr(request));
		convertEntity.setIsMember(isMember);
		convertEntity.setUserId(userId);
		convertEntity.setModule(module);
		convertEntity.setIsMobile(isMobile);
		convertEntity.setIsRPT(isRPT);
		return convertEntity;
	}
	
	
	
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
	public ConvertParameterPO buildConvertParameterPO(ConvertParameterBO convertBO,Long userId) {
		if(convertBO.getConvertTimeOut() == null) {
			//后门用户设置超时时间为半个小时
			if(isUnlimitedUsers(userId)){
				convertBO.setConvertTimeOut(TimeConsts.SECOND_OF_HALFHOUR);
			}else{
				convertBO.setConvertTimeOut(configProperty.getConvertTimeout());
			}
		}

		//PDF转图片，清晰度做特殊处理，zoom值暂定为3
		if(isPic(convertBO)){
			convertBO.setZoom(3f);
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
	 * 判断是否是PDF转图片
	 * @param convertBO
	 * @return
	 */
	public Boolean isPic(ConvertParameterBO convertBO){
		switch (EnumConvertType.getEnum(convertBO.getConvertType())){
			case PDF_GIF:
			case PDF_PNG:
			case PDF_JPG:
			case PDF_TIFF:
			case PDF_BMP:
				return true;
		}
			return false;
	}


	/**
	 * 构建FcsFileInfoPO对象
	 * @param convertBO
	 * @param fcsFileInfoBO
	 * @param convertEntity
	 * @return
	 */
	public FcsFileInfoPO buildFcsFileInfoParameter(ConvertParameterBO convertBO,FcsFileInfoBO fcsFileInfoBO,ConvertEntity convertEntity) {
		FcsFileInfoPO fcsFileInfoPO = new FcsFileInfoPO();
		fcsFileInfoPO.setIpAddress(convertEntity.getIpAddress());
		fcsFileInfoPO.setUserID(convertEntity.getUserId());
		fcsFileInfoPO.setFileHash(fcsFileInfoBO.getFileHash());
		fcsFileInfoPO.setResultCode(fcsFileInfoBO.getCode());
		fcsFileInfoPO.setSrcFileName(fcsFileInfoBO.getSrcFileName());
		fcsFileInfoPO.setDestFileSize(fcsFileInfoBO.getDestFileSize());
		fcsFileInfoPO.setSrcFileSize(fcsFileInfoBO.getSrcFileSize());
		fcsFileInfoPO.setConvertType(fcsFileInfoBO.getConvertType());
		fcsFileInfoPO.setSrcStoragePath(fcsFileInfoBO.getSrcStoragePath());
		fcsFileInfoPO.setDestStoragePath(fcsFileInfoBO.getDestStoragePath());
		fcsFileInfoPO.setStatus(EnumStatus.ENABLE.getValue());
		fcsFileInfoPO.setModule(convertEntity.getModule());
		fcsFileInfoPO.setIsRPT(convertEntity.getIsRPT()? EnumRPTCode.IS_RPT.getValue():EnumRPTCode.UN_RPT.getValue());

		//手写签批，做特殊处理DestFileName，需要保存上传的源文件
		//viewUrl需要修改成download
		if(convertBO.getConvertType() == 14 && convertBO.getIsSignature()!=null && convertBO.getIsSignature() ==1) {
			fcsFileInfoPO.setDestFileName(fcsFileInfoBO.getSrcFileName());

			String downloadUrl = StringUtils.replace(fcsFileInfoBO.getViewUrl(), PtsConsts.VIEW_PREVIEW, PtsConsts.VIEW_DOWNLOAD);
			fcsFileInfoPO.setViewUrl(downloadUrl);
		}else {
			fcsFileInfoPO.setDestFileName(fcsFileInfoBO.getDestFileName());
			fcsFileInfoPO.setViewUrl(fcsFileInfoBO.getViewUrl());
		}

		return fcsFileInfoPO;
	}


	/**
	 * 构建PtsSummaryPO对象，用于数据统计
	 * @param fcsFileInfoBO
	 * @return
	 */
	public PtsSummaryPO buildPtsSummaryPOParameter(FcsFileInfoBO fcsFileInfoBO,ConvertParameterBO convertBO,ConvertEntity convertEntity) {
		PtsSummaryPO ptsSummaryPO = new PtsSummaryPO();
		Long srcFileSize = convertBO.getSrcFileSize();
		if(srcFileSize >= 0 && srcFileSize <=SizeConsts.PTS_TWO_SIZE) {
			ptsSummaryPO.setZeroToTwo(1);
		}
		if(srcFileSize >SizeConsts.PTS_TWO_SIZE  && srcFileSize <=SizeConsts.PTS_FIVE_SIZE) {
			ptsSummaryPO.setTwoToFive(1);
		}
		if(srcFileSize >SizeConsts.PTS_FIVE_SIZE  && srcFileSize <=SizeConsts.PTS_TEN_SIZE) {
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

		if(convertEntity.getIsMobile()) {//判断是手机端，还是PC端
			ptsSummaryPO.setAppType(0);
		}else {
			ptsSummaryPO.setAppType(1);
		}


		String nowDate = DateViewUtils.getNow();
		String nowTime = DateViewUtils.getNowTime();


		
		if(convertEntity.getUserId() ==null) {//如果登录ip_address就记录userId，没有登录就记录ip地址
			ptsSummaryPO.setIpAddress(convertEntity.getIpAddress());
		}else {
			ptsSummaryPO.setIpAddress(String.valueOf(convertEntity.getUserId()));
		}

		//区分模块
		if(convertEntity.getModule() != null ) {
			ptsSummaryPO.setModule(convertEntity.getModule());
		}
		ptsSummaryPO.setCreateDate(DateViewUtils.parseSimple(nowDate));//时间搞一搞
		ptsSummaryPO.setCreateTime(DateViewUtils.parseSimpleTime(nowTime));
		ptsSummaryPO.setModifiedDate(DateViewUtils.parseSimple(nowDate));
		ptsSummaryPO.setModifiedTime(DateViewUtils.parseSimpleTime(nowTime));

		return ptsSummaryPO;
	}


	public String getFileHash(ConvertParameterBO convertBO) {
		String srcRelative = convertBO.getSrcRelativePath();
		if (!StringUtils.isEmpty(srcRelative)) {
			String srcRoot = ptsProperty.getFcs_srcfile_dir();
			convertBO.setSrcPath(srcRoot + File.separator + srcRelative);
			if (StringUtils.isEmpty(convertBO.getSrcFileName())) {
				convertBO.setSrcFileName(FilenameUtils.getName(srcRelative));
			}
			File srcFile = new File(srcRoot, srcRelative);
			if (srcFile.isFile()) {
				convertBO.setSrcFileSize(srcFile.length());
			}
		}
		return GetConvertMd5Utils.getNotNullConvertMd5(convertBO);
	}


	/**
	 * 判断该用户是否是后门用户
	 * @param userId
	 * @return
	 */
	public Boolean isUnlimitedUsers(Long userId){
		String[] unlimitedUsers = configProperty.getUnlimitedUsers().split(SysConstant.COMMA);
		for(String unlimitedUser : unlimitedUsers){
			if(StringUtils.equals(unlimitedUser,String.valueOf(userId))){
				return true;
			}
		}
		return false;
	}


	public static void main(String[] args) {
		PtsConvertParamService p = new PtsConvertParamService();
		ConvertParameterBO co = new ConvertParameterBO();
		co.setConvertType(6);
		System.out.println(UUIDHelper.generateUUID());
	}
}
