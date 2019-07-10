package com.neo.commons.cons;

public enum EnumSsmCode {
	CODE_200("200","传输正常"),
	CODE_201("201","缺少参数"),
	CODE_8000("8000","系统内部错误（更新）"),
	CODE_8001("8001","请求参数不合法"),
	CODE_8002("8002","请求主密不存在"),
	CODE_8003("8003","请求子密不存在"),
	CODE_8004("8004","解密出错"),
	CODE_8005("8005","请求超时"),
	CODE_8006("8006","网络异常"),
	CODE_8007("8007","服务错误"),
	CODE_8008("8008","请求方法不存在"),
	CODE_8009("8009","无法匹配设备（设备不在线）"),
	CODE_8010("8010","无效的参数"),
	CODE_8011("8011","请求超时"),
	CODE_8012("8012","设备不存在"),
	CODE_8013("8013","设备状态异常"),
	CODE_8014("8014","设备忙");
	
	private String code;
	private String info;

	private EnumSsmCode(String code, String info) {
		this.code = code;
		this.info = info;
	}
	public static String getInfo(String velue) {
        for (EnumSsmCode code : values()) {
			if(code.getCode().equals(velue)){
				return code.getInfo();
			}
		}
		return "未知错误！";
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	
}
