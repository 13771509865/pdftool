package com.neo.commons.cons;

import com.neo.commons.cons.constants.ConvertConsts;

/**
 * 转换类型枚举封装
 */
public enum EnumConvertType {
	// PS:每次添加新类型后，对应后面的方法也需要检查修改
	MS_HTML_SVG(0, "ms2htmlsvg", "文档格式到高清html的转换", "html", false, true, true, false),
	MS_HTML(1, "ms2html", "文档格式到html的转换", "html", false, false, true, false),
	MS_TXT(2, "ms2txt", "文档格式到txt的转换", "txt", false, false, false, false),
	MS_PDF(3, "ms2pdf", "文档格式到pdf的转换", "pdf", false, false, false, false),
	MS_GIF(4, "ms2gif", "文档格式到gif的转换", "gif", true, false, true, false),
	MS_PNG(5, "ms2png", "文档格式到png的转换", "png", true, false, true, false),
	MS_JPG(6, "ms2jpg", "文档格式到jpg的转换", "jpg", true, false, true, false),
	MS_TIFF(7, "ms2tiff", "文档格式到tiff的转换", "tiff", true, false, true, false),
	MS_BMP(8, "ms2bmp", "文档格式到bmp的转换", "bmp", true, false, true, false),

	PDF_GIF(9, "pdf2gif", "pdf文档格式到gif的转换", "gif", true, false, true, false),
	PDF_PNG(10, "pdf2png", "pdf文档格式到png的转换", "png", true, false, true, false),
	PDF_JPG(11, "pdf2jpg", "pdf文档格式到jpg的转换", "jpg", true, false, true, false),
	PDF_TIFF(12, "pdf2tiff", "pdf文档格式到tiff的转换", "tiff", true, false, true, false),
	PDF_BMP(13, "pdf2bmp", "pdf文档格式到bmp的转换", "bmp", true, false, true, false),
	PDF_HTML(14, "pdf2html", "pdf文档格式到html的转换", "html", false, true, true, false),

	HTML_MS(15, "html2ms", "html文档格式到微软文档格式的转换", "doc", false, false, false, false),
	MS_SVG_TEMP(16, "ms2svgtemp", "文档转换多个SVG返回分页加载页面", "svg", true, false, true, false), // 模版展示
	TIF_HTML(17, "tif2html", "tif文件转成html", "html", false, true, true, false),
	MS_SVG(18, "ms2svg", "文档转换多个SVG", "svg", true, false, true, false),
	ZIP_HTML_TEMP(19, "zip2html", "压缩文件到html的转换", "html", true, false, true, true), // 模版展示
	PDF_HTML_TEMP(20, "pdf2htmltemp", "PDF文件到html的转换", "html", false, false, false, true), // 模版展示
	OFD_HTML_TEMP(21, "ofd2htmltemp", "ofd文件到html的转换", "html", false, false, false, true), // 模版展示
	MS_MERGE(22, "ms2merge", "两个doc文档合并", "doc", false, false, false, false),
	PIC_HTML(23, "pic2html", "图片到html的转换", "html", false, false, true, false),
	PDF_TXT(24, "pdf2txt", "pdf文档格式到txt的转换", "txt", false, false, false, false),
	MS_SVG_PAGE(25, "ms2svgpage", "文档按页转换（高清版）", "html", true, false, true, false),
	MS_HTML_PAGE(26, "ms2htmlpage", "文档按页转换（标准版）", "html", true, false, true, false),
	GET_PAGECONUT_MS(27, "getPageMS", "只获取文档页码", "int", false, false, false, false),
	GET_PAGECONUT_PDF(28, "getPagePDF", "只获取pdf页码", "int", false, false, false, false),
	MS_OFD(29, "ms2ofd", "文档转ofd格式", "ofd", false, false, false, false), // 文档转ofd
	MS_HTMLOFPIC(30, "ms2htmlofpic", "文档转html(图片)", "html", false, false, true, false),
	PDF_MERGE(31, "pdf2merge", "多pdf文档合并", "pdf", false, false, false, false),
	PIC_PDF(32, "pic2pdf", "图片到pdf的转换", "pdf", false, false, false, false),
	MS_MS(33, "ms2ms", "文档到文档的转换", "doc", false, false, false, false),
	PDF_PDF(34, "pdf2pdf", "pdf到pdf的转换", "pdf", false, false, false, false),
	TIF_HTML_TEMP(35, "tif2htmltemp", "tif到html的转换(模板)", "html", false, false, false, true),
	PDF_WORD(36, "pdf2docx", "pdf到word的转换", "docx", false, false, false, false),

	// ----------------------bbn
	INDD_PDF(37, "indd2pdf", "indd到pdf的转换", "pdf", false, false, false, false),
	CAD_PDF(38, "cad2pdf", "cad到pdf的转换", "pdf", false, false, false, false),
	PSD_PDF(39, "psd2pdf", "psd到pdf的转换", "pdf", false, false, false, false),
	AI_PDF(40, "ai2pdf", "ai到pdf的转换", "pdf", false, false, false, false),
	CDR_PDF(41, "cdr2pdf", "cdr到pdf的转换", "pdf", false, false, false, false),
	ThreeD_DAE(99, "3d2dae", "3d到dae的转换", "dae", false, false, false, false),

	ALL_PDF_TEMP(42, "all2pdftemp", "其它格式转换到pdf格式预览(模板)", "pdf", false, false, false, false),
	MS_UOF(43, "ms2uof", "文档到uof的转换", "uof", false, false, false, false),
	IMAGE_IMAGE(44, "image2image", "图片到图片的转换", "jpg", false, false, false, false), // 默认生成的图片格式是jpg
	VIDEO_MP4(45, "video2mp4", "视频到mp4的转换", "mp4", false, false, false, false),
	OFD_STRING(46, "ofd2string", "获取ofd里面的内容", "string", false, false, false, false),
	IMAGE_OFD(47, "image2ofd", "图片转ofd", "ofd", false, false, false, false),
	OFD_OFD(48, "ofd2ofd", "ofd转ofd", "ofd", false, false, false, false),
	OFD_MERGE(49, "ofdtoofd", "合并ofd", "ofd", false, false, false, false),
	HTML_PDF(50, "html2pdf", "html到pdf", "pdf", false, false, false, false),
	EPUB_HTML_TEMP(51, "epub2htmltemp", "epub到html的转换(模板)", "html", false, false, false, true),
	DAE_HTML_TEMP(52, "dae2htmltemp", "dae到html的转换(模板)", "html", false, false, false, true);

	private final Integer value;
	private String type;
	private String info;
	private String ext;
	private boolean isMultiType;
	private boolean supportAsync;
	private boolean isNeedPack; // 下载时是否需要打包
	private boolean isTemplate; // 是否是模板形式

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

	public boolean isMultiType() {
		return isMultiType;
	}

	public boolean supportAsync() {
		return supportAsync;
	}

	public Boolean isNeedPack() {
		return isNeedPack;
	}

	public boolean isTemplate() {
		return isTemplate;
	}

	public void setTemplate(boolean template) {
		isTemplate = template;
	}

	private EnumConvertType(Integer value, String type, String info, String ext, boolean isMultiType,
			boolean supportAsync, boolean isNeedPack, boolean isTemplate) {
		this.value = value;
		this.type = type;
		this.info = info;
		this.ext = ext;
		this.isMultiType = isMultiType;
		this.supportAsync = supportAsync;
		this.isNeedPack = isNeedPack;
		this.isTemplate = isTemplate;
	}

	public static EnumConvertType getEnum(Integer value) {
		if (value != null) {
			for (EnumConvertType code : values()) {
				if (value == code.getValue())
					return code;
			}
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

	/**
	 * @description 是否是获取文档属性的
	 * @author zhoufeng
	 * @date 2019/5/10
	 */
	public static boolean isGetFileAttribute(Integer value) {
		EnumConvertType enumConvertType = getEnum(value);
		if (enumConvertType != null) {
			switch (enumConvertType) {
			case GET_PAGECONUT_MS:
			case GET_PAGECONUT_PDF:
			case OFD_STRING:
				return true;
			default:
				return false;
			}
		}
		return false;
	}

	// 是否是合并文件操作
	public static boolean isMerge(Integer value) {
		EnumConvertType enumConvertType = getEnum(value);
		if (enumConvertType != null) {
			switch (enumConvertType) {
			case PDF_MERGE:
			case MS_MERGE:
			case OFD_MERGE:
				return true;
			default:
				return false;
			}
		}
		return false;
	}

	public static boolean isOfdOperation(Integer value) {
		EnumConvertType enumConvertType = getEnum(value);
		if (enumConvertType != null) {
			switch (enumConvertType) {
			case OFD_HTML_TEMP:
			case OFD_STRING:
			case OFD_OFD:
			case OFD_MERGE:
				return true;
			default:
				return false;
			}
		}
		return false;
	}

	public static boolean isCheckDestFile(Integer value) {
		EnumConvertType enumConvertType = getEnum(value);
		if (enumConvertType != null) {
			switch (enumConvertType) {
			case GET_PAGECONUT_MS:
			case GET_PAGECONUT_PDF:
				return false;
			default:
				return true;
			}
		}
		return true;
	}

	public static boolean isPdfOperation(Integer value) {
		EnumConvertType enumConvertType = getEnum(value);
		if (enumConvertType != null) {
			switch (enumConvertType) {
			case PDF_GIF:
			case PDF_PNG:
			case PDF_JPG:
			case PDF_TIFF:
			case PDF_BMP:
			case PDF_HTML:
			case PDF_HTML_TEMP:
			case PDF_TXT:
			case GET_PAGECONUT_PDF:
			case PDF_MERGE:
			case PDF_PDF:
			case PDF_WORD:
			case MS_OFD:
				return true;
			default:
				return false;
			}
		}
		return false;
	}

	/**
	 * @return 是返回true
	 * @description 判断是否返回html
	 * @author zhoufeng
	 * @date 2019/3/14
	 */
	public static boolean isHtmlOperation(Integer value) {
		EnumConvertType enumConvertType = getEnum(value);
		if (enumConvertType != null) {
			switch (enumConvertType) {
			case MS_HTML_SVG:
			case MS_HTML:
			case PDF_HTML:
			case TIF_HTML:
			case PIC_HTML:
			case MS_HTMLOFPIC:
			case ZIP_HTML_TEMP:
			case PDF_HTML_TEMP:
			case OFD_HTML_TEMP:
			case TIF_HTML_TEMP:
			case EPUB_HTML_TEMP:
			case DAE_HTML_TEMP:
				return true;
			default:
				return false;
			}
		}
		return false;
	}

	/**
	 * 根据convertType判断用哪个jar
	 *
	 * @param value
	 * @return
	 * @author zhouf
	 */
//	public static String useWhichJar(Integer value) {
//		EnumConvertType enumConvertType = getEnum(value);
//		if (enumConvertType != null) {
//			switch (enumConvertType) {
//			case PDF_WORD:
//				return ConvertConsts.PDF2WORDJAR;
//			case VIDEO_MP4:
//			case AI_PDF:
//			case CAD_PDF:
//			case INDD_PDF:
//			case CDR_PDF:
//			case PSD_PDF:
//			case ThreeD_DAE:
//				return ConvertConsts.FcsOtherConvertJAR;
//			case TIF_HTML_TEMP:
//			case OFD_HTML_TEMP:
//			case EPUB_HTML_TEMP:
//			case PDF_HTML_TEMP:
//			case DAE_HTML_TEMP:
//			case ZIP_HTML_TEMP:
//				return ConvertConsts.FcsOtherActionJAR;
//			default:
//				return ConvertConsts.DCCJAR;
//			}
//		}
//		return null;
//	}

	public static boolean isMultiTarget(Integer value) {
		EnumConvertType enumConvertType = getEnum(value);
		if (enumConvertType != null) {
			return enumConvertType.isMultiType;
		}
		return false;
	}

	public static boolean isNeedPack(Integer value) {
		EnumConvertType enumConvertType = getEnum(value);
		if (enumConvertType != null) {
			return enumConvertType.isNeedPack;
		}
		return false;
	}

	// 是否支持后台转换
	public static boolean isSupportAsync(Integer value) {
		EnumConvertType enumConvertType = getEnum(value);
		if (enumConvertType != null) {
			return enumConvertType.supportAsync;
		}
		return false;
	}

	public static String getTargetExt(Integer value) {
		EnumConvertType enumConvertType = getEnum(value);
		if (enumConvertType != null) {
			return enumConvertType.ext;
		}
		return "";
	}

	/**
	 * 根据convertType,ext 纠正convertType
	 *
	 * @param value
	 * @param ext
	 * @return
	 * @author dh
	 */
	public static Integer fixConvertType(Integer value, String ext) {
		EnumConvertType enumConvertType = getEnum(value);
		if(enumConvertType==null) {
			enumConvertType=ALL_PDF_TEMP;
		}
		String type = getFileType(ext);
		if (type != null) {
			switch (type) {
			case "html": {
				switch (enumConvertType) {
				case HTML_MS:
				case HTML_PDF:
					return value;
				default:
					return HTML_PDF.getValue();
				}
			}

			case "ms": {
				switch (enumConvertType) {
				case MS_HTML_SVG:
				case MS_HTML:
				case MS_TXT:
				case MS_PDF:
				case MS_GIF:
				case MS_PNG:
				case MS_JPG:
				case MS_TIFF:
				case MS_BMP:
				case MS_SVG_TEMP:
				case MS_SVG:
				case MS_MERGE:
				case MS_SVG_PAGE:
				case MS_HTML_PAGE:
				case MS_OFD:
				case MS_HTMLOFPIC:
				case MS_MS:
				case GET_PAGECONUT_MS:
				case MS_UOF:
					return value;
				default:
					return MS_HTML.getValue();
				}
			}
			case "pdf": {
				if(isPdfOperation(value)) {
					return value;
				}
				else {
					return PDF_HTML.getValue();
				}
//				switch (enumConvertType) {
//				case PDF_GIF:
//				case PDF_PNG:
//				case PDF_JPG:
//				case PDF_TIFF:
//				case PDF_BMP:
//				case PDF_HTML:
//				case PDF_HTML_TEMP:
//				case PDF_TXT:
//				case PDF_MERGE:
//				case PDF_WORD:
//				case GET_PAGECONUT_PDF:
//					return value;
//				default:
//					return PDF_HTML.getValue();
//				}
			}

			case "tif":
			case "tiff": {
				switch (enumConvertType) {
				case TIF_HTML:
				case TIF_HTML_TEMP:
					return value;
				default:
					return TIF_HTML.getValue();
				}
			}

			case "ofd": {
				if(isOfdOperation(value)) {
					return value;
				}
				else {
					return OFD_HTML_TEMP.getValue();
				}
//				switch (enumConvertType) {
//				case OFD_HTML_TEMP:
//				case OFD_STRING:
//				case OFD_OFD:
//				case OFD_MERGE:
//					return value;
//				default:
//					return OFD_HTML_TEMP.getValue();
//				}
			}

			case "zip": {
				switch (enumConvertType) {
				case ZIP_HTML_TEMP:
					return value;
				default:
					return ZIP_HTML_TEMP.getValue();
				}
			}

			case "pic": {
				switch (enumConvertType) {
				case PIC_HTML:
				case PIC_PDF:
				case IMAGE_IMAGE:
				case IMAGE_OFD:
					return value;
				default:
					return PIC_HTML.getValue();
				}
			}

			case "mp4": {
				switch (enumConvertType) {
				case VIDEO_MP4:
					return value;
				default:
					return VIDEO_MP4.getValue();
				}
			}

			case "indd": {
				switch (enumConvertType) {
				case INDD_PDF:
					return value;
				default:
					return INDD_PDF.getValue();
				}
			}

			case "cad": {
				switch (enumConvertType) {
				case CAD_PDF:
					return value;
				default:
					return CAD_PDF.getValue();
				}
			}

			case "psd": {
				switch (enumConvertType) {
				case PSD_PDF:
					return value;
				default:
					return PSD_PDF.getValue();
				}
			}

			case "ai": {
				switch (enumConvertType) {
				case AI_PDF:
					return value;
				default:
					return AI_PDF.getValue();
				}
			}

			case "cdr": {
				switch (enumConvertType) {
				case CDR_PDF:
					return value;
				default:
					return CDR_PDF.getValue();
				}
			}

			case "threed": {
				switch (enumConvertType) {
				case ThreeD_DAE:
					return value;
				default:
					return ThreeD_DAE.getValue();
				}
			}

			case "epub": {
				switch (enumConvertType) {
				case EPUB_HTML_TEMP:
					return value;
				default:
					return EPUB_HTML_TEMP.getValue();
				}
			}

			case "dae": {
				switch (enumConvertType) {
				case DAE_HTML_TEMP:
					return value;
				default:
					return DAE_HTML_TEMP.getValue();
				}
			}

			default:
				return ALL_PDF_TEMP.getValue();
			}
		}

		return null;
	}

	public static String getFileType(String ext) {
		if (ext != null) {
			switch (ext.toLowerCase()) {
			case "doc":
			case "docx":
			case "xls":
			case "xlsx":
			case "ppt":
			case "pptx":
			case "txt":
			case "wps":
			case "wpt":
			case "dps":
			case "dpt":
			case "et":
			case "ett":
			case "xml":
				return "ms";

			case "cr2":
			case "arw":
			case "dng":
			case "nef":
			case "pef":
				return "cdr";

			case "wav":
			case "wma":
			case "ape":
				return "mp3";

			case "flv":
			case "f4v":
			case "mp4":
			case "m4v":
			case "webm":
			case "3gp":
			case "3gpp":
			case "wmv":
			case "avi":
			case "rm":
			case "rmvb":
			case "mkv":
			case "asf":
			case "mov":
			case "mpeg":
			case "swf":
			case "mpg":
			case "mts":
			case "m2ts":
			case "ogv":
				return "mp4";

			case "stl":
			case "x3d":
			case "fbx":
			case "obj":
			case "ply":
			case "3ds":
			case "abc":
				return "threed";

			case "tar":
			case "7z":
			case "gz":
			case "rar":
			case "zip":
				return "zip";

			case "bmp":
			case "jpg":
			case "png":
			case "gif":
			case "ico":
			case "jpeg":
			case "jpe":
				return "pic";

			case "dwg":
			case "dwt":
			case "dxf":
				return "cad";

			case "indd":
			case "indt":
			case "idml":
				return "indd";

			default:
				return ext.toLowerCase();
			}
		}
		return null;
	}

}
