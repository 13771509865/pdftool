package com.neo.service.auth.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumAuthCode;
import com.neo.commons.cons.EnumLockCode;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.EnumStatus;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.PtsConsts;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.helper.PermissionHelper;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.properties.ConvertNumProperty;
import com.neo.commons.util.DateViewUtils;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.commons.util.StrUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.dto.PermissionDto;
import com.neo.model.po.PtsAuthPO;
import com.neo.model.qo.PtsAuthQO;
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
	public IResult<Map<String,Object>> getPermission(Long userID,String authCode) {
		try {
			Map<String,Object> defaultMap = new HashMap<>();

			//拿到配置文件里面，注册用户的权限
			defaultMap = getPermissionByConfig(defaultMap);

			//拿到配置文件里面，注册用户的转换次数的权限
			Map<String,Object> numMap = JsonUtils.parseJSON2Map(convertNumProperty);

			//defaultMap里面是所有的默认权限
			defaultMap.putAll(numMap);


			Map<String,Object> newAuthMap = new HashMap<>();
			if(userID !=null) {
				//根据authCode获取转换大小，数量权益
				List<PtsAuthPO> list = iAuthService.selectPtsAuthPO(new PtsAuthQO(userID,EnumStatus.ENABLE.getValue(),authCode));
				HttpUtils.getRequest().setAttribute(SysConstant.MEMBER_SHIP, list.isEmpty()?false:true);
				if(!list.isEmpty() && list.size()>0) {
					Integer num = 0;
					Integer size = 0;

					//获取会员的转换权益
					for(PtsAuthPO ptsAuthPO : list) {
						if(StringUtils.equals(ptsAuthPO.getAuthValue(), SysConstant.TRUE)) {
							newAuthMap.put(ptsAuthPO.getAuthCode(), ptsAuthPO.getAuthValue());
						}
						if(StringUtils.equals(ptsAuthPO.getAuthCode(), EnumAuthCode.PTS_CONVERT_NUM.getAuthCode())) {
							num = Integer.valueOf(ptsAuthPO.getAuthValue())>num?Integer.valueOf(ptsAuthPO.getAuthValue()):num;
						}
						if(StringUtils.equals(ptsAuthPO.getAuthCode(), EnumAuthCode.PTS_UPLOAD_SIZE.getAuthCode())) {
							size = Integer.valueOf(ptsAuthPO.getAuthValue())>size?Integer.valueOf(ptsAuthPO.getAuthValue()):size;
						}
					}

					//转换数量
					if(num != 0 ) {
						newAuthMap.put(EnumAuthCode.PTS_CONVERT_NUM.getAuthCode(), num);
					}
					//转换大小
					if(size != 0 ) {
						newAuthMap.put(EnumAuthCode.PTS_UPLOAD_SIZE.getAuthCode(), size);
					}
				}
			}
			return DefaultResult.successResult(getPermission(defaultMap,newAuthMap));
		} catch (Exception e) {
			HttpUtils.getRequest().setAttribute(SysConstant.MEMBER_SHIP, false);
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
	 * 获取会员每个模块的次数和大小权限
	 * @param permissionDtoAuthMap
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> getPermission(Map<String,Object> defaultMap,Map<String,Object> newAuthMap){

		//如果convertNum值不为空，则认为是会员
		if(newAuthMap.get(EnumAuthCode.PTS_CONVERT_NUM.getAuthCode())!=null) {
			Integer num = Integer.valueOf(newAuthMap.get(EnumAuthCode.PTS_CONVERT_NUM.getAuthCode()).toString());
			num = num>999?-1:num;

			//数据库auth的值循环
			for (Map.Entry<String, Object> permissionEntry : newAuthMap.entrySet()) {
				Object obj = permissionEntry.getValue();
				if(obj!=null && StringUtils.equals(obj.toString(), SysConstant.TRUE)) {

					//根据authCode拿moduleNum
					String mouldeNum =  EnumAuthCode.getModuleNum(permissionEntry.getKey());
					if(StringUtils.isNotBlank(mouldeNum)) {
						defaultMap.put(mouldeNum, num);
					}
				}
			}
		}

		//会员允许转换的大小
		if(newAuthMap.get(EnumAuthCode.PTS_UPLOAD_SIZE.getAuthCode())!=null) {
			defaultMap.put(EnumAuthCode.PTS_UPLOAD_SIZE.getAuthCode(), newAuthMap.get(EnumAuthCode.PTS_UPLOAD_SIZE.getAuthCode()));
		}
		return defaultMap;
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
