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

	PTS_CONVERT_NUM(0,"所有模块转换数量", "convertNum",null,5,"convertNum","convertNum","convertNum",null),
	PDF_WORD(1,"PDF转Word","convert001","36","true","Pdf2word","convert001Num","convert001Size",null),
	WORD_PDF(2,"Word转PDF","convert002","3","true","Word2pdf","convert002Num","convert002Size",null),
	PDF_PPT(3,"PDF转PPT","convert003","80","true","Pdf2ppt","convert003Num","convert003Size",null),
	PPT_PDF(4,"PPT转PDF","convert004","3","true","Ppt2pdf","convert004Num","convert004Size",null),
	PDF_EXCEL(5,"PDF转Excel","convert005","81","true","Pdf2excel","convert005Num","convert005Size",null),
	EXCEL_PDF(6,"Excel转PDF","convert006","3","true","Excel2pdf","convert006Num","convert006Size",null),
	PDF_IMG(7,"PDF转图片","convert007","9,10,11,12,13","true","Pdf2pic","convert007Num","convert007Size",null),
	PDF_HTML(8,"PDF转Html","convert008","14","true","Pdf2html","convert008Num","convert008Size",null),
	PDF_MARK(9,"PDF加水印","convert009","34","true","Pdfaddstain","convert009Num","convert009Size","_已加水印"),
	PDF_SIGN(10,"PDF手写签批","convert010","14","true","Pdfaddsign","convert010Num","convert010Size","_已签批"),
	PDF_MERGE(11,"PDF合并","convert011","31","true","Pdfmerge","convert011Num","convert011Size",null),
	PDF_SPLIT(12,"PDF拆分","convert012","82","true","Pdfsplit","convert012Num","convert012Size","_已拆分"),
	OCR_IMG_TXT(13,"图片文字识别","convert013","83","true","Ocr","convert013Num","convert013Size",null),
	PDF_ORC_WORD(14,"扫描件转Word","convert014","64","true","Pdftoocrword","convert014Num","convert014Size",null),
	PIC_PDF(15,"图片转PDF","convert015","32","true","Pic2pdf","convert015Num","convert015Size",null),

	PDF_ADDPAGES(16, "pdf插入页","convert016","76","true","pdfaddpages","convert016Num","convert016Size","_已插入页面"),
	PDF_LPNG(17, "pdf转png长图","convert017","77","true","pdf2lpng","convert017Num","convert017Size",null),
	PDF_DECRYPT(18, "pdf解密","convert018","78","true","pdfdecrypt","convert018Num","convert018Size","_已解密"),
	PDF_ENCRYPT(19, "pdf加密","convert019","79","true","pdfencrypt","convert019Num","convert019Size","_已加密"),

	PDF_COMPRESS(20,"PDF压缩","convert020","73",true,"pdfcompress","convert020Num","convert020Size","_已压缩"),
	PDF_DELETE_PAGE(21,"PDF删除页面","convert021","71",true,"pdfdeletepage","convert021Num","convert021Size","_已删除页面"),
	PDF_EXTRACT_IMG(22,"PDF提取图片","convert022","72",true,"pdfextractimg","convert022Num","convert022Size",null),
	PDF_ROTATE(23,"PDF旋转","convert023","74",true,"pdfrotate","convert023Num","convert023Size","_已旋转"),
	PPT_LONG_PIC(24,"PPT转长图","convert024","69",true,"pptLongPic","convert024Num","convert024Size",null),
	WORD_LONG_PIC(25,"Word转长图","convert025","69",true,"wordLongPic","convert025Num","convert025Size",null),
	PDF_ADD_PAGE_NUMBER(26,"PDF加页码","convert026","75",true,"pdfAddPageNumber","convert026Num","convert026Size","_已加页码"),


	PTS_UPLOAD_SIZE(99,"文件大小","uploadSize",null,3,null,"uploadSize","uploadSize",null),
	PTS_VALIDITY_TIME(100,"权益有效期","validityTime",null,1,null,"validityTime","validityTime",null);



	private Integer value;
	private String info;
	private String authCode;
	private String type;
	private Object defaultVaule;
	private String module;//与前端模块文件名一致，不可随意修改
	private String moduleNum;
	private String moduleSize;
	private String suffix;



	private EnumAuthCode(Integer value, String info,String authCode,String type,
			Object defaultVaule,String module,String moduleNum,String moduleSize,String suffix) {
		this.value = value;
		this.info = info;
		this.authCode = authCode;
		this.type = type;
		this.defaultVaule = defaultVaule;
		this.module = module;
		this.moduleNum = moduleNum;
		this.moduleSize = moduleSize;
		this.suffix = suffix;
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



	public static String getModuleSizeByModuleNum(String moduleNum) {
		for (EnumAuthCode code : values()) {
			if (code.getModuleNum() !=null && code.getModuleNum().equals(moduleNum)) {
				return code.getModuleSize();
			}
		}
		return null;
	}

	public static String getSuffixByAuthCode(String authCode){
		for (EnumAuthCode code : values()) {
			if (code.getAuthCode() !=null && code.getAuthCode().equals(authCode)) {
				return code.getSuffix();
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


	/**
	 * 判断是不是moduleSize
	 * @param moduleSize
	 * @return
	 */
	public static Boolean isModuleSize(String moduleSize) {
		for (EnumAuthCode code : values()) {
			if(code.getModuleSize()!=null && code.getModuleSize().equals(moduleSize)){
				return true;
			}
		}
		return false;
	}


	/**
	 * 判断是不是moduleNum
	 * @param moduleNum
	 * @return
	 */
	public static Boolean isModuleNum(String moduleNum) {
		for (EnumAuthCode code : values()) {
			if(code.getModuleNum()!=null && code.getModuleNum().equals(moduleNum)){
				return true;
			}
		}
		return false;
	}


	public static void main(String[] args) {
		System.out.println(EnumAuthCode.isModuleSize("convert001"));
		System.out.println(EnumAuthCode.isModuleNum("convert001Num"));
	}


}
