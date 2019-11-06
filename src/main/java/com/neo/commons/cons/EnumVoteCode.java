package com.neo.commons.cons;

import lombok.Getter;

/**
 * pdf工具集，投票枚举类
 * @author xujun
 * @description
 * @create 2019年10月28日
 */
@Getter
public enum EnumVoteCode {

	PDF_INSERT_PAGE(1,"pdf插入页面"),
	PDF_ROTATE(2,"pdf旋转"),
	PDF_DELETE_PAGE(3,"pdf删除页面"),
	PDF_ENCRYPT(4,"pdf加密"),
	PDF_EDIT(5,"pdf编辑"),
	PDF_UNLOCK(6,"pdf解锁"),
	CAD_TO_PDF(7,"cad转pdf"),
	COMPRESS_PDF(8,"压缩pdf"),
	PDF_SCAN_CONVERT(9,"pdf扫描件转换"),
	PDF_SCAN_CONVERT_DOUBLE(10,"pdf扫描件转双层pdf"),
	IMAGE_TO_PDF(11,"图片转pdf"),
	PDF_SIGN(12,"pdf电子签章"),
	OTHER(13,"其他");
	
	
	private Integer value;
	private String info;

	private EnumVoteCode(Integer code, String info) {
		this.value = code;
		this.info = info;
	}


	public static String getTypeInfo(Integer velue) {
        for (EnumVoteCode statu : values()) {
			if(statu.getValue().equals(velue)){
				return statu.getInfo();
			}
		}
		return null;
	}

	
	
	
}
