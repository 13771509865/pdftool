package com.neo.commons.cons;


import lombok.Getter;

/**
 * pdf工具集，用户权益枚举类（包含目前线上，用户所有的权益）
 * @author xujun
 * @description
 * @create 2019年11月4日
 */
@Getter
public enum EnumAuthCode {


	PDF_WORD(1,"pdf2word","convert001","36","false"),
	WORD_PDF(2,"word2pdf","convert002","3","false"),
	PDF_PPT(3,"pdf2ppt","convert003","80","false"),
	PPT_PDF(4,"ppt2pdf","convert004","3","false"),
	PDF_EXCEL(5,"pdf2excel","convert005","81","false"),
	EXCEL_PDF(6,"excel2pdf","convert006","3","false"),
	PDF_IMG(7,"pdf2img","convert007","9,10,11,12,13","false"),
	PDF_HTML(8,"pdf2html","convert008","14","false"),
	PDF_MARK(9,"pdf2watermark","convert009","34","false"),
	PDF_SIGN(10,"pdf2sign","convert010","14","false"),
	PDF_MERGE(11,"pdf2merge","convert011","31","false"),
	PDF_SPLIT(12,"pdf2split","convert012","82","false"),
	OCR_IMG_TXT(13,"ocrImage2txt","convert013","83","false"),

	PTS_CONVERT_NUM(15,"转换数量", "convertNum",null,5), 
	PTS_UPLOAD_SIZE(16,"文件大小","uploadSize",null,2),
	PTS_VALIDITY_TIME(17,"权益有效期","validityTime",null,1);


	private Integer value;
	private String info;
	private String authCode;
	private String type;
	private Object defaultVaule;



	private EnumAuthCode(Integer value, String info,String authCode,String type,Object defaultVaule) {
		this.value = value;
		this.info = info;
		this.authCode = authCode;
		this.type = type;
		this.defaultVaule = defaultVaule;
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
			if(statu.getType().equals(type)){
				return statu.getAuthCode();
			}
		}
		return null;
	}


	public static Object getDefaultValue(String authCode) {
		for (EnumAuthCode statu : values()) {
			if(statu.getAuthCode().equals(authCode)){
				return statu.getDefaultVaule();
			}
		}
		return null;
	}


	public static EnumAuthCode getEnum(Integer value) {
		for (EnumAuthCode code : values()) {
			if (value == code.getValue())
				return code;
		}
		return null;
	}

	public static EnumAuthCode getEnum(String authCode) {
		for (EnumAuthCode code : values()) {
			if (code.getAuthCode().equals(authCode)) {
				return code;
			}
		}
		return null;
	}


	
	

}
