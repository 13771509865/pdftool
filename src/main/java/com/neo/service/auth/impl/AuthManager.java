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
import com.neo.model.po.PtsAuthCorpPO;
import com.neo.model.po.PtsAuthPO;
import com.neo.model.qo.PtsAuthCorpQO;
import com.neo.model.qo.PtsAuthQO;
import com.neo.service.auth.IAuthService;
import com.neo.service.authCorp.IAuthCorpService;
import com.yozosoft.auth.client.security.UaaToken;
import com.yozosoft.auth.client.security.UaaUserRole;
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

	@Autowired
	private IAuthCorpService iAuthCorpService;



	/**
	 * 根据userId获取用户的权限对象
	 * @return
	 */
	public IResult<Map<String,Object>> getPermission(UaaToken uaaToken, String authCode) {
		try {
			//defaultMap里面是所有的默认权限
			Map<String,Object> defaultMap = getPermissionByConfig();
			String authValue = StringUtils.isBlank(authCode)?null:defaultMap.get(authCode).toString();

			//如果默认权益是-1，就不去查数据库了
			//authCode为空说明要拿所有的权益
			if(StringUtils.isBlank(authCode) || !StringUtils.equals(authValue,"-1")) {
				if(uaaToken.getRole().equals(UaaUserRole.CorpAdmin) || uaaToken.getRole().equals(UaaUserRole.CorpMember)){
					defaultMap = getCorpAuth(defaultMap,uaaToken.getCorpId(),authCode);
				}else {
					defaultMap = getUserAuth(defaultMap,uaaToken.getUserId(),authCode);
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
	public Map<String,Object> getPermissionByConfig() {
		Map<String,Object> defaultMap = new HashMap<>();
		String[] convertCode = config.getConvertModule().split(SysConstant.COMMA);
		for(String code : convertCode) {
			String authCode = EnumAuthCode.getAuthCode(Integer.valueOf(code));
			defaultMap.put(authCode, SysConstant.TRUE);
		}
		defaultMap.putAll(JsonUtils.parseJSON2Map(convertNumProperty));
		defaultMap.putAll(JsonUtils.parseJSON2Map(convertSizeProperty));
		return defaultMap;
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

		//文档转长图
		if(convertType == 69) {
			String ext = FilenameUtils.getExtension(convertParameterBO.getSrcRelativePath());
			switch (ext) {
				case "doc":
				case "docx":
					return DefaultResult.successResult(EnumAuthCode.WORD_LONG_PIC.getAuthCode());
				case "ppt":
				case "pptx":
					return DefaultResult.successResult(EnumAuthCode.PPT_LONG_PIC.getAuthCode());
			}
		}
		return DefaultResult.failResult();
	}


	/**
	 * 获取个人用户会员权益
	 * @return
	 */
	private Map<String,Object> getUserAuth(Map<String,Object> defaultMap,Long userID,String authCode){
		PtsAuthQO ptsAuthQO = new PtsAuthQO();
		ptsAuthQO.setAuthCode(authCode);
		ptsAuthQO.setStatus(EnumStatus.ENABLE.getValue());
		ptsAuthQO.setUserid(userID);
		List<PtsAuthPO> list = iAuthService.selectPtsAuthPO(ptsAuthQO);
		HttpUtils.getRequest().setAttribute(SysConstant.MEMBER_SHIP, list.isEmpty()?false:true);//取票时要用
		if(!list.isEmpty() && list.size()>0) {
			for(PtsAuthPO ptsAuthPO : list) {
				//权益值如果是-1，或者是资源包（次数）,赋值后直接跳出循环
				if(StringUtils.equals(ptsAuthPO.getAuthValue(),"-1") || StringUtils.equals(EnumAuthCode.PTS_CONVERT_NUM.getAuthCode(),ptsAuthPO.getAuthCode())){
					defaultMap.put(ptsAuthPO.getAuthCode(),Integer.valueOf(ptsAuthPO.getAuthValue()));
					continue;
				}
				Object deAuthValue = defaultMap.get(ptsAuthPO.getAuthCode());
				//deAuthValue不等于-1
				if(deAuthValue!=null && !StringUtils.equals(deAuthValue.toString(),"-1")){
					//size取最大值
					if(EnumAuthCode.isModuleSize(ptsAuthPO.getAuthCode()) && Integer.valueOf(deAuthValue.toString()) < Integer.valueOf(ptsAuthPO.getAuthValue())){
						defaultMap.put(ptsAuthPO.getAuthCode(),Integer.valueOf(ptsAuthPO.getAuthValue()));
					}
					//num累加
					if(EnumAuthCode.isModuleNum(ptsAuthPO.getAuthCode())){
						Integer num = Integer.valueOf(ptsAuthPO.getAuthValue()+Integer.valueOf(deAuthValue.toString()));
						defaultMap.put(ptsAuthPO.getAuthCode(),num);
					}
				}
			}
		}
		return defaultMap;
	}


	/**
	 * 获取企业账号会员权益
	 * @return
	 */
	private Map<String,Object> getCorpAuth(Map<String,Object> defaultMap,Long corpId,String authCode){
		PtsAuthCorpQO ptsAuthCorpQO = new PtsAuthCorpQO();
		ptsAuthCorpQO.setAuthCode(authCode);
		ptsAuthCorpQO.setStatus(EnumStatus.ENABLE.getValue());
		ptsAuthCorpQO.setCorpId(corpId);
		List<PtsAuthCorpPO> list = iAuthCorpService.selectPtsAuthCorpPO(ptsAuthCorpQO);
		HttpUtils.getRequest().setAttribute(SysConstant.MEMBER_SHIP, list.isEmpty()?false:true);//取票时要用
		if(!list.isEmpty() && list.size()>0) {
			for(PtsAuthCorpPO ptsAuthCorpPO : list) {
				//数据库里权益值如果是-1，或者是资源包（次数）,赋值后直接跳出循环
				if(StringUtils.equals(ptsAuthCorpPO.getAuthValue(),"-1") || StringUtils.equals(EnumAuthCode.PTS_CONVERT_NUM.getAuthCode(),ptsAuthCorpPO.getAuthCode())){
					defaultMap.put(ptsAuthCorpPO.getAuthCode(),Integer.valueOf(ptsAuthCorpPO.getAuthValue()));
					continue;
				}
				Object deAuthValue = defaultMap.get(ptsAuthCorpPO.getAuthCode());
				//deAuthValue不等于-1
				if(deAuthValue!=null && !StringUtils.equals(deAuthValue.toString(),"-1")){
					//size取最大值
					if(EnumAuthCode.isModuleSize(ptsAuthCorpPO.getAuthCode()) && Integer.valueOf(deAuthValue.toString()) < Integer.valueOf(ptsAuthCorpPO.getAuthValue())){
						defaultMap.put(ptsAuthCorpPO.getAuthCode(),Integer.valueOf(ptsAuthCorpPO.getAuthValue()));
					}
					//num累加
					if(EnumAuthCode.isModuleNum(ptsAuthCorpPO.getAuthCode())){
						Integer num = Integer.valueOf(ptsAuthCorpPO.getAuthValue()+Integer.valueOf(deAuthValue.toString()));
						defaultMap.put(ptsAuthCorpPO.getAuthCode(),num);
					}
				}
			}
		}
		return defaultMap;
	}










}
