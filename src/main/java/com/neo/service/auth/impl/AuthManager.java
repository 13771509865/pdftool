package com.neo.service.auth.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumAuthCode;
import com.neo.commons.cons.EnumModule;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.DateViewUtils;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.po.PtsAuthPO;
import com.neo.service.auth.IAuthService;

@Service("authManager")
public class AuthManager {
	
	@Autowired
	private IAuthService iAuthService;
	
	@Autowired
	private ConfigProperty config;

	
	
	/**
	 * 根据userId获取用户的权限对象
	 * @param userID
	 * @return
	 */
	public PtsAuthPO getPtsAuthPO(Long userID) {
		PtsAuthPO ptsAuthPO = new PtsAuthPO();
		
		if(userID !=null) {//登录用户
			List<PtsAuthPO> list = iAuthService.selectAuthByUserid(userID);
			if(!list.isEmpty() && list.size()>0) {//没有购买过会员
				ptsAuthPO = list.get(0);
				if(!DateViewUtils.isExpiredForDays(ptsAuthPO.getGmtExpire())) {//没有过期
					return ptsAuthPO;
				}
			}
			ptsAuthPO.setAuth(appendAuth(true));
			
		}else {//游客
			ptsAuthPO.setAuth(appendAuth(false));
		}
		return ptsAuthPO;
	}

	
	
	/**
	 * 拼接注册用户或者游客的auth
	 * @param isLoginUser 是否注册/游客
	 * @return
	 */
	public String appendAuth(Boolean isLoginUser) {
		StringBuilder builder = new StringBuilder();
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
		
		//拼接模块
		for(int i=0;i<convertCode.length;i++) {
			builder.append(EnumModule.getAuthCode(Integer.valueOf(convertCode[i]))).append(SysConstant.COLON).append(SysConstant.TRUE).append(SysConstant.COMMA);
		}
		//拼接转换次数
		builder.append(EnumAuthCode.PTS_CONVERT_NUM.getInfo()).append(SysConstant.COLON).append(convertTimes).append(SysConstant.COMMA);
		
		//拼接上传文件大小
		builder.append(EnumAuthCode.PTS_UPLOAD_SIZE.getInfo()).append(SysConstant.COLON).append(convertSize);
		return builder.toString();
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
			return DefaultResult.successResult(EnumModule.PDF_IMG.getAuthCode());
		}
		
		//区分pdf签批和pdf2html
		//isSignature=1,表示签批
		if(convertType == 14) {
			if(convertParameterBO.getIsSignature() == 1) {
				return DefaultResult.successResult(EnumModule.PDF_SIGN.getAuthCode());
			}else {
				return DefaultResult.successResult(EnumModule.PDF_HTML.getAuthCode());
			}
		}
		
		//文档转pdf
		if(convertType == 3) {
			String ext = FilenameUtils.getExtension(convertParameterBO.getSrcRelativePath());
			switch (ext) {
			case "doc":
			case "docx":
				return DefaultResult.successResult(EnumModule.WORD_PDF.getAuthCode());
			case "ppt":
			case "pptx":
				return DefaultResult.successResult(EnumModule.PPT_PDF.getAuthCode());
			case "xls":
            case "xlsx":
            	return DefaultResult.successResult(EnumModule.EXCEL_PDF.getAuthCode());
			}
		}
		return DefaultResult.failResult();
	}
	

}
