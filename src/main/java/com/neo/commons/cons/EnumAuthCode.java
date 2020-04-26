package com.neo.commons.cons;


import org.apache.commons.lang3.StringUtils;

import com.neo.commons.cons.constants.SysConstant;

import lombok.Getter;

/**
 * pdf工具集，用户权益枚举类（包含目前线上，用户所有的权益）
 * @author xujun
 * @description
 * @create 2019年11月4日
 */
@Getter
public enum EnumAuthCode {


	PDF_WORD(1,"pdf2word","convert001","36","true","Pdf2word","convert001Num"),
	WORD_PDF(2,"word2pdf","convert002","3","true","Word2pdf","convert002Num"),
	PDF_PPT(3,"pdf2ppt","convert003","80","true","Pdf2ppt","convert003Num"),
	PPT_PDF(4,"ppt2pdf","convert004","3","true","Ppt2pdf","convert004Num"),
	PDF_EXCEL(5,"pdf2excel","convert005","81","true","Pdf2excel","convert005Num"),
	EXCEL_PDF(6,"excel2pdf","convert006","3","true","Excel2pdf","convert006Num"),
	PDF_IMG(7,"pdf2img","convert007","9,10,11,12,13","true","Pdf2pic","convert007Num"),
	PDF_HTML(8,"pdf2html","convert008","14","true","Pdf2html","convert008Num"),
	PDF_MARK(9,"pdf2watermark","convert009","34","true","Pdfaddstain","convert009Num"),
	PDF_SIGN(10,"pdf2sign","convert010","14","true","Pdfaddsign","convert010Num"),
	PDF_MERGE(11,"pdf2merge","convert011","31","true","Pdfmerge","convert011Num"),
	PDF_SPLIT(12,"pdf2split","convert012","82","true","Pdfsplit","convert012Num"),
	OCR_IMG_TXT(13,"ocrImage2txt","convert013","83","true","Ocr","convert013Num"),
	PDF_ORC_WORD(14,"pdftoorcword","convert014","64","true","Pdftoocrword","convert014Num"),

	PTS_CONVERT_NUM(15,"转换数量", "convertNum",null,5,null,null), 
	PTS_UPLOAD_SIZE(16,"文件大小","uploadSize",null,3,null,null),
	PTS_VALIDITY_TIME(17,"权益有效期","validityTime",null,1,null,null);


	private Integer value;
	private String info;
	private String authCode;
	private String type;
	private Object defaultVaule;
	private String module;//与前端模块文件名一致，不可随意修改
	private String moduleNum;



	private EnumAuthCode(Integer value, String info,String authCode,String type,
			Object defaultVaule,String module,String moduleNum) {
		this.value = value;
		this.info = info;
		this.authCode = authCode;
		this.type = type;
		this.defaultVaule = defaultVaule;
		this.module = module;
		this.moduleNum = moduleNum;
	}



	public static String getTypeInfo(Integer velue) {
		for (EnumAuthCode statu : values()) {
			if(statu.getValue().equals(velue)){
				return statu.getInfo();
			}
		}
		return null;
	}


	public static String getAuthCode(Integer value) {
		for (EnumAuthCode statu : values()) {
			if(statu.getValue().equals(value)){
				return statu.getAuthCode();
			}
		}
		return null;
	}


	public static String getAuthCode(String type) {
		for (EnumAuthCode statu : values()) {
			if(statu.getType()!=null && statu.getType().equals(type)){
				return statu.getAuthCode();
			}
		}
		return null;
	}
	
	
	public static String getAuthCodeByModuleNum(String moduleNum) {
		for (EnumAuthCode statu : values()) {
			if(statu.getModuleNum()!=null && statu.getModuleNum().equals(moduleNum)){
				return statu.getAuthCode();
			}
		}
		return null;
	}


	public static Object getDefaultValue(String authCode) {
		for (EnumAuthCode statu : values()) {
			if(statu.getAuthCode()!=null && statu.getAuthCode().equals(authCode)){
				return statu.getDefaultVaule();
			}
		}
		return null;
	}
	
	
	public static Integer getValue(String authCode) {
		for (EnumAuthCode code : values()) {
			if(code.getAuthCode()!=null && code.getAuthCode().equals(authCode)){
				return code.getValue();
			}
		}
		return null;
	}


	public static EnumAuthCode getEnum(Integer value) {
		for (EnumAuthCode code : values()) {
			if (value == code.getValue()) {
				return code;
			}
		}
		return null;
	}

	public static EnumAuthCode getEnum(String authCode) {
		for (EnumAuthCode code : values()) {
			if (code.getAuthCode()!=null && code.getAuthCode().equals(authCode)) {
				return code;
			}
		}
		return null;
	}
	
	
	
	public static String getModule(Integer value) {
		for (EnumAuthCode code : values()) {
			if(code.getValue().equals(value)){
				return code.getModule();
			}
		}
		return null;
	}
	
	
	
	public static String getModule(String authCode) {
		for (EnumAuthCode code : values()) {
			if (code.getAuthCode() !=null && code.getAuthCode().equals(authCode)) {
				return code.getModule();
			}
		}
		return null;
	}


	public static String getModuleNum(String authCode) {
		for (EnumAuthCode code : values()) {
			if (code.getAuthCode() !=null && code.getAuthCode().equals(authCode)) {
				return code.getModuleNum();
			}
		}
		return null;
	}
	
	
	
	public static String getModuleByModuleNum(String moduleNum) {
		for (EnumAuthCode code : values()) {
			if (code.getModuleNum() !=null && code.getModuleNum().equals(moduleNum)) {
				return code.getModule();
			}
		}
		return null;
	}
	
	
	public static String getModuleNum(Integer value) {
		for (EnumAuthCode code : values()) {
			if(code.getValue().equals(value)){
				return code.getModuleNum();
			}
		}
		return null;
	}
	
	
	//判断authCode是否存在reConvertModule
	//reConvertModule对应配置文件中的reConvertModule
	public static Boolean existReconvertModule(String authCode,String reConvertModule) {
		String[] modules = reConvertModule.split(SysConstant.COMMA);
		for(String m : modules) {
			if(StringUtils.equals(authCode, getAuthCode(Integer.valueOf(m)))) {
				return true;
			}
		}
		return false;
	}
	

}
