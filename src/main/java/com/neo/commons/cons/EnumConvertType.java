package com.neo.commons.cons;
/**
 * 转换类型枚举封装
 */
public enum EnumConvertType {
	//PS:每次添加新类型后，对应后面的方法也需要检查修改，isTemplet，isResultPage,supportAsync
	MS_HTML_SVG(0,"ms2htmlsvg","文档格式到高清html的转换","html",false,true),
	MS_HTML(1,"ms2html","文档格式到html的转换","html",false,false),
	MS_TXT(2,"ms2txt","文档格式到txt的转换","txt",false,false),
	MS_PDF(3,"ms2pdf","文档格式到pdf的转换","pdf",false,false),
	MS_GIF(4,"ms2gif","文档格式到gif的转换","gif",true,false),
	MS_PNG(5,"ms2png","文档格式到png的转换","png",true,false),
	MS_JPG(6,"ms2jpg","文档格式到jpg的转换","jpg",true,false),
	MS_TIFF(7,"ms2tiff","文档格式到tiff的转换","tiff",true,false),
	MS_BMP(8,"ms2bmp","文档格式到bmp的转换","bmp",true,false),
	
	PDF_GIF(9,"pdf2gif","pdf文档格式到gif的转换","gif",true,false),
	PDF_PNG(10,"pdf2png","pdf文档格式到png的转换","png",true,false),
	PDF_JPG(11,"pdf2jpg","pdf文档格式到jpg的转换","jpg",true,false),
	PDF_TIFF(12,"pdf2tiff","pdf文档格式到tiff的转换","tiff",true,false),
	PDF_BMP(13,"pdf2bmp","pdf文档格式到bmp的转换","bmp",true,false),
	PDF_HTML(14,"pdf2html","pdf文档格式到html的转换", "html",false,true),
	
	HTML_MS(15,"html2ms","html文档格式到微软文档格式的转换","ms",true,false),
	MS_SVG_TEMP(16,"ms2svgtemp","文档转换多个SVG返回分页加载页面", "svg",true,false),//模版展示
	TIF_HTML(17,"tif2html","tif文件转成html", "html",false,true),
	MS_SVG(18,"ms2svg","文档转换多个SVG","svg",true,false),
	ZIP_HTML_TEMP(19,"zip2html","压缩文件到html的转换","zip",true,false),//模版展示
	PDF_HTML_TEMP(20,"pdf2htmltemp","PDF文件到html的转换","html",true,false),//模版展示
	OFD_HTML_TEMP(21,"ofd2htmltemp","ofd文件到html的转换","html",true,false),//模版展示
	MS_MERGE(22,"ms2merge","两个doc文档合并","doc",false,false),//模版展示
	PIC_HTML(23,"pic2html","图片到html的转换","html",false,false),
	PDF_TXT(24,"pdf2txt","pdf文档格式到txt的转换", "txt",false,false),
	MS_SVG_PAGE(25,"ms2svgpage","文档按页转换（高清版）","html",true,false),
	MS_HTML_PAGE(26,"ms2htmlpage","文档按页转换（标准版）","html",true,false),
	GET_PAGECONUT_MS(27,"getPageMS","只获取文档页码","html",false,false),
	GET_PAGECONUT_PDF(28,"getPagePDF","只获取pdf页码","html",false,false),
	MS_OFD(29,"ms2ofd","文档转ofd格式","ofd",false,false),//文档转ofd
	MS_HTMLOFPIC(30,"ms2htmlofpic","文档转html(图片)","html",false,false),
	PDF_MERGE(31,"pdf2merge","多pdf文档合并","pdf",false,false),
	PIC_PDF(32,"pic2pdf","图片到pdf的转换","pdf",false,false),
	MS_MS(33,"ms2ms","文档到文档的转换","doc",false,false),
	PDF_PDF(34,"pdf2pdf","pad到pdf的转换","pdf",false,false),
	TIF_HTML_TEMP(35,"tif2htmltemp","tif到html的转换(模板)","html",true,false),
	PDF_WORD(36,"pdf2docx","pdf到word的转换","docx",false,false);
	
	private Integer value;
	private String type;
	private String info;
	private String ext;
	private boolean isImageType;
	private boolean supportAsync;
	public Integer getValue() {
		return value;
	}
	public String getType() {
		return type;
	}
	public String getInfo() {
		return info;
	}

	public String getExt() {
		return ext;
	}
	public boolean isImageType() {
		return isImageType;
	}
	public boolean supportAsync(){
		return supportAsync;
	}
	private EnumConvertType(Integer value, String type, String info, String ext,
			boolean isImageType,boolean supportAsync) {
		this.value = value;
		this.type = type;
		this.info = info;
		this.ext = ext;
		this.isImageType = isImageType;
		this.supportAsync = supportAsync;
	}
	public static EnumConvertType getEnum(int value) {
		for (EnumConvertType code : values()) {
			if (value == code.getValue())
				return code;
		}
		return null;
	}
	//是否是合并文件操作
	public static boolean isMerge(String type) {
		EnumConvertType enumConvertType = getEnumByType(type);
		if (enumConvertType != null) {
			switch (enumConvertType) {
				case PDF_MERGE :
				case MS_MERGE :
					return true;

				default :
					return false;
			}
		}
		return false;
	}
	public static boolean isTemplet(String type) {
		EnumConvertType enumConvertType = getEnumByType(type);
		if (enumConvertType != null) {
			switch (enumConvertType) {
				case OFD_HTML_TEMP :
				case MS_SVG_TEMP :
				case ZIP_HTML_TEMP :
				case PDF_HTML_TEMP :
				case TIF_HTML_TEMP:
				case MS_PDF:
					return true;
				default :
					return false;
			}
		}
		return false;
	}
	//是否需要通过dcc权限校验
	public static boolean isVerify(String type) {
		EnumConvertType enumConvertType = getEnumByType(type);
		if (enumConvertType != null) {
			switch (enumConvertType) {
				case OFD_HTML_TEMP :
				case PDF_HTML_TEMP :
				case TIF_HTML_TEMP :
					return true;
				default :
					return false;
			}
		}
		return false;
	}
	
	// 这几个 类型 转换后 返回的是 页码。。。
	public static boolean isResultPage(String type) {
		EnumConvertType enumConvertType = getEnumByType(type);
		if (enumConvertType != null) {
			switch (enumConvertType) {
				case MS_GIF:
				case MS_PNG:
				case MS_JPG:
				case MS_TIFF:
				case MS_BMP:
				case PDF_GIF:
				case PDF_PNG:
				case PDF_JPG:
				case PDF_TIFF:
				case PDF_BMP:
				case MS_SVG_TEMP:
				case MS_SVG:
				case MS_SVG_PAGE:
				case MS_HTML_PAGE:
				case GET_PAGECONUT_MS:
				case GET_PAGECONUT_PDF:
				return true;
				default :
					return false;
			}
		}
		return false;
	}
	//判断是否是转html的操作
	public static boolean isHtmlOperation(String type) {
		EnumConvertType enumConvertType = getEnumByType(type);
		if (enumConvertType != null) {
			switch (enumConvertType) {
				case MS_HTML_SVG:
				case MS_HTML:
				case PDF_HTML:
				case TIF_HTML:
				case ZIP_HTML_TEMP:
				case PDF_HTML_TEMP:
				case OFD_HTML_TEMP:
				case PIC_HTML:
				case MS_SVG_PAGE:
				case MS_HTML_PAGE:
				case MS_HTMLOFPIC:
				case TIF_HTML_TEMP:
				return true;
				default :
					return false;
			}
		}
		return false;
	}
    //包含pdf的操作，主要为了解决any2ofd接口
	public static boolean isIncludePdf(String type) {
		EnumConvertType enumConvertType = getEnumByType(type);
		if (enumConvertType != null) {
			switch (enumConvertType) {
				case PDF_GIF :
				case PDF_PNG :
				case PDF_JPG :
				case PDF_TIFF :
				case PDF_BMP :
				case PDF_HTML :
				case PDF_HTML_TEMP :
				case PDF_TXT :
				case GET_PAGECONUT_PDF:
				case PDF_MERGE:
				case MS_OFD:
				case PDF_PDF:
				case PDF_WORD:
					return true;
				default :
					return false;
			}
		}
		return false;		
	}
	
	public static boolean isPdfOperation(String type) {
		EnumConvertType enumConvertType = getEnumByType(type);
		if (enumConvertType != null) {
			switch (enumConvertType) {
				case PDF_GIF :
				case PDF_PNG :
				case PDF_JPG :
				case PDF_TIFF :
				case PDF_BMP :
				case PDF_HTML :
				case PDF_HTML_TEMP :
				case PDF_TXT :
				case GET_PAGECONUT_PDF:
				case PDF_MERGE:
				case PDF_PDF:
					return true;

				default :
					return false;
			}
		}
		return false;
	}
	
	/**
	 * 根据convertType判断用哪个jar
	 * @author zhouf
	 * @param value
	 * @return
	 */
	public static String useWhichJar (Integer value){
		EnumConvertType enumConvertType = getEnum(value);
		if(enumConvertType!=null) {
			switch(enumConvertType){
				case PDF_WORD:
					return SysConstant.PDF2WORDJAR;
				default :
					return SysConstant.DCCJAR;
			}
		}
		return null;
	}
	
	public static boolean isImageTarget(String type) {
		EnumConvertType enumConvertType = getEnumByType(type);
		if (enumConvertType != null) {
			return enumConvertType.isImageType;
		}
		return false;
	}
	
	//是否支持后台转换
	public static boolean isSupportAsync(String type) {
		EnumConvertType enumConvertType = getEnumByType(type);
		if (enumConvertType != null) {
			return enumConvertType.supportAsync;
		}
		return false;
	}
	
	public static String getTargetType(String type) {
		EnumConvertType enumConvertType = getEnumByType(type);
		if (enumConvertType != null) {
			return enumConvertType.ext;
		}
		return "";
	}
	//根据type取value
	public static Integer getTargetValue(String type){
		EnumConvertType enumConvertType = getEnumByType(type);
		if (enumConvertType != null) {
			return enumConvertType.value;
		}
		return null;
	}
	public static EnumConvertType getEnumByType(String type) {
		for (EnumConvertType code : values()) {
			if (type == code.getType())
				return code;
		}
		return null;
	}

}
