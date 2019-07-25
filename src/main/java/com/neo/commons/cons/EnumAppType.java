package com.neo.commons.cons;


public enum EnumAppType {
	
	MOBILE(0,"手机端"),
	PC(1,"移动端");
	
	private Integer value;
	private String info;
	
	
	private EnumAppType(Integer code, String info) {
		this.value = code;
		this.info = info;
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
	
    public static String getAppInfo(Integer value) {
        for (EnumAppType status : values()) {
            if (status.getValue().equals(value)) {
                return status.getInfo();
            }
        }
        return null;
    }

}
