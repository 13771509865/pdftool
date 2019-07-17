package com.neo.commons.cons;
/**
 * 返回对象的枚举类
 */
public enum ResultCode {
	E_SUCCES(0, "操作成功"), 
	E_INPUT_FILE_NOTFOUND(1,"找不到指定文档"), 
	E_INPUT_FILE_OPENFAILED(2,"无法打开指定文档"), 
	E_CONVERSION_FAIL(3,"操作失败"), 
	E_INPUT_FILE_ENCRPTED(4,"转换的文档为加密文档或密码有误，请重新添加password参数进行转换"), 
	E_OUTPUT_FILE_WRONG_EXT(5, "输出文档后缀错误"), 
	E_DCC_EXPIRE(6, "授权文件过期"), 
	E_CONVERSION_TIMEOUT(7,"转换超时，内容可能不完整"),
	E_INVALID_PARAM(8,"无效参数"),
	
	//根据别的功能新增加的
	E_UPLOAD_FILE(10,"上传失败"),
	E_URLDOWNLOAD_FILE(11,"下载文件失败"),
	E_UPLOAD_SIZE(12,"文件过大"),
	E_DELETE_FAIL(13,"删除失败"),
	E_CONVERT_OVER_TIME(14,"可转码时间已到"),
	E_ACCESS_OVER_TIME(15,"您每天最多可转换50个文档"),
	E_REDIS_FAIL(16,"redis操作失败,请联系管理员检查redis服务"),
	E_UPLOAD_OVER_TIME(17,"您每天上传文档数量超过限制"),
	E_USEROPERATION_ILLEGAL(18,"用户操作无权限"),
	E_SERVER_BUSY(19,"服务器正忙"),
	E_FILESERVICE_FAIL(20,"文件操作失败,请联系管理员检查服务器"),
	E_GETCONVERTMD5_FAIL(21,"生成转换文档md5失败"),
	E_SERVER_UNKNOW_ERROR(22,"服务器未知错误^_^"),
	
	//用户权限
	E_VISITOR_UPLOAD_ERROR(23,"游客仅可上传10M以内文档，获取更大权限，请注册登录"),
	E_VISITOR_CONVERT_NUM_ERROR(24,"游客每日可转换5个文件，获取更大权限，请注册登录"),
	E_USER_UPLOAD_ERROR(25,"文件大小超出30M，暂不支持，如有紧急，请联系客服"),
	E_USER_CONVERT_NUM_ERROR(26,"每天可转换20个文件"),
	
	
	E_UNSUPPORT_FAIL(44,"没有支持的转码服务,请联系永中技术支持"),
	E_SIGNATURE_FAIL(45, "签批生成失败"),
	E_SIGNATURE_USE(46, "文件已被锁住，提交失败"),
	E_FCSENI_ERROR(47,"fcs授权文件错误,请检查文件"),
	E_WAITING_STATUS(48,"文档转换中,请稍后"),
	E_CONVERTYPE_SRCFILE_ERROR(49,"转换类型与文件不匹配,请检查参数"),
	E_MERGEFILE_NULL(51,"需要合并的文档不存在"),
	E_WMPICFILE_ERROE(52,"图片水印不存在或文件有误,请检查"),
	E_ILLEGA_PARAM(53,"参数包含非法字符，请检查参数");

	private Integer value;
	private String info;
	private ResultCode(Integer value, String info) {
		this.value = value;
		this.info = info;
	}

	public Integer getValue() {
		return value;
	}

	public String getInfo() {
		return info;
	}


	public static ResultCode getEnum(Integer value) {
		for (ResultCode code : values()) {
			if (value ==code.getValue())
				return code;
		}
		return E_CONVERSION_FAIL;
	}
}
