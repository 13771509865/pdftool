package com.neo.commons.cons;

public enum EnumUserType {
	SYS_USER(0, "系统用户"), WX_USER(1, "微信用户");
	private Integer value;
	private String info;
	private EnumUserType(int code, String info) {
		this.value = code;
		this.info = info;
	}
	public static String getTypeInfo(Integer velue) {
        for (EnumUserType statu : values()) {
			if(statu.getValue().equals(velue)){
				return statu.getInfo();
			}
		}
		return null;
	}

	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
