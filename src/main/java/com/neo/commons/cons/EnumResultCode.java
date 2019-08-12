package com.neo.commons.cons;
/**
 * 返回对象的枚举类
 */
public enum EnumResultCode {
	E_SUCCES(0, "操作成功"), 
	E_FAIL(1, "操作失败"),
	E_PERMISSION(2, "用户无权限"),
	E_TIMEOUT(3, "登录超时"),

	E_UPLOAD_FILE(9,"上传失败"),
	E_FILEMD5_HEAD_FAIL(10,"根据文件头信息获取文件Md5失败"),
	E_DOWNLOAD_FILE_FAIL(11,"下载文件失败"),
	E_HTTP_SEND_FAIL(12,"http请求失败"),
	E_SERVER_BUSY(19,"服务器正忙"),
	E_FILESERVICE_FAIL(20,"文件操作失败,请联系管理员检查服务器"),
	E_SERVER_UNKNOW_ERROR(22,"服务器未知错误^_^"),

	//用户权限
	E_VISITOR_UPLOAD_ERROR(23,"游客仅可上传10M以内文档，获取更大权限，请注册登录"),
	E_VISITOR_CONVERT_NUM_ERROR(24,"游客每日可转换5个文件，获取更大权限，请注册登录"),
	E_USER_UPLOAD_ERROR(25,"文件大小超出30M，暂不支持，如有紧急，请联系客服"),
	E_USER_CONVERT_NUM_ERROR(26,"每天可转换20个文件"),

	E_FCS_VTOKEN_FAIL(42,"获取vToken失败"),
	E_FCS_CONVERT_FAIL(43,"文档转换服务器通讯失败,请联系管理员"),
	E_NOTALL_PARAM(54,"参数不完整,请检查参数");


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
