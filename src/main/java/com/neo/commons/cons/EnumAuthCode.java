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


	PDF_WORD(1,"PDF转Word","convert001","36","true","Pdf2word","convert001Num","convert001Size"),
	WORD_PDF(2,"Word转PDF","convert002","3","true","Word2pdf","convert002Num","convert002Size"),
	PDF_PPT(3,"PDF转PPT","convert003","80","true","Pdf2ppt","convert003Num","convert003Size"),
	PPT_PDF(4,"PPT转PDF","convert004","3","true","Ppt2pdf","convert004Num","convert004Size"),
	PDF_EXCEL(5,"PDF转Excel","convert005","81","true","Pdf2excel","convert005Num","convert005Size"),
	EXCEL_PDF(6,"Excel转PDF","convert006","3","true","Excel2pdf","convert006Num","convert006Size"),
	PDF_IMG(7,"PDF转图片","convert007","9,10,11,12,13","true","Pdf2pic","convert007Num","convert007Size"),
	PDF_HTML(8,"PDF转Html","convert008","14","true","Pdf2html","convert008Num","convert008Size"),
	PDF_MARK(9,"PDF加水印","convert009","34","true","Pdfaddstain","convert009Num","convert009Size"),
	PDF_SIGN(10,"PDF手写签批","convert010","14","true","Pdfaddsign","convert010Num","convert010Size"),
	PDF_MERGE(11,"PDF合并","convert011","31","true","Pdfmerge","convert011Num","convert011Size"),
	PDF_SPLIT(12,"PDF拆分","convert012","82","true","Pdfsplit","convert012Num","convert012Size"),
	OCR_IMG_TXT(13,"图片文字识别","convert013","83","true","Ocr","convert013Num","convert013Size"),
	PDF_ORC_WORD(14,"扫描件转Word","convert014","64","true","Pdftoocrword","convert014Num","convert014Size"),
	PIC_PDF(15,"图片转PDF","convert015","32","true","Pic2pdf","convert015Num","convert015Size"),

	/**
	 * PTS_CONVERT_NUM和PTS_UPLOAD_SIZE !!!!!上线后就可以删了!!!!!
	 */
	PTS_CONVERT_NUM(25,"转换数量", "convertNum",null,5,null,"convertNum","convertNum"),
	PTS_UPLOAD_SIZE(26,"文件大小","uploadSize",null,3,null,"uploadSize","uploadSize"),
	PTS_VALIDITY_TIME(27,"权益有效期","validityTime",null,1,null,"validityTime","validityTime");



	private Integer value;
	private String info;
	private String authCode;
	private String type;
	private Object defaultVaule;
	private String module;//与前端模块文件名一致，不可随意修改
	private String moduleNum;
	private String moduleSize;



	private EnumAuthCode(Integer value, String info,String authCode,String type,
			Object defaultVaule,String module,String moduleNum,String moduleSize) {
		this.value = value;
		this.info = info;
		this.authCode = authCode;
		this.type = type;
		this.defaultVaule = defaultVaule;
		this.module = module;
		this.moduleNum = moduleNum;
		this.moduleSize = moduleSize;
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



	public static String getModuleSize(String authCode) {
		for (EnumAuthCode code : values()) {
			if (code.getAuthCode() !=null && code.getAuthCode().equals(authCode)) {
				return code.getModuleSize();
			}
		}
		return null;
	}



	public static String getModuleSize(Integer value) {
		for (EnumAuthCode code : values()) {
			if(code.getValue().equals(value)){
				return code.getModuleSize();
			}
		}
		return null;
	}

	public static String getModuleByModuleSize(String moduleSize) {
		for (EnumAuthCode code : values()) {
			if (code.getModuleSize() !=null && code.getModuleSize().equals(moduleSize)) {
				return code.getModule();
			}
		}
		return null;
	}


	/**
	 * 订单预留时，判断预留订单中的特性，是否存在
	 * @param features
	 * @return
	 */
	public static Boolean existAuth(String features) {
		for (EnumAuthCode code : values()) {
			if(StringUtils.equals(code.getModuleNum(),features) || StringUtils.equals(code.getModuleSize(),features)){
				return true;
			}
		}
		return false;
	}


	
	//重复转换失败文件，判断authCode是否存在reConvertModule
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
