package com.neo.commons.cons;
/**
 * 返回对象的枚举类
 */
public enum EnumResultCode {
	E_SUCCES(0, "操作成功"), 
	E_FAIL(1, "操作失败"),
	E_PERMISSION(2, "抱歉，您还未获取该功能的权限或者权限已过期，如有疑问，请联系客服"),
	E_TIMEOUT(3, "登录超时"),
	E_UNLOGIN_ERROR(4,"请立即登录，登录后可解锁功能"),

	E_UPLOAD_FILE(9,"上传失败"),
	E_FILEMD5_HEAD_FAIL(10,"根据文件头信息获取文件Md5失败"),
	E_DOWNLOAD_FILE_FAIL(11,"下载文件失败"),
	E_HTTP_SEND_FAIL(12,"http请求失败"),
	E_YCSERVICE_UPLOAD_ERROR(13,"优云服务器连接失败"),
	E_YCUPLOAD_ERROR(13,"获取优云文件失败"),
	E_ERROR_FILE_NULL(14,"错误文件不存在"),
	E_RECORD_FILE_ERROR(15,"记录错误文件失败"),
	E_ZIP_PACKAGE_ERROR(16,"打包压缩包失败"),
	E_SAVE_YCFILE_ERROR(17,"保存文件至优云失败"),
	E_GET_UCLOUD_ID_ERROR(18,"获取优云fileId失败"),
	
	E_SERVER_BUSY(19,"服务器正忙，请稍后再试"),
	E_FILESERVICE_FAIL(20,"抱歉防盗措施生效，文件操作失败"),
	E_SERVER_UNKNOW_ERROR(22,"服务器未知错误^_^"),

	//用户权限
	E_VISITOR_UPLOAD_ERROR(23,"游客仅可上传2M以内文档，获取更大权限，请注册登录"),
	E_VISITOR_CONVERT_NUM_ERROR(24,"游客每日可转换5个文件，获取更大权限，请注册登录"),
	E_USER_UPLOAD_ERROR(25,"抱歉，文件大小超出限制，升级身份后可以获取更大的权益"),
	E_USER_CONVERT_NUM_ERROR(26,"抱歉，转换次数已经超出每日限额，升级身份后可以获取更大的权益"),
	
	//投票
	E_VOTE_NULL_ERROR(27,"抱歉，提交前请至少选择一项投票内容"),
	E_VOTE_CONTENT_OVER_ERROR(28,"抱歉，输入的内容最多不能超过50个字符"),
	E_VOTE_NUM_ERROR(29,"抱歉，每次最多可以投5票"),
	E_VOTE_CONTENT_NULL_ERROR(30,"抱歉，选择其他选项时，请填写您需要的功能"),
	E_VOTE_OTHER_ERROR(31,"抱歉，选择其他选项时，才能提交您填写的内容"),
	E_ZIPPACK_FAIL(32, "打包成压缩包失败,请稍后重试"),
	
	E_FCS_VTOKEN_FAIL(42,"获取vToken失败"),
	E_FCS_CONVERT_FAIL(43,"抱歉网络有点忙，请您稍后再尝试"),
	E_NOTALL_PARAM(54,"参数不完整,请检查参数"),
	E_UCLOUDFILEID_NULL(55,"UCLOUDFILEID为空"),
	E_YCUPLOAD_SAVE_FAIL(56,"优云文件上传失败信息保存失败"),
	E_YCUPLOAD_UPDATE_FAIL(57,"优云文件上传失败信息更新失败"),
	E_YCUPLOAD_NULL(58,"未查到优云文件上传失败信息"),
	E_YCUPLOAD_RETRY_NULL(59,"重试成功0条数据"),
	
	E_USER_CLEAR_ILLEGAL(60, "非法删除操作"),
	E_USER_CLEAR_FAIL(61, "用户删除失败"),
	E_MERGE_FILE_NAME_ERROR(62,"亲爱的用户您好，您的文件名填写有误，请确认后使用。"),
	E_USER_INVALID(63,"无用户信息,请重新登录!"),
	E_DELETE_CONVERT_RECORD_NULL(64,"抱歉，没有找到对应的转换记录"),
	E_DELETE_CONVERT_RECORD_ERROR(65,"抱歉，删除转换记录失败，请稍后再试"),
	E_CONVERT_LIMIT_ERROR(66,"抱歉，您当前账户出现异常，请联系客服进行解决"),
	 
	E_GET_AUTH_ERROR(98,"解析用户权限失败"),
	E_ORDER_AUTH_CODE_ERROR(99,"抱歉该订单缺少pdf工具集必要的AuthCode或者存在非法的AuthCode"),
	E_ORDER_ILLEGAL(100,"非法订单信息"),
	E_CONVERT_FAIL(101,"转换失败"),
	E_BEING_CONVERT(102,"目标文件正在转换请稍后。。。"),
	;
	

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


	public static String getTypeInfo(Integer velue) {
        for (EnumResultCode statu : values()) {
			if(statu.getValue().equals(velue)){
				return statu.getInfo();
			}
		}
		return null;
	}
	
	public static EnumResultCode getEnum(Integer value) {
		for (EnumResultCode code : values()) {
			if (value == code.getValue())
				return code;
		}
		return null;
	}

}
