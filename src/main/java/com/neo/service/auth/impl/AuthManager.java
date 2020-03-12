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
import com.neo.commons.properties.ConvertNumProperty;
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

	@Autowired
	private ConvertNumProperty convertNumProperty;



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
//			Map<String,Object> permissionDtoAuthMap = new HashMap<>();
			if(userID !=null) {//登录用户或者会员
				List<PtsAuthPO> list = iAuthService.selectAuthByUserid(userID);
				if(!list.isEmpty() && list.size()>0) {//没有购买过会员
					PtsAuthPO ptsAuthPO = list.get(0);
					if(!DateViewUtils.isExpiredForDays(ptsAuthPO.getGmtExpire())) {//没有过期
						//会员注册的权限转map
						Map<String,Object> ptsAuthPOAuthMap = StrUtils.strToMap(ptsAuthPO.getAuth(), SysConstant.COMMA, SysConstant.COLON);
						permissionDtoAuthMap.putAll(ptsAuthPOAuthMap);
						return DefaultResult.successResult(getPermission(permissionDtoAuthMap));
					}
				}
			}
			//注册用户
			return DefaultResult.successResult(getPermission(getPermissionByConfig(permissionDtoAuthMap)));
		} catch (Exception e) {
			//aop会做处理
			SysLogUtils.error("解析用户权限失败,原因："+e.getMessage());
			return DefaultResult.failResult(EnumResultCode.E_GET_AUTH_ERROR.getInfo());
		}
	}


	/**
	 * 获取注册用户的权限
	 * @return
	 */
	public Map<String,Object> getPermissionByConfig(Map<String,Object> permissionDtoAuthMap) {

		String[] convertCode = config.getConvertModule().split(SysConstant.COMMA);
		for(String code : convertCode) {
			String authCode = EnumAuthCode.getAuthCode(Integer.valueOf(code));
			permissionDtoAuthMap.put(authCode, SysConstant.TRUE);
		}
		
		permissionDtoAuthMap.put(EnumAuthCode.PTS_UPLOAD_SIZE.getAuthCode(), config.getUploadSize());
		return permissionDtoAuthMap;
	}



	/**
	 * 获取每个模块的次数和大小权限
	 * @param permissionDtoAuthMap
	 * @return
	 */
	public Map<String,Object> getPermission(Map<String,Object> permissionDtoAuthMap) {
		Map<String,Object> numMap = JsonUtils.parseJSON2Map(convertNumProperty);

		//这个判断现在暂时这么搞
		//如果convertNum值不为空，则认为是会员
		if(permissionDtoAuthMap.get(EnumAuthCode.PTS_CONVERT_NUM.getAuthCode())!=null) {
			 for (Map.Entry<String, Object> numEntry : numMap.entrySet()) {
				 numEntry.setValue(permissionDtoAuthMap.get(EnumAuthCode.PTS_CONVERT_NUM.getAuthCode()));
			 }
		}
		permissionDtoAuthMap.putAll(numMap);
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
			if(convertParameterBO.getIsSignature() !=null && convertParameterBO.getIsSignature() == 1) {
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
