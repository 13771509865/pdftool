package com.neo.commons.cons;
/**
 * 返回对象的枚举类
 */
public enum EnumResultCode {
	E_SUCCES(0, "操作成功"), 
	E_FAIL(1, "操作失败"),
	E_PERMISSION(2, "用户无权限"),
	E_TIMEOUT(3, "登录超时"),
	
	E_FILEMD5_HEAD_FAIL(10,"根据文件头信息获取文件Md5失败"),
	E_DOWNLOAD_FILE_FAIL(11,"下载文件失败"),
	E_HTTP_SEND_FAIL(12,"http请求失败");
	private Integer value;
	private String info;
	private EnumResultCode(Integer value, String info) {
		this.value = value;
		this.info = info;
	}

	public Integer getValue() {
		return value;
	}

	public String getInfo() {
		return info;
	}


	public static EnumResultCode getEnum(Integer value) {
		for (EnumResultCode code : values()) {
			if (value == code.getValue())
				return code;
		}
		return null;
	}

}
