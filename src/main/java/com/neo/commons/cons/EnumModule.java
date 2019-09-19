package com.neo.commons.cons;

public enum EnumModule {
	
	
	PDF_WORD(1,"pdf2word"),
	WORD_PDF(2,"word2pdf"),
	PDF_PPT(3,"pdf2ppt"),
	PPT_PDF(4,"ppt2pdf"),
	PDF_EXCEL(5,"pdf2excel"),
	EXCEL_PDF(6,"excel2pdf"),
	PDF_IMG(7,"pdf2img"),
	PDF_HTML(8,"pdf2html"),
	PDF_MARK(9,"pdf2watermark"),
	PDF_SIGN(10,"pdf2sign"),
	PDF_MERGE(11,"pdf2merge"),
	PDF_SPLIT(12,"pdf2split");
	

	private Integer value;
	private String info;
	private EnumModule(Integer value, String info) {
		this.value = value;
		this.info = info;
	}

	public Integer getValue() {
		return value;
	}

	public String getInfo() {
		return info;
	}


	public static String getTypeInfo(Integer velue) {
        for (EnumModule statu : values()) {
			if(statu.getValue().equals(velue)){
				return statu.getInfo();
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
