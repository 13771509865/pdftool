package com.neo.service.auth.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumAuthCode;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.helper.PermissionHelper;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.DateViewUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.commons.util.StrUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.dto.PermissionDto;
import com.neo.model.po.PtsAuthPO;
import com.neo.service.auth.IAuthService;

@Service("authManager")
public class AuthManager {

	@Autowired
	private IAuthService iAuthService;

	@Autowired
	private ConfigProperty config;

	@Autowired
	private PermissionHelper permissionHelper;



	/**
	 * 根据userId获取用户的权限对象
	 * @param userID
	 * @return
	 */
	public IResult<Map<String,Object>> getPermission(Long userID) {
		try {
			PermissionDto permissionDto = permissionHelper.buildDefaultPermission();
			//默认权限map
			Map<String,Object> permissionDtoAuthMap = JsonUtils.parseJSON2Map(permissionDto);
			if(userID !=null) {//登录用户或者会员
				List<PtsAuthPO> list = iAuthService.selectAuthByUserid(userID);
				if(!list.isEmpty() && list.size()>0) {//没有购买过会员
					PtsAuthPO ptsAuthPO = list.get(0);
					if(!DateViewUtils.isExpiredForDays(ptsAuthPO.getGmtExpire())) {//没有过期
						//会员注册的权限转map
						Map<String,Object> ptsAuthPOAuthMap = StrUtils.strToMap(ptsAuthPO.getAuth(), SysConstant.COMMA, SysConstant.COLON);
						permissionDtoAuthMap.putAll(ptsAuthPOAuthMap);
						return DefaultResult.successResult(permissionDtoAuthMap);
					}
				}
				//注册用户
				return DefaultResult.successResult(getPermissionByConfig(true,permissionDtoAuthMap));
			}else {//游客
				return DefaultResult.successResult(getPermissionByConfig(false,permissionDtoAuthMap));
			}
		} catch (Exception e) {
			SysLogUtils.error("解析用户权限失败,原因："+e.getMessage());
			return DefaultResult.failResult(EnumResultCode.E_GET_AUTH_ERROR.getInfo());
		}
	}


	/**
	 * 获取注册用户或者游客的权限
	 * @param isLoginUser 是否注册/游客
	 * @return
	 */
	public Map<String,Object> getPermissionByConfig(Boolean isLoginUser,Map<String,Object> permissionDtoAuthMap) {
		String[] convertCode;
		Integer convertTimes;
		Integer convertSize;

		if(isLoginUser) {//注册用户
			convertCode = config.getMConvertModule().split(SysConstant.COMMA);
			convertTimes = config.getMConvertTimes();
			convertSize = config.getMUploadSize();
		}else {//游客
			convertCode = config.getVConvertModule().split(SysConstant.COMMA);
			convertTimes = config.getVConvertTimes();
			convertSize = config.getVUploadSize();
		}
		for(String code : convertCode) {
			String authCode = EnumAuthCode.getAuthCode(Integer.valueOf(code));
			permissionDtoAuthMap.put(authCode, SysConstant.TRUE);
		}

		permissionDtoAuthMap.put(EnumAuthCode.PTS_CONVERT_NUM.getAuthCode(), convertTimes);
		permissionDtoAuthMap.put(EnumAuthCode.PTS_UPLOAD_SIZE.getAuthCode(), convertSize);
		return permissionDtoAuthMap;
	}




	/**
	 * convertType特殊处理，返回
	 * @param convertParameterBO
	 * @return
	 */
	public IResult<String> authCodeHandle(ConvertParameterBO convertParameterBO){

		Integer convertType = convertParameterBO.getConvertType();
		//pdf转图片
		if(convertType == 9 || convertType==10 || convertType==11 ||convertType==12 ||convertType ==13) {
			return DefaultResult.successResult(EnumAuthCode.PDF_IMG.getAuthCode());
		}

		//区分pdf签批和pdf2html
		//isSignature=1,表示签批
		if(convertType == 14) {
			if(convertParameterBO.getIsSignature() == 1) {
				return DefaultResult.successResult(EnumAuthCode.PDF_SIGN.getAuthCode());
			}else {
				return DefaultResult.successResult(EnumAuthCode.PDF_HTML.getAuthCode());
			}
		}

		//文档转pdf
		if(convertType == 3) {
			String ext = FilenameUtils.getExtension(convertParameterBO.getSrcRelativePath());
			switch (ext) {
			case "doc":
			case "docx":
				return DefaultResult.successResult(EnumAuthCode.WORD_PDF.getAuthCode());
			case "ppt":
			case "pptx":
				return DefaultResult.successResult(EnumAuthCode.PPT_PDF.getAuthCode());
			case "xls":
			case "xlsx":
				return DefaultResult.successResult(EnumAuthCode.EXCEL_PDF.getAuthCode());
			}
		}
		return DefaultResult.failResult();
	}


	/**
	 * 根据用户请求的convertType，获取对应的authCode
	 * @param convertParameterBO
	 * @return
	 */
	public String getAuthCode(ConvertParameterBO convertParameterBO) {
		IResult<String> result = authCodeHandle(convertParameterBO);
		Integer convertType = convertParameterBO.getConvertType();
		String authCode;
		if(result.isSuccess()) {
			authCode = result.getData();
		}else {
			authCode = EnumAuthCode.getAuthCode(String.valueOf(convertType));
		}
		return authCode;
	}







}
