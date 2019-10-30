package com.neo.commons.cons;

import lombok.Getter;

@Getter
public enum EnumModule {
	
	
	PDF_WORD(1,"pdf2word","convert001","36"),
	WORD_PDF(2,"word2pdf","convert002","3"),
	PDF_PPT(3,"pdf2ppt","convert003","80"),
	PPT_PDF(4,"ppt2pdf","convert004","3"),
	PDF_EXCEL(5,"pdf2excel","convert005","81"),
	EXCEL_PDF(6,"excel2pdf","convert006","3"),
	PDF_IMG(7,"pdf2img","convert007","9,10,11,12,13"),
	PDF_HTML(8,"pdf2html","convert008","14"),
	PDF_MARK(9,"pdf2watermark","convert009","34"),
	PDF_SIGN(10,"pdf2sign","convert010","14"),
	PDF_MERGE(11,"pdf2merge","convert011","31"),
	PDF_SPLIT(12,"pdf2split","convert012","82");
	

	private Integer value;
	private String info;
	private String authCode;
	private String type;
	
	private EnumModule(Integer value, String info,String authCode,String type) {
		this.value = value;
		this.info = info;
		this.authCode = authCode;
		this.type = type;
	}



	public static String getTypeInfo(Integer velue) {
        for (EnumModule statu : values()) {
			if(statu.getValue().equals(velue)){
				return statu.getInfo();
			}
		}
		return null;
	}
	
	
	public static String getAuthCode(Integer value) {
        for (EnumModule statu : values()) {
			if(statu.getValue().equals(value)){
				return statu.getAuthCode();
			}
		}
		return null;
	}
	
	
	public static String getAuthCode(String type) {
        for (EnumModule statu : values()) {
			if(statu.getType().equals(type)){
				return statu.getAuthCode();
			}
		}
		return null;
	}
	
	public static EnumModule getEnum(Integer value) {
		for (EnumModule code : values()) {
			if (value == code.getValue())
				return code;
		}
		return null;
	}

	
}
