package com.neo.service.auth.impl;

import com.neo.commons.cons.*;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.properties.ConvertNumProperty;
import com.neo.commons.properties.ConvertSizeProperty;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.po.PtsAuthPO;
import com.neo.model.qo.PtsAuthQO;
import com.neo.service.auth.IAuthService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("authManager")
public class AuthManager {

	@Autowired
	private IAuthService iAuthService;

	@Autowired
	private ConfigProperty config;

	@Autowired
	private ConvertNumProperty convertNumProperty;

	@Autowired
	private ConvertSizeProperty convertSizeProperty;



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

			//拿到配置文件里面，注册用户的转换大小的权限
			Map<String,Object> sizeMap = JsonUtils.parseJSON2Map(convertSizeProperty);

			//defaultMap里面是所有的默认权限
			defaultMap.putAll(numMap);
			defaultMap.putAll(sizeMap);
			String authValue = StringUtils.isBlank(authCode)?null:defaultMap.get(authCode).toString();

			//如果默认权益是-1，就不去查数据库了
			//authCode为空说明要拿所有的权益
			if(StringUtils.isBlank(authCode) || !StringUtils.equals(authValue,"-1")) {
				//根据authCode获取转换大小，数量权益
				List<PtsAuthPO> list = iAuthService.selectPtsAuthPO(new PtsAuthQO(userID,EnumStatus.ENABLE.getValue(),authCode));
				HttpUtils.getRequest().setAttribute(SysConstant.MEMBER_SHIP, list.isEmpty()?false:true);
				if(!list.isEmpty() && list.size()>0) {
					//获取会员的转换权益
					for(PtsAuthPO ptsAuthPO : list) {
						if(StringUtils.equals(ptsAuthPO.getAuthValue(),"-1")){//权益值如果是-1，负值后直接跳出循环
							defaultMap.put(ptsAuthPO.getAuthCode(),Integer.valueOf(ptsAuthPO.getAuthValue()));
							continue;
						}

						Object deAuthValue = defaultMap.get(ptsAuthPO.getAuthCode());
						//deAuthValue不等于-1
						//deAuthValue小于authValue
						if(deAuthValue!=null && !StringUtils.equals(deAuthValue.toString(),"-1") && Integer.valueOf(deAuthValue.toString()) < Integer.valueOf(ptsAuthPO.getAuthValue())){
							defaultMap.put(ptsAuthPO.getAuthCode(),Integer.valueOf(ptsAuthPO.getAuthValue()));
						}
					}
				}
			}
			return DefaultResult.successResult(defaultMap);
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
